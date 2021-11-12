package controllers;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import modelo.asociacion.Asociacion;
import modelo.pregunta.ParDePreguntas;
import repositorios.RepositorioAsociaciones;
import repositorios.RepositorioPreguntasObligatorias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class PreguntasController extends Controller implements WithGlobalEntityManager, TransactionalOps {
  RepositorioAsociaciones repositorioAsociaciones = new RepositorioAsociaciones();
  RepositorioPreguntasObligatorias repositorioPreguntasObligatorias = new RepositorioPreguntasObligatorias();

  public ModelAndView mostrarPreguntasAsociaciones(Request request, Response response) {
    String filtro = request.queryParams("idAsociacion");

    List<Asociacion> todasLasAsociaciones = repositorioAsociaciones.getAsociaciones();
    List<Asociacion> asociacionFiltrada = filtro == null ?
        todasLasAsociaciones : repositorioAsociaciones.buscarPorId(Long.parseLong(filtro));

    List<ParDePreguntas> parDePreguntas = asociacionFiltrada.size() == 1 ?
        asociacionFiltrada.get(0).getPreguntas() : repositorioPreguntasObligatorias.getPreguntas();

    Map<String, Object> modelo = getMap(request);
    modelo.put("asociaciones", todasLasAsociaciones);
    modelo.put("preguntas", parDePreguntas);

    return new ModelAndView(modelo, "preguntas-asociaciones.html.hbs");
  }

  public ModelAndView cargarNuevaPreguntaAsociacion(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    List<Asociacion> todasLasAsociaciones = repositorioAsociaciones.getAsociaciones();
    modelo.put("asociaciones", todasLasAsociaciones);
    return new ModelAndView(modelo, "nueva-pregunta.html.hbs");
  }
  
  public ModelAndView matchearPreguntasAsociacion(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    return new ModelAndView(modelo, "matchear-preguntas.html.hbs");
  }

}
