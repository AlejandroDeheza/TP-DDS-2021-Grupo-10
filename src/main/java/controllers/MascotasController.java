package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import modelo.mascota.*;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioMascotas;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.Constantes;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MascotasController extends Controller implements WithGlobalEntityManager, TransactionalOps {

  public ModelAndView mostrarRegistracion(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/login?origin=/registracion-mascota");
      return null;
    }
    Map<String, Object> modelo = getMap(request);
    RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
    List<CaracteristicaConValoresPosibles> listaCaracteristicas = repositorioCaracteristicas.getCaracteristicasConValoresPosibles();
    modelo.put("caracteristicas", listaCaracteristicas);

    return new ModelAndView(modelo, "registracion-mascota.html.hbs");
  }

  public Void registrarMascota(Request request, Response response) throws IOException {
    List<Caracteristica> caracteristicas;
    List<Foto> fotosMascota = new ArrayList<>();

    // Cargo la foto de la mascota
    File uploadDir = new File(Constantes.UPLOAD_DIRECTORY);
    Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".jpg");

    request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
    try(InputStream fotoInputStream = request.raw().getPart("fotoMascota").getInputStream()) {
      Files.copy(fotoInputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException | ServletException exception) {
      System.out.println(exception);
      redireccionCasoError(request, response, "/", "Hubo un error al cargar la foto de tu mascota, intenta con otra foto");
    }

    //Obtengo sus caracteristicas
    caracteristicas = obtenerListaCaracteristicas(request);

    // Fecha de nacimiento
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    LocalDate fechaNacimiento = LocalDate.parse(request.queryParams("fechaNacimiento"),formatter);

    MascotaRegistrada nueva = new MascotaRegistrada(
        new RepositorioUsuarios().getPorId(request.session().attribute("user_id")),
        request.queryParams("nombre"),
        request.queryParams("apodo"),
        fechaNacimiento,
        request.queryParams("descripcionFisica"),
        Sexo.values()[Integer.parseInt(request.queryParams("sexo"))],
        Animal.values()[Integer.parseInt(request.queryParams("tipoAnimal"))],
        caracteristicas,
        fotosMascota,
        TamanioMascota.values()[Integer.parseInt(request.queryParams("tamanio"))]
    );

    withTransaction(() -> {
      new RepositorioMascotas().agregar(nueva);
    });

    redireccionCasoFeliz(request, response, "/", "MASCOTA_REGISTRADA");
    return null;
  }

  private List<Caracteristica> obtenerListaCaracteristicas(Request request) {
    Set<String> queryParams = request.queryParams();

    List<Caracteristica> caracteristicas = new ArrayList<>();

    RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
    // Obtengo los nombre de caracteristicas para comparar con los query params :)
    List<String> listaNombresCaracteristicas = repositorioCaracteristicas.getCaracteristicas()
        .stream()
        .map(caracteristica -> caracteristica.getNombreCaracteristica())
        .collect(Collectors.toList());

    List<String> nombreParamsQueMandaron = queryParams
        .stream()
        .filter(param -> listaNombresCaracteristicas
            .stream()
            .anyMatch( c -> c.equals(param)))
        .collect(Collectors.toList());

    nombreParamsQueMandaron.forEach(
        param -> {
          Caracteristica caracteristica = new Caracteristica(param, request.queryParams(param));
          caracteristicas.add(caracteristica);
        }
    );
    return caracteristicas;
  }
}
