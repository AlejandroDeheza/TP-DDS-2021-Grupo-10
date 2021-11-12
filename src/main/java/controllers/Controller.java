package controllers;

import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public abstract class Controller {

  protected Map<String, Object> getMap(Request request) {
    Map<String, Object> mapa = new HashMap<>();
    mapa.put("sesionIniciada", tieneSesionActiva(request));
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
}
  