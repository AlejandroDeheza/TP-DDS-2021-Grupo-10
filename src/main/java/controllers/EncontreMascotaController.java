package controllers;

import modelo.mascota.MascotaRegistrada;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.persona.TipoDocumento;
import repositorios.RepositorioMascotaRegistrada;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class EncontreMascotaController extends Controller {

  public ModelAndView getFormularioDatosNoUsuario(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    if (tieneSesionActiva(request)) {
      response.redirect("/mascotas/encontre-mascota/lugar-encuentro");
      return null;
    }

    EnumSet<TipoNotificadorPreferido> tipoNotificadorPreferidos = EnumSet.allOf(TipoNotificadorPreferido.class);

    EnumSet<TipoDocumento> tiposDocumentos = EnumSet.allOf(TipoDocumento.class);
    modelo.put("medio-de-contacto", tipoNotificadorPreferidos);
    modelo.put("tipos-documentos", tiposDocumentos);
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
    return new ModelAndView(getMap(request), "encontre-mascota-tipo-encuentro.html.hbs");
  }


  public ModelAndView getFormularioConChapita(Request request, Response response) {
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    Map<String, Object> modelo = getMap(request);
    RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();
    String id = request.queryParams("codigo-chapita");
    MascotaRegistrada mascotaRegistrada =
        id != null ?
            repositorioMascotaRegistrada.getPorId(Long.parseLong(id)) :
            null;

    modelo.put("mascotaRegistrada", mascotaRegistrada);
    return new ModelAndView(modelo, "encontre-mascota-tipo-encuentro-con-chapita.html.hbs");
  }

//
//  public Object getDetalleMascotaConChapita(Request request, Response response,
//                                            TemplateEngine engine) {
//    if (request.session().attribute("user_id") != null) {
//      response.redirect("/mascotas/encontre-mascota");
//      return null;
//    }
//    String id = request.params(":id");
//    try {
//      RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();
//      MascotaRegistrada mascotaRegistrada =
//          repositorioMascotaRegistrada.getPorId(Long.parseLong(id));
//      return mascotaRegistrada != null ?
//          engine.render(new ModelAndView(mascotaRegistrada, "encontre-mascota-tipo-encuentro-con-chapita.html.hbs"))
//          : null;
//    } catch (NumberFormatException e) {
//      response.status(400);
//      System.out.println("El id ingresado (" + id + ") no es un número");
//      return "Bad Request";
//    }
//  }

  public ModelAndView getFormularioSinChapita(Request request, Response response) {
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    return new ModelAndView(getMap(request), "encontre-mascota-tipo-encuentro-sin-chapita.html.hbs");
  }


  public Void enviarMascotaEncontrada(Request request, Response response) {
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }

    redireccionCasoFeliz(request, response, "/", "La cuenta se ha registrado con exito!");
    return null;
  }
}
