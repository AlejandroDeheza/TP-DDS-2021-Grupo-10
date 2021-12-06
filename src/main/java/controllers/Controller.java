package controllers;

import modelo.asociacion.Asociacion;
import modelo.mascota.Foto;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioAsociaciones;
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
import java.util.stream.IntStream;

public abstract class Controller implements WithGlobalEntityManager, TransactionalOps {

  protected Map<String, Object> getMap(Request request) {
    Map<String, Object> mapa = new HashMap<>();
    mapa.put("sesionIniciada", tieneSesionActiva(request));
    mapa.put("esAdmin", request.session().attribute("is_admin"));
    if (tieneSesionActiva(request)) {
      mapa.put("nombreUsuario", request.session().attribute("user_name"));
    }
    return mapa;
  }

  private Boolean tieneSesionActiva(Request request) {
    return request.session().attribute("user_id") != null;
  }

  protected Boolean noEsAdmin(Request request) {
    Boolean esAdmin = request.session().attribute("is_admin");
    return !esAdmin;
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
      redireccionCasoError(request, response, "/",
          "Hubo un error al cargar la foto de tu mascota, intentalo mas tarde o intenta con otra foto");
    }
    return fotosMascota;
  }

  protected List<Integer> obtenerRango(int limite) {
    return IntStream.rangeClosed(1, limite).boxed()
        .collect(Collectors.toList());
  }

  protected int porOrdenAlfabetico(String s1, String s2) {
    return s1.compareTo(s2);
  }

  protected List<Asociacion> getAsociacionesOrdenadas() {
    return new RepositorioAsociaciones().listarTodos().stream()
        .sorted((a1, a2) -> porOrdenAlfabetico(a1.getNombre(), a2.getNombre()))
        .collect(Collectors.toList());
  }

}
  