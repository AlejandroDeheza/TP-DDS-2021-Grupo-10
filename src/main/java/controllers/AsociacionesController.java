package controllers;

import java.util.List;
import java.util.Map;
import modelo.asociacion.Asociacion;
import repositorios.RepositorioAsociaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class AsociacionesController extends Controller {
  private RepositorioAsociaciones repositorioAsociaciones = new RepositorioAsociaciones();

  public ModelAndView mostrarAsociaciones(Request request, Response response) {
    List<Asociacion> asociaciones = repositorioAsociaciones.listarTodos();
    Map<String, Object> modelo = getMap(request);
    modelo.put("asociaciones", asociaciones);
    modelo.put("mostrarBotonAgregarPreguntas", false);
    return new ModelAndView(modelo, "preguntas-asociaciones.html.hbs");
  }

}
