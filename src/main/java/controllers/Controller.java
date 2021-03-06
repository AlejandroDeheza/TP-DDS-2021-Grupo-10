package controllers;

import modelo.asociacion.Asociacion;
import modelo.mascota.Foto;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioAsociaciones;
import repositorios.RepositorioCaracteristicas;
import spark.Request;
import spark.Response;
import utils.Constantes;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Controller implements WithGlobalEntityManager, TransactionalOps {

  protected Map<String, Object> getMap(Request request) {
    Map<String, Object> mapa = new HashMap<>();
    mapa.put("sesionIniciada", tieneSesionActiva(request));
    if (tieneSesionActiva(request)) {
      mapa.put("nombreUsuario", request.session().attribute("user_name"));
      mapa.put("esAdmin", request.session().attribute("is_admin"));
    }
    mapa.put("rutaCreacionUsuario", getRutaConOrigin(request, "/usuarios/creacion-usuario"));
    return mapa;
  }

  private Boolean tieneSesionActiva(Request request) {
    return request.session().attribute("user_id") != null;
  }

  protected String getRutaConOrigin(Request request, String ruta) {
    if (request.queryParams("origin") != null) {
      return ruta + "?origin=" + request.queryParams("origin");
    } else {
      return ruta;
    }
  }

  protected void iniciarSesion(Request request, Usuario usuario){
    request.session().attribute("user_id", usuario.getId());
    request.session().attribute("is_admin", usuario.esAdmin());
    request.session().attribute("user_name", usuario.getUsuario());
  }

  protected void redireccionCasoFeliz(Request request, Response response, String mensaje) {
    response.redirect(
        request.queryParams("origin") == null ? "/" : request.queryParams("origin")
    );
    // TODO: hacer una pantalla para poder mostrar un mensaje de exito, como la pantalla de errores
  }

  protected void redireccionCasoError(Request request, Response response, String mensaje) {
    request.session().attribute("mensajeError", mensaje);
    response.redirect("/error");
  }

  protected List<Integer> obtenerRango(int limite) {
    return IntStream.rangeClosed(1, limite).boxed()
        .collect(Collectors.toList());
  }

  protected List<Asociacion> getAsociacionesOrdenadas() {
    return new RepositorioAsociaciones().listarTodos().stream()
        .sorted((a1, a2) -> porOrdenAlfabetico(a1.getNombre(), a2.getNombre()))
        .collect(Collectors.toList());
  }

  protected int porOrdenAlfabetico(String s1, String s2) {
    return s1.compareTo(s2);
  }

  protected List<Caracteristica> obtenerListaCaracteristicas(Request request) {

    RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();

    // Obtengo los nombre de caracteristicas para comparar con los query params :)
    List<String> listaNombresCaracteristicas = repositorioCaracteristicas.getCaracteristicasConValoresPosibles()
        .stream()
        .map(CaracteristicaConValoresPosibles::getNombreCaracteristica)
        .collect(Collectors.toList());

    List<String> nombreParamsQueMandaron = request.queryParams()
        .stream()
        .filter(param -> listaNombresCaracteristicas
            .stream()
            .anyMatch(c -> c.equals(param)))
        .collect(Collectors.toList());

    List<Caracteristica> caracteristicas = new ArrayList<>();

    nombreParamsQueMandaron.forEach(
        param -> {
          Caracteristica caracteristica = repositorioCaracteristicas
              .getCaracteristicaSegun(param, request.queryParams(param));
          caracteristicas.add(caracteristica);
        }
    );

    return caracteristicas;
  }

  protected List<Foto> obtenerFotosMascota(Request request, Response response) {
    List<Foto> fotosMascota = new ArrayList<>();

    try {
      String pathUpload = new Constantes().getUploadDirectory();
      Path tempFile = Files.createTempFile(new File(pathUpload).toPath(), null, ".jpg");

      InputStream fotoInputStream = request.raw().getPart("fotoMascota").getInputStream();
      Files.copy(fotoInputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

      String pathWithForwardSlash = tempFile.toString().replace("\\", "/");
      String nombreDeLaFoto = pathWithForwardSlash.replace(pathUpload, "");
      fotosMascota.add(new Foto(nombreDeLaFoto, LocalDate.now().toString()));

    } catch (IOException | ServletException exception) {
      redireccionCasoError(request, response,
          "Hubo un error al cargar la foto de tu mascota, intentalo mas tarde o intenta con otra foto");
    }
    return fotosMascota;
  }

}
  