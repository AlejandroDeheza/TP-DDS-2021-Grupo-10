package controllers;

import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import repositorios.RepositorioCaracteristicas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CaracteristicasController extends Controller {

  RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();

  public ModelAndView mostrarCaracteristicas(Request request, Response response) {

    List<CaracteristicaConValoresPosibles> listaCaracteristicas = repositorioCaracteristicas
        .getCaracteristicasConValoresPosibles();
    List<WrapperCaracteristica> wrappersDeCaracteristicas = new ArrayList<>();

    Integer index = 1;

    for(CaracteristicaConValoresPosibles caracteristica : listaCaracteristicas){
      wrappersDeCaracteristicas.add(new WrapperCaracteristica(index ++, caracteristica));
    }

    Map<String, Object> modelo = getMap(request);
    modelo.put("caracteristicas", wrappersDeCaracteristicas);
    return new ModelAndView(modelo, "caracteristicas.html.hbs");
  }

  public ModelAndView cantidadCaracteristicas(Request request, Response response) {
    int totalCaracteristicas = 5;
    List<Integer> cantidadCaracteristicas = super.obtenerRango(totalCaracteristicas);

    Map<String, Object> modelo = getMap(request);
    modelo.put("cantidadCaracteristicas", cantidadCaracteristicas);
    return new ModelAndView(modelo, "nueva-caracteristica.html.hbs");
  }


  public Void crearNuevaCaracteristicas(Request request, Response response) {

    String nombreCaracteristica = request.queryParams("nombreCaracteristica");
    String caracteristica1 = request.queryParams("ValoresCaracteristica1");
    String caracteristica2 = request.queryParams("ValoresCaracteristica2");
    String caracteristica3 = request.queryParams("ValoresCaracteristica3");
    String caracteristica4 = request.queryParams("ValoresCaracteristica4");
    String caracteristica5 = request.queryParams("ValoresCaracteristica5");

    List<String> listaCaracteristicas = new ArrayList<>();
    listaCaracteristicas.add(caracteristica1);
    listaCaracteristicas.add(caracteristica2);
    listaCaracteristicas.add(caracteristica3);
    listaCaracteristicas.add(caracteristica4);
    listaCaracteristicas.add(caracteristica5);

    listaCaracteristicas.removeAll(Collections.singleton(""));

    if(listaCaracteristicas.size() < 2){
      super.redireccionCasoError(request, response, null, "Debe ingresar mas de una caracteristica posible");
    }

    CaracteristicaConValoresPosibles caracteristicaConValores = new CaracteristicaConValoresPosibles(
        nombreCaracteristica,
        listaCaracteristicas
    );

    withTransaction(() -> {
      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(caracteristicaConValores);
    });

    response.redirect("/caracteristicas");
    return null;
  }

}
