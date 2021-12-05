package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.Map;

public class AsociacionesController extends Controller {

  public ModelAndView mostrarAsociaciones(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    modelo.put("asociaciones", super.getAsociacionesOrdenadas());
    modelo.put("mostrarBotonAgregarPreguntas", false);
    return new ModelAndView(modelo, "preguntas-asociaciones.html.hbs");
  }

}
