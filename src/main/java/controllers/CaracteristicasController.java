package controllers;

import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.pregunta.ParDePreguntas;
import repositorios.RepositorioCaracteristicas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.*;
import java.util.stream.Collectors;

public class CaracteristicasController extends Controller {

  RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();

  public ModelAndView mostrarCaracteristicas(Request request, Response response) {

    List<CaracteristicaConValoresPosibles> listaCaracteristicasOrdenadas = repositorioCaracteristicas
        .getCaracteristicasConValoresPosibles().stream()
        .sorted((c1, c2) -> super.porOrdenAlfabetico(c1.getNombreCaracteristica(), c2.getNombreCaracteristica()))
        .collect(Collectors.toList());

    Integer index = 1;
    List<WrapperCaracteristica> wrappersDeCaracteristicas = new ArrayList<>();
    for(CaracteristicaConValoresPosibles caracteristica : listaCaracteristicasOrdenadas){
      wrappersDeCaracteristicas.add(new WrapperCaracteristica(index ++, caracteristica));
    }

    Map<String, Object> modelo = getMap(request);
    modelo.put("caracteristicas", wrappersDeCaracteristicas);
    return new ModelAndView(modelo, "caracteristicas.html.hbs");
  }

  public ModelAndView mostrarFormularioCreacionCaracteristicas(Request request, Response response) {
    int totalCaracteristicas = 5;
    Map<String, Object> modelo = getMap(request);
    modelo.put("cantidadCaracteristicas", super.obtenerRango(totalCaracteristicas));
    return new ModelAndView(modelo, "nueva-caracteristica.html.hbs");
  }

  public Void crearNuevaCaracteristicas(Request request, Response response) {

    List<String> listaCaracteristicas = new ArrayList<>();
    listaCaracteristicas.add( request.queryParams("ValoresCaracteristica1") );
    listaCaracteristicas.add( request.queryParams("ValoresCaracteristica2") );
    listaCaracteristicas.add( request.queryParams("ValoresCaracteristica3") );
    listaCaracteristicas.add( request.queryParams("ValoresCaracteristica4") );
    listaCaracteristicas.add( request.queryParams("ValoresCaracteristica5") );

    listaCaracteristicas.removeAll(Collections.singleton(""));

    if (listaCaracteristicas.size() < 2) {
      super.redireccionCasoError(request, response, "Debe ingresar mas de una caracteristica posible");
    }

    CaracteristicaConValoresPosibles caracteristicaConValores = new CaracteristicaConValoresPosibles(
        request.queryParams("nombreCaracteristica"),
        listaCaracteristicas
    );

    withTransaction(() -> {
      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(caracteristicaConValores);
    });

    response.redirect("/caracteristicas");
    return null;
  }

}
