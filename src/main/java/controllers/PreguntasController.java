package controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
  BorradorParDePreguntas borradorParDePreguntas;

  public ModelAndView mostrarPreguntasAsociaciones(Request request, Response response) {
    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<mostrarPreguntasAsociaciones<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    String filtro = request.queryParams("idAsociacion");

    List<Asociacion> todasLasAsociaciones = repositorioAsociaciones.getAsociaciones();
    List<Asociacion> asociacionFiltrada = filtro == null ?
        todasLasAsociaciones : repositorioAsociaciones.buscarPorId(Long.parseLong(filtro));

    List<ParDePreguntas> parDePreguntas = asociacionFiltrada.size() == 1 ?
        asociacionFiltrada.get(0).getPreguntas() : repositorioPreguntasObligatorias.getPreguntas();
    /* TODO: filtrar solamente preguntas que sean obligatorias de la asociación. Contemplar preguntas no obligatorias? */

    Map<String, Object> modelo = getMap(request);
    modelo.put("asociaciones", todasLasAsociaciones);
    modelo.put("preguntas", parDePreguntas);

    return new ModelAndView(modelo, "preguntas-asociaciones.html.hbs");
  }

  public ModelAndView cargarNuevaPreguntaAsociacion(Request request, Response response) {
    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<cargarNuevaPreguntaAsociacion<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    borradorParDePreguntas = new BorradorParDePreguntas();
    Map<String, Object> modelo = getMap(request);
    List<Asociacion> todasLasAsociaciones = repositorioAsociaciones.getAsociaciones();
    modelo.put("asociaciones", todasLasAsociaciones);
    return new ModelAndView(modelo, "nueva-pregunta.html.hbs");
  }

  public ModelAndView matchearPreguntasAsociacion(Request request, Response response) {
    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<matchearPreguntasAsociacion<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    String asociacionId = request.queryParams("asociacionId");
    String preguntaDador = request.queryParams("preguntaDador");
    String preguntaAdoptante = request.queryParams("preguntaAdoptante");
    String cantidadMatcheos = request.queryParams("cantidadMatcheos");
    Boolean esObligatoria = request.queryParams("esObligatoria") == null ? false : true;
    Map<String, Object> modelo = getMap(request);
    List<Integer> matcheos = IntStream.rangeClosed(1, Integer.parseInt(cantidadMatcheos)).boxed().collect(Collectors.toList());
    this.borradorParDePreguntas.setPreguntas(preguntaDador, preguntaAdoptante, esObligatoria).setAsociacionId(Long.parseLong(asociacionId));

    request.session().attribute("asociacion_id", asociacionId);
    request.session().attribute("pregunta_dador", preguntaDador);
    request.session().attribute("pregunta_adoptante", preguntaAdoptante);
    request.session().attribute("es_obligatoria", esObligatoria);

    modelo.put("asociacionId", asociacionId);
    modelo.put("cantidadMatcheos", matcheos);
    modelo.put("borradorParDePreguntas", borradorParDePreguntas);
    return new ModelAndView(modelo, "matchear-preguntas.html.hbs");
  }

  public Void crearParDePreguntasAsociacion(Request request, Response response) {
    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<crearParDePreguntasAsociacion<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    String asociacionId = request.session().attribute("asociacion_id");
    String preguntaDador = request.session().attribute("pregunta_dador");
    String preguntaAdoptante = request.session().attribute("pregunta_adoptante");
    Boolean esObligatoria = request.session().attribute("es_obligatoria");

    System.out.println("ID: " + asociacionId + "Dador: " + preguntaDador + "Adoptante: " + preguntaAdoptante + "Obligatoria: " + esObligatoria);

    ParDePreguntas parDePreguntas = new ParDePreguntas(
      preguntaDador,
      preguntaAdoptante,
      esObligatoria
    );

    withTransaction(() -> {
      if(esObligatoria) {
        repositorioAsociaciones.getAsociaciones().stream().forEach(asociacion -> {
          asociacion.agregarPregunta(parDePreguntas);
        });
      } else {
        Asociacion asociacion = repositorioAsociaciones.buscarPorId(Long.parseLong(asociacionId)).get(0);
        asociacion.agregarPregunta(parDePreguntas);
      }
    });

    this.finalizarCreacionDeParDePreguntas(request, response);
    return null;
  }

  public Void finalizarCreacionDeParDePreguntas(Request request, Response response) {
    request.session().attribute("asociacion_id", null);
    request.session().attribute("pregunta_dador", null);
    request.session().attribute("pregunta_adoptante", null);
    request.session().attribute("es_obligatoria", null);
    response.redirect("/preguntas-asociaciones");
    return null;
  }

}
