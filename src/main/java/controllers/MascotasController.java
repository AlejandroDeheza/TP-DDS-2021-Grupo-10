package controllers;

import modelo.mascota.*;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioMascotaRegistrada;
import repositorios.RepositorioMascotas;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.Constantes;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    try (InputStream fotoInputStream = request.raw().getPart("fotoMascota").getInputStream()) {
      Files.copy(fotoInputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
      Foto fotoMascota = new Foto(tempFile.toString(), null);
      fotosMascota.add(fotoMascota);
    } catch (IOException | ServletException exception) {
      System.out.println(exception);
      redireccionCasoError(request, response, "/", "Hubo un error al cargar la foto de tu mascota, intenta con otra foto");
    }

    //Obtengo sus caracteristicas
    caracteristicas = super.obtenerListaCaracteristicas(request);

    // Fecha de nacimiento
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    LocalDate fechaNacimiento = LocalDate.parse(request.queryParams("fechaNacimiento"), formatter);

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

  public ModelAndView getRedirectMascotas(Request request, Response response) {
    return new ModelAndView(getMap(request), "menu-mascotas.html.hbs");
  }

  public ModelAndView getMascotasDeUsuario(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/login");
      return null;
    }
    // OBtener las mascotas del usuario que pidio esto
    Usuario usuario = new RepositorioUsuarios().getPorId(request.session().attribute("user_id"));
    RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();

    // Meterlas en el modelo
    Map<String, Object> modelo = getMap(request);
    modelo.put("mascotasUsuario", repositorioMascotaRegistrada.obtenerMascotasDeUnDuenio(usuario));
    //Mandarlas a la vista
    return new ModelAndView(modelo, "mis-mascotas.html.hbs");
  }
}