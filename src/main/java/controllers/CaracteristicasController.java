package controllers;

import modelo.asociacion.Asociacion;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.pregunta.ParDePreguntas;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioAsociaciones;
import repositorios.RepositorioCaracteristicas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CaracteristicasController extends Controller implements WithGlobalEntityManager, TransactionalOps {

  RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();

  public ModelAndView mostrarCaracteristicas(Request request, Response response) {

    List<CaracteristicaConValoresPosibles> listaCaracteristicas = repositorioCaracteristicas.getCaracteristicasConValoresPosibles();
    List<BorradorCaracteristicas> borradorCaracteristicas = new ArrayList<>();

    Integer index = 1;

    for(CaracteristicaConValoresPosibles caracteristica : listaCaracteristicas){
      borradorCaracteristicas.add(new BorradorCaracteristicas(index++,caracteristica));
    };

    Map<String, Object> modelo = getMap(request);
    modelo.put("caracteristicas", borradorCaracteristicas);

    return new ModelAndView(modelo, "caracteristicas.html.hbs");
  }

  public ModelAndView cantidadCaracteristicas(Request request, Response response) {
    int totalCaracteristicas = 5;
    List<Integer> cantidadCaracteristicas = IntStream.rangeClosed(1, totalCaracteristicas).boxed().collect(Collectors.toList());

    Map<String, Object> modelo = getMap(request);
    modelo.put("cantidadCaracteristicas", cantidadCaracteristicas);
    return new ModelAndView(modelo, "nueva-caracteristica.html.hbs");
  }


  public Void crearNuevaCaracteristicas(Request request, Response response) {

    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>CARACTERISTICAS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" );

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

    List<String> listaCaracteristicasCompletas = new ArrayList<>();

    Integer valoresCompletos = 0;

    for(String caracteristica:listaCaracteristicas){
      if(!caracteristica.isEmpty()){
        valoresCompletos++;
      }
    }

    if(valoresCompletos < 2){
      super.redireccionCasoError(request, response, null, "Debe ingresar mas de una caracteristica posible");
    }

    if(valoresCompletos >= 2) {
      withTransaction(() -> {

        listaCaracteristicas.stream().forEach(caracteristica -> {
          if (!caracteristica.isEmpty()) {
            listaCaracteristicasCompletas.add(caracteristica);
          }
        });

        CaracteristicaConValoresPosibles caracteristicaConValores = new CaracteristicaConValoresPosibles(
            nombreCaracteristica,
            listaCaracteristicasCompletas
        );

        repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(caracteristicaConValores);
      });
    }

    response.redirect("/caracteristicas");
    return null;
  }

}
