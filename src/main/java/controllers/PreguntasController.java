package controllers;

import modelo.asociacion.Asociacion;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.ParDeRespuestas;
import repositorios.RepositorioAsociaciones;
import repositorios.RepositorioParDePreguntas;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PreguntasController extends Controller {

  private RepositorioAsociaciones repoAsociaciones = new RepositorioAsociaciones();
  private RepositorioParDePreguntas repositorioParDePreguntas = new RepositorioParDePreguntas();
  private static final int totalRespuestasPosibles = 5;

  public ModelAndView mostrarPreguntasDeLaAsociacion(Request request, Response response) {
    String idAsociacion = request.params(":idAsociacion");
    List<ParDePreguntas> paresDePreguntas;
    Map<String, Object> modelo = getMap(request);

    if(idAsociacion.equals("0")) {
      paresDePreguntas = repositorioParDePreguntas.getPreguntasObligatorias();
    } else {
      Asociacion asociacionBuscada = repoAsociaciones.buscarPorId(Long.parseLong(idAsociacion));
      validarAsociacionSolicitada(request, response, asociacionBuscada);
      paresDePreguntas = asociacionBuscada.getPreguntas();
      modelo.put("asociacion", asociacionBuscada);
    }

    List<ParDePreguntas> paresDePreguntasOrdenadas = paresDePreguntas.stream()
        .sorted((p1, p2) -> super.porOrdenAlfabetico(p1.getConcepto(), p2.getConcepto())).collect(Collectors.toList());

    modelo.put("asociaciones", super.getAsociacionesOrdenadas());
    modelo.put("preguntas", paresDePreguntasOrdenadas);
    modelo.put("esObligatoria", idAsociacion.equals("0"));
    modelo.put("mostrarBotonAgregarPreguntas", true);
    return new ModelAndView(modelo, "preguntas-asociaciones.html.hbs");
  }

  public ModelAndView mostrarFomularioNuevaPregunta(Request request, Response response) {
    if (!request.params(":idAsociacion").equals("0")) {
      Asociacion asociacionBuscada = repoAsociaciones.buscarPorId(Long.parseLong(request.params(":idAsociacion")));
      validarAsociacionSolicitada(request, response, asociacionBuscada);
    }
    Map<String, Object> modelo = getMap(request);
    modelo.put("idAsociacion", request.params(":idAsociacion"));
    modelo.put("rangoDeRespuestas", super.obtenerRango(totalRespuestasPosibles));
    return new ModelAndView(modelo, "nueva-pregunta.html.hbs");
  }

  public ModelAndView mostrarFormularioNuevaPreguntaContinuacion(Request request, Response response) {
    // Si el "concepto" es null es porque no viene de la pantalla que deveria, o sea la pantalla del form anterior
    if (request.queryParams("concepto") == null) {
      response.redirect("/asociaciones");
      return null;
    }
    Map<String, Object> modelo = getMap(request);
    String idAsociacion = request.params(":idAsociacion");
    if(!idAsociacion.equals("0")) {
      Asociacion asociacionBuscada = repoAsociaciones.buscarPorId(Long.parseLong(idAsociacion));
      validarAsociacionSolicitada(request, response, asociacionBuscada);
      modelo.put("asociacion", asociacionBuscada);
    }

    List<String> respuestasPosiblesDelDador = new ArrayList<>();
    List<String> respuestasPosiblesDelAdoptante = new ArrayList<>();

    super.obtenerRango(totalRespuestasPosibles).forEach(i -> {
      respuestasPosiblesDelDador.add(request.queryParams("respuestaPosibleDador".concat(String.valueOf(i))));
      respuestasPosiblesDelAdoptante.add(request.queryParams("respuestaPosibleAdoptante".concat(String.valueOf(i))));
    });

    respuestasPosiblesDelDador.removeAll(Collections.singleton(""));
    respuestasPosiblesDelAdoptante.removeAll(Collections.singleton(""));

    if(respuestasPosiblesDelDador.size() <= 1 || respuestasPosiblesDelAdoptante.size() <= 1) {
      super.redireccionCasoError(request, response, "Debe ingresar mas de una respuesta posible");
    }

    BorradorParDePreguntas borradorParDePreguntas = new BorradorParDePreguntas(
        Long.parseLong(request.params(":idAsociacion")),
        request.queryParams("concepto"),
        request.queryParams("preguntaDador"),
        request.queryParams("preguntaAdoptante"),
        idAsociacion.equals("0"),
        respuestasPosiblesDelDador,
        respuestasPosiblesDelAdoptante
    );

    modelo.put("cantidadRespuestasPosibles", super.obtenerRango(totalRespuestasPosibles));
    modelo.put("respuestasPosiblesDador", borradorParDePreguntas.getRespuestasPosiblesDelDador());
    modelo.put("respuestasPosiblesAdoptante", borradorParDePreguntas.getRespuestasPosiblesDelAdoptante());
    modelo.put("asociacionId", borradorParDePreguntas.getAsociacionId());
    modelo.put("preguntaDador", borradorParDePreguntas.getPreguntaDelDador());
    modelo.put("preguntaAdoptante", borradorParDePreguntas.getPreguntaDelAdoptante());

    request.session().attribute("borrador_par_preguntas", borradorParDePreguntas);
    return new ModelAndView(modelo, "nueva-pregunta-2.html.hbs");
  }

  public Void crearParDePreguntasAsociacion(Request request, Response response) {
    List<ParDeRespuestas> paresDeRespuestas = new ArrayList<>();

    super.obtenerRango(totalRespuestasPosibles).forEach(i -> {
      paresDeRespuestas.add(
          new ParDeRespuestas(
              request.queryParams("respuestaPosibleDador".concat(String.valueOf(i))),
              request.queryParams("respuestaPosibleAdoptante".concat(String.valueOf(i)))
          )
      );
    });

    BorradorParDePreguntas borradorParDePreguntas = request.session().attribute("borrador_par_preguntas");

    List<ParDeRespuestas> paresDeRespuestasFiltradas =
        paresDeRespuestas.stream().filter(
            par -> !par.getRespuestaDelDador().equals("Elegir respuesta posible dador...")
                && !par.getRespuestaDelAdoptante().equals("Elegir respuesta posible adoptante...")
        ).collect(Collectors.toList());

    ParDePreguntas parDePreguntas = new ParDePreguntas(
        borradorParDePreguntas.getConcepto(),
        borradorParDePreguntas.getPreguntaDelDador(),
        borradorParDePreguntas.getPreguntaDelAdoptante(),
        borradorParDePreguntas.getEsPreguntaObligatoria(),
        borradorParDePreguntas.getRespuestasPosiblesDelDador(),
        borradorParDePreguntas.getRespuestasPosiblesDelAdoptante(),
        paresDeRespuestasFiltradas
    );

    withTransaction(() -> {
      repositorioParDePreguntas.agregar(parDePreguntas);
      if(!borradorParDePreguntas.getEsPreguntaObligatoria()) {
        Asociacion asociacion = repoAsociaciones.buscarPorId(borradorParDePreguntas.getAsociacionId());
        asociacion.agregarPregunta(parDePreguntas);
      }
    });

    request.session().removeAttribute("borrador_par_preguntas");
    response.redirect("/asociaciones/".concat(request.params(":idAsociacion")).concat("/preguntas"));
    return null;
  }

  private void validarAsociacionSolicitada(Request request, Response response, Asociacion asociacionBuscada) {
    if (asociacionBuscada == null) {
      redireccionCasoError(request, response, "La asociacion solicitada no es valida");
    }
  }

}
