package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import modelo.asociacion.Asociacion;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.ParDeRespuestas;
import repositorios.RepositorioAsociaciones;
import repositorios.RepositorioParDePreguntas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class PreguntasController extends Controller {

  private RepositorioAsociaciones repositorioAsociaciones = new RepositorioAsociaciones();
  private RepositorioParDePreguntas repositorioParDePreguntas = new RepositorioParDePreguntas();
  private final String totalRespuestasPosibles = "5";

  public ModelAndView mostrarPreguntasDeLaAsociacion(Request request, Response response) {
    String idAsociacion = request.params(":idAsociacion");
    List<ParDePreguntas> paresDePreguntas;
    Map<String, Object> modelo = getMap(request);

    if(idAsociacion.equals("0")) {
      request.session().attribute("es_obligatoria", true);
      paresDePreguntas = repositorioParDePreguntas.getPreguntasObligatorias();
    } else {
      request.session().attribute("es_obligatoria", false);
      Asociacion asociacionBuscada = repositorioAsociaciones.buscarPorId(Long.parseLong(idAsociacion));
      paresDePreguntas = asociacionBuscada.getPreguntas();
      modelo.put("asociacion", asociacionBuscada);
    }

    modelo.put("asociaciones", repositorioAsociaciones.listarTodos());
    modelo.put("preguntas", paresDePreguntas);
    modelo.put("esObligatoria", request.session().attribute("es_obligatoria"));
    modelo.put("mostrarBotonAgregarPreguntas", true);
    return new ModelAndView(modelo, "preguntas-asociaciones.html.hbs");
  }

  public ModelAndView nuevaPregunta(Request request, Response response) {
    String idAsociacion = request.params(":idAsociacion");
    List<Integer> rangoDeRespuestas = this.obtenerRango();
    Map<String, Object> modelo = getMap(request);
    modelo.put("asociacion", idAsociacion);
    modelo.put("rangoDeRespuestas", rangoDeRespuestas);
    return new ModelAndView(modelo, "nueva-pregunta.html.hbs");
  }

  public ModelAndView matchearRespuestasPosibles(Request request, Response response) {
    String idAsociacion = request.params(":idAsociacion");
    
    List<String> listaRespuestasDador = new ArrayList<>();
    List<String> listaRespuestasAdoptante = new ArrayList<>();
    this.obtenerRango().stream().forEach(i -> {
      listaRespuestasDador.add(request.queryParams("respuestaPosibleDador".concat(String.valueOf(i))));
      listaRespuestasAdoptante.add(request.queryParams("respuestaPosibleAdoptante".concat(String.valueOf(i))));
    });

    listaRespuestasDador.removeAll(Collections.singleton(""));
    listaRespuestasAdoptante.removeAll(Collections.singleton(""));
    if(listaRespuestasDador.size() <= 1 || listaRespuestasAdoptante.size() <= 1) {
      super.redireccionCasoError(request, response, null, "Debe ingresar mas de una respuesta posible");
    }

    BorradorParDePreguntas borradorParDePreguntas = new BorradorParDePreguntas();
    borradorParDePreguntas.setAsociacionId(Long.parseLong(idAsociacion));
    borradorParDePreguntas.setPreguntas(
      request.queryParams("preguntaDador"),
      request.queryParams("preguntaAdoptante"),
      request.session().attribute("es_obligatoria")
    );

    this.obtenerRango().stream().forEach(i -> {
      borradorParDePreguntas.agregarRespuestaPosibleDador(
          request.queryParams("respuestaPosibleDador".concat(String.valueOf(i)))
      );
      borradorParDePreguntas.agregarRespuestaPosibleAdoptante(
          request.queryParams("respuestaPosibleAdoptante".concat(String.valueOf(i)))
      );
    });

    Map<String, Object> modelo = getMap(request);
    if(!borradorParDePreguntas.getEsObligatoria()) {
      modelo.put("asociacion", repositorioAsociaciones.buscarPorId(borradorParDePreguntas.getAsociacionId()));
    }
    modelo.put("cantidadRespuestasPosibles", this.obtenerRango());
    modelo.put("respuestasPosiblesDador", borradorParDePreguntas.getRespuestasPosiblesDelDador());
    modelo.put("respuestasPosiblesAdoptante", borradorParDePreguntas.getRespuestasPosiblesDelAdoptante());
    modelo.put("redirectId", borradorParDePreguntas.getAsociacionId());

    request.session().attribute("borrador_par_preguntas", borradorParDePreguntas);
    return new ModelAndView(modelo, "nueva-pregunta-2.html.hbs");
  }

  public Void crearParDePreguntasAsociacion(Request request, Response response) {
    BorradorParDePreguntas borradorParDePreguntas = request.session().attribute("borrador_par_preguntas");
    this.obtenerRango().stream().forEach(i -> {
      borradorParDePreguntas.agregarParDeRespuestas(
        new ParDeRespuestas(
          request.queryParams("respuestaPosibleDador".concat(String.valueOf(i))),
          request.queryParams("respuestaPosibleAdoptante".concat(String.valueOf(i)))
        )
      );
    });

    ParDePreguntas parDePreguntas = borradorParDePreguntas.crearParDePreguntas();
    withTransaction(() -> {
      repositorioParDePreguntas.agregar(parDePreguntas);
      if(!borradorParDePreguntas.getEsObligatoria()) {
        Asociacion asociacion = repositorioAsociaciones.buscarPorId(borradorParDePreguntas.getAsociacionId());
        asociacion.agregarPregunta(parDePreguntas);
      }
    });

    response.redirect("/asociaciones/".concat((String.valueOf(borradorParDePreguntas.getAsociacionId())).concat("/preguntas")));
    request.session().removeAttribute("borrador_par_preguntas");
    request.session().removeAttribute("es_obligatoria");
    return null;
  }

  private List<Integer> obtenerRango() {
    return IntStream.rangeClosed(1, Integer.parseInt(totalRespuestasPosibles)).boxed().collect(Collectors.toList());
  }

}
