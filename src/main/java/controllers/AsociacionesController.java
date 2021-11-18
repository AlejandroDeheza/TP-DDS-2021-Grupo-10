package controllers;

import java.util.List;
import java.util.Map;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import modelo.asociacion.Asociacion;
import repositorios.RepositorioAsociaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class AsociacionesController extends Controller implements WithGlobalEntityManager, TransactionalOps {
  private RepositorioAsociaciones repositorioAsociaciones = new RepositorioAsociaciones();

  public ModelAndView mostrarAsociaciones(Request request, Response response) {
    List<Asociacion> asociaciones = repositorioAsociaciones.getAsociaciones();
    Map<String, Object> modelo = getMap(request);
    modelo.put("asociaciones", asociaciones);
    return new ModelAndView(modelo, "asociaciones.html.hbs");
  }

}
