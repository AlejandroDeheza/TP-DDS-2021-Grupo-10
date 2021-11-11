package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.Map;

public class EncontreMascotaController {

  public ModelAndView getFormularioDatosNoUsuario(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota/lugar-encuentro");
      return null;
    }
    return new ModelAndView(modelo, "formulario-encontre-mascota-datos-personales.html.hbs");
  }


  public Void completarLugarEncuentro(Request request, Response response) {
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    response.redirect("/mascotas/encontre-mascota/lugar-encuentro");
    return null;
  }

  public ModelAndView getFormularioLugarEncuentro(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    return new ModelAndView(modelo, "formulario-encontre-mascota-datos-ubicacion.html.hbs");
  }

  public Void elegirLugarEncuentro(Request request, Response response) {
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    response.redirect("/mascotas/encontre-mascota/lugar-encuentro/tipo-encuentro");
    return null;
  }

  public ModelAndView getTiposEncuentros(Request request, Response response) {
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    return new ModelAndView(null, "encontre-mascota-tipo-encuentro.html.hbs");
  }


  public ModelAndView getFormularioConChapita(Request request, Response response) {
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    return new ModelAndView(null, "encontre-mascota-tipo-encuentro-con-chapita.html.hbs");
  }

  public ModelAndView getFormularioSinChapita(Request request, Response response) {
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    return new ModelAndView(null, "encontre-mascota-tipo-encuentro-sin-chapita.html.hbs");
  }


  public Void enviarMascotaEncontrada(Request request, Response response) {
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    response.redirect("/");
    return null;
  }
}
