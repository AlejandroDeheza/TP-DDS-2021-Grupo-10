package controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import modelo.asociacion.Asociacion;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.ParDeRespuestas;
import repositorios.RepositorioAsociaciones;
import repositorios.RepositorioPreguntasObligatorias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class PreguntasController extends Controller implements WithGlobalEntityManager, TransactionalOps {
  private RepositorioAsociaciones repositorioAsociaciones = new RepositorioAsociaciones();
  private RepositorioPreguntasObligatorias repositorioPreguntasObligatorias = new RepositorioPreguntasObligatorias();
  private BorradorParDePreguntas borradorParDePreguntas;
  final private String respuestasPosibles = "5";

  public ModelAndView mostrarPreguntasDeLaAsociacion(Request request, Response response) {
    String idAsociacion = request.params(":idAsociacion");
    request.session().attribute("es_obligatoria", false);

    List<Asociacion> asociaciones = repositorioAsociaciones.getAsociaciones();
    Asociacion asociacionBuscada = repositorioAsociaciones.buscarPorId(Long.parseLong(idAsociacion)).get(0);
    List<ParDePreguntas> paresDePreguntasDeLaAsociacionBuscada = asociacionBuscada.getPreguntas();

    Map<String, Object> modelo = getMap(request);
    modelo.put("asociaciones", asociaciones);
    modelo.put("asociacion", asociacionBuscada);
    modelo.put("preguntas", paresDePreguntasDeLaAsociacionBuscada);
    modelo.put("esObligatoria", request.session().attribute("es_obligatoria"));
    return new ModelAndView(modelo, "preguntas-asociaciones.html.hbs");
  }

  public ModelAndView mostrarPreguntasObligatoriasDeLasAsociaciones(Request request, Response response) {
    String idAsociacion = request.queryParams("idAsociacion");
    request.session().attribute("es_obligatoria", true);

    List<Asociacion> todasLasAsociaciones = repositorioAsociaciones.getAsociaciones();
    List<Asociacion> asociacionFiltrada = idAsociacion == null ?
        todasLasAsociaciones : repositorioAsociaciones.buscarPorId(Long.parseLong(idAsociacion));

    List<ParDePreguntas> paresDePreguntasObligatorias = asociacionFiltrada.size() == 1 ?
        asociacionFiltrada.get(0).getPreguntasObligatorias() : repositorioPreguntasObligatorias.getPreguntasObligatorias();

    Map<String, Object> modelo = getMap(request);
    modelo.put("asociaciones", todasLasAsociaciones);
    modelo.put("preguntas", paresDePreguntasObligatorias);
    modelo.put("esObligatoria", request.session().attribute("es_obligatoria"));
    return new ModelAndView(modelo, "preguntas-asociaciones.html.hbs");
  }

  public ModelAndView agregarNuevaPreguntaALaAsociacion(Request request, Response response) {
    String idAsociacion = request.params(":idAsociacion");
    List<Integer> listaRespuestasPosibles = IntStream.rangeClosed(1, Integer.parseInt(respuestasPosibles)).boxed().collect(Collectors.toList());
    Map<String, Object> modelo = getMap(request);
    modelo.put("asociacion", idAsociacion);
    modelo.put("respuestasPosibles", listaRespuestasPosibles);
    return new ModelAndView(modelo, "nueva-pregunta.html.hbs");
  }

  public ModelAndView matchearRespuestasPosibles(Request request, Response response) {
    String idAsociacion = request.params(":idAsociacion");

    this.borradorParDePreguntas = new BorradorParDePreguntas();
    this.borradorParDePreguntas.setAsociacionId(Long.parseLong(idAsociacion));
    this.borradorParDePreguntas.setPreguntas(request.queryParams("preguntaDador"), request.queryParams("preguntaAdoptante"), request.session().attribute("es_obligatoria"));

    for(int i = 1; i <= Integer.parseInt(respuestasPosibles); i++) {
      this.borradorParDePreguntas.agregarRespuestaPosibleDador(request.queryParams("respuestaPosibleDador".concat(String.valueOf(i))));
      this.borradorParDePreguntas.agregarRespuestaPosibleAdoptante(request.queryParams("respuestaPosibleAdoptante".concat(String.valueOf(i))));
    }

    List<Integer> cantidadRespuestasPosibles = IntStream.rangeClosed(1, Integer.parseInt(respuestasPosibles)).boxed().collect(Collectors.toList());
    Map<String, Object> modelo = getMap(request);
    if(!idAsociacion.contentEquals("0")) {
      modelo.put("asociacion", repositorioAsociaciones.buscarPorId(this.borradorParDePreguntas.getAsociacionId()).get(0));
    }
    modelo.put("cantidadRespuestasPosibles", cantidadRespuestasPosibles);
    modelo.put("respuestasPosiblesDador", this.borradorParDePreguntas.getRespuestasPosiblesDelDador());
    modelo.put("respuestasPosiblesAdoptante", this.borradorParDePreguntas.getRespuestasPosiblesDelAdoptante());
    modelo.put("redirectId", this.borradorParDePreguntas.getAsociacionId());

    return new ModelAndView(modelo, "nueva-pregunta-2.html.hbs");
  }

  public Void crearParDePreguntasAsociacion(Request request, Response response) {
    for(int i = 1; i <= Integer.parseInt(respuestasPosibles); i++) {
      this.borradorParDePreguntas.agregarParDeRespuestas(
          new ParDeRespuestas(
          request.queryParams("respuestaPosibleDador".concat(String.valueOf(i))),
          request.queryParams("respuestaPosibleAdoptante".concat(String.valueOf(i)))
          )
        );
    }

    withTransaction(() -> {
      if(this.borradorParDePreguntas.getEsObligatoria()) {
        ParDePreguntas parDePreguntas = this.borradorParDePreguntas.crearParDePreguntas();
        repositorioAsociaciones.getAsociaciones().stream().forEach(asociacion -> {
          asociacion.agregarPregunta(parDePreguntas);
        });
      } else {
        Asociacion asociacion = repositorioAsociaciones.buscarPorId(this.borradorParDePreguntas.getAsociacionId()).get(0);
        asociacion.agregarPregunta(this.borradorParDePreguntas.crearParDePreguntas());
      }
    });

    response.redirect("/asociaciones/".concat((String.valueOf(this.borradorParDePreguntas.getAsociacionId())).concat("/preguntas")));
    request.session().attribute("es_obligatoria", null);
    return null;
  }

}
