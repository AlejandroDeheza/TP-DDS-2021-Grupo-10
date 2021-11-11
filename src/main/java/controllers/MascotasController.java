package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MascotasController extends Controller {

  public ModelAndView mostrarRegistracion(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/login?origin=/registracion-mascota");
      return null;
    }
    return new ModelAndView(getMap(request), "registracion-mascota.html.hbs");
  }


  public ModelAndView getRedirectMascotas(Request request, Response response) {
    return new ModelAndView(null, "menu-mascotas.html.hbs");
  }


}
