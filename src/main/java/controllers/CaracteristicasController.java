package controllers;

import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioAsociaciones;
import repositorios.RepositorioCaracteristicas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Map;

public class CaracteristicasController extends Controller implements WithGlobalEntityManager, TransactionalOps {

  RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();

  public ModelAndView mostrarCaracteristicas(Request request, Response response) {

    List<CaracteristicaConValoresPosibles> listaCaracteristicas = repositorioCaracteristicas.getCaracteristicasConValoresPosibles();
    CaracteristicaConValoresPosibles valor1 = listaCaracteristicas.get(1);

    System.out.println("Nombre1: " + listaCaracteristicas.get(0).getNombreCaracteristica());
    System.out.println("Valores1: " + listaCaracteristicas.get(0).getValoresCaracteristicas());

    Map<String, Object> modelo = getMap(request);
    modelo.put("caracteristicas", listaCaracteristicas);

    return new ModelAndView(getMap(request), "caracteristicas.html.hbs");
  }

  public ModelAndView cargarNuevaCaracteristica(Request request, Response response) {
    return new ModelAndView(getMap(request), "nueva-caracteristica.html.hbs");
  }

}
