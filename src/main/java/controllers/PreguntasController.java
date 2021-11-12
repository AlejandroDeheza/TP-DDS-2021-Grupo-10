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

  public class BorradorParDePreguntas {
    private String preguntaDelDador;
    private String preguntaDelAdoptante;
    private Boolean esObligatoria = true;
    private Long asociacionId;

    public BorradorParDePreguntas setPreguntas(String preguntaDelDador, String preguntaDelAdoptante, Boolean esObligatoria) {
      this.preguntaDelDador = preguntaDelDador;
      this.preguntaDelAdoptante = preguntaDelAdoptante;
      this.esObligatoria = esObligatoria;
      return this;
    }

    public BorradorParDePreguntas setAsociacionId(Long asociacionId) {
      this.asociacionId = asociacionId;
      return this;
    }

    public ParDePreguntas crearParDePreguntas() {
      return new ParDePreguntas(this.preguntaDelDador, this.preguntaDelAdoptante, this.esObligatoria);
    }

    public String getPreguntaDelDador() {
      return this.preguntaDelDador;
    }

    public String getPreguntaDelAdoptante() {
      return this.preguntaDelAdoptante;
    }

    public Boolean getEsObligatoria() {
      return this.esObligatoria;
    }

    public Long getAsociacionId() {
      return this.asociacionId;
    }
  }

  RepositorioAsociaciones repositorioAsociaciones = new RepositorioAsociaciones();
  RepositorioPreguntasObligatorias repositorioPreguntasObligatorias = new RepositorioPreguntasObligatorias();
  BorradorParDePreguntas borradorParDePreguntas;

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
    borradorParDePreguntas = new BorradorParDePreguntas();
    Map<String, Object> modelo = getMap(request);
    List<Asociacion> todasLasAsociaciones = repositorioAsociaciones.getAsociaciones();
    modelo.put("asociaciones", todasLasAsociaciones);
    return new ModelAndView(modelo, "nueva-pregunta.html.hbs");
  }
  
  public ModelAndView matchearPreguntasAsociacion(Request request, Response response) {
    String asociacionId = request.queryParams("asociacionId");
    String preguntaDador = request.queryParams("preguntaDador");
    String preguntaAdoptante = request.queryParams("preguntaAdoptante");
    String cantidadMatcheos = request.queryParams("cantidadMatcheos");
    Boolean esObligatoria = request.queryParams("esObligatoria") == null ? false : true;
    Map<String, Object> modelo = getMap(request);
    List<Integer> matcheos = IntStream.rangeClosed(1, Integer.parseInt(cantidadMatcheos)).boxed().collect(Collectors.toList());
    this.borradorParDePreguntas.setPreguntas(preguntaDador, preguntaAdoptante, esObligatoria).setAsociacionId(Long.parseLong(asociacionId));
    modelo.put("asociacionId", asociacionId);
    modelo.put("cantidadMatcheos", matcheos);
    modelo.put("borradorParDePreguntas", borradorParDePreguntas);
    return new ModelAndView(modelo, "matchear-preguntas.html.hbs");
  }

  public Void crearParDePreguntasAsociacion(Request request, Response response) {
    System.out.println("Dador: " + this.borradorParDePreguntas.getPreguntaDelDador());
    System.out.println("Adoptante: " + this.borradorParDePreguntas.getPreguntaDelAdoptante());
    System.out.println("Obligatorio: " + this.borradorParDePreguntas.getEsObligatoria());
    response.redirect("/preguntas-asociaciones");
    return null;
  }

}
