package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.Map;

public class ErrorController extends Controller {

  public ModelAndView mostrarPantallaError(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    String mensajeError = request.session().attribute("mensajeError");
    modelo.put("mensajeError", mensajeError);
    return new ModelAndView(modelo, "error.html.hbs");
  }
}
