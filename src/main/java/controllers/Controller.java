package controllers;

import modelo.mascota.Foto;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import repositorios.RepositorioCaracteristicas;
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
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Controller {

  protected Map<String, Object> getMap(Request request) {
    Map<String, Object> mapa = new HashMap<>();
    mapa.put("sesionIniciada", tieneSesionActiva(request));
    mapa.put("esAdmin", request.session().attribute("is_admin"));
    if (tieneSesionActiva(request)) {
      mapa.put("nombreUsuario", request.session().attribute("user_name"));
    }
    return mapa;
  }

  protected Boolean tieneSesionActiva(Request request) {
    return request.session().attribute("user_id") != null;
  }

  protected Boolean noEsAdmin(Request request) {
    return request.session().attribute("is_admin") == null;
  }

  protected void redireccionCasoFeliz(Request request, Response response, String porDefecto, String mensaje) {
    response.redirect(
        request.queryParams("origin") == null ? porDefecto : request.queryParams("origin")
    );
    // TODO redirigir agregando un mensaje de exito si hace falta
  }

  protected void redireccionCasoError(Request request, Response response, String porDefecto, String mensaje) {
    response.redirect("/error" +
        (request.queryParams("origin") == null ? "" : "?origin=" + request.queryParams("origin")) +
        "?mensajeError=" + mensaje
    );
  }

  protected List<Caracteristica> obtenerListaCaracteristicas(Request request) {
    Set<String> queryParams = request.queryParams();

    List<Caracteristica> caracteristicas = new ArrayList<>();

    RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
    // Obtengo los nombre de caracteristicas para comparar con los query params :)
    List<String> listaNombresCaracteristicas = repositorioCaracteristicas.getCaracteristicasConValoresPosibles()
        .stream()
        .map(CaracteristicaConValoresPosibles::getNombreCaracteristica)
        .collect(Collectors.toList());

    List<String> nombreParamsQueMandaron = queryParams
        .stream()
        .filter(param -> listaNombresCaracteristicas
            .stream()
            .anyMatch(c -> c.equals(param)))
        .collect(Collectors.toList());

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
      File uploadDir = new File(Constantes.UPLOAD_DIRECTORY);
      Path tempFile = Files.createTempFile(uploadDir.toPath(), null, ".jpg");

      request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
      InputStream fotoInputStream = request.raw().getPart("fotoMascota").getInputStream();
      Files.copy(fotoInputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

      String pathWithForwardSlash = tempFile.toString().replace("\\", "/");
      String nombreDeLaFoto = pathWithForwardSlash.replace(Constantes.UPLOAD_DIRECTORY, "");
      fotosMascota.add(new Foto(nombreDeLaFoto, LocalDate.now().toString()));
    } catch (IOException | ServletException exception) {
      redireccionCasoError(request, response, "/",
          "Hubo un error al cargar la foto de tu mascota, intentalo mas tarde o intenta con otra foto");
    }
    return fotosMascota;
  }
}
  