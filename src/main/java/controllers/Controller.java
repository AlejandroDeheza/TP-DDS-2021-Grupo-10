package controllers;

import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import repositorios.RepositorioCaracteristicas;
import spark.Request;
import spark.Response;

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
}
  