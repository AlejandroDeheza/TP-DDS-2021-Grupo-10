package controllers;

import repositorios.RepositorioDarEnAdopcion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.Map;

public class PublicacionesController extends Controller{

  RepositorioDarEnAdopcion repositorioDarEnAdopcion = new RepositorioDarEnAdopcion();

  public ModelAndView mostrarMascotasEnAdopcion(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    modelo.put("mascotasEnAdopcion", repositorioDarEnAdopcion.getPublicacionesActivas());
    return new ModelAndView(modelo, "mascotas-en-adopcion.html.hbs");
  }
}
