package controllers;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CaracteristicasController extends Controller implements WithGlobalEntityManager, TransactionalOps {

  public ModelAndView mostrarCaracteristicas(Request request, Response response) {
    return new ModelAndView(getMap(request), "caracteristicas.html.hbs");
  }

}
