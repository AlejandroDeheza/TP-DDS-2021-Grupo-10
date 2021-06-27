package modelo.asociacion;

import modelo.pregunta.ParDePreguntas;

import java.util.ArrayList;
import java.util.List;

public class RepositorioAsociaciones {
  private List<Asociacion> asociaciones = new ArrayList<>();
  private List<ParDePreguntas> preguntasGeneralesParaTodasLasAsociaciones = new ArrayList<>();
  private static RepositorioAsociaciones repositorioAsociaciones = new RepositorioAsociaciones();

  public RepositorioAsociaciones() {
  }

  public static RepositorioAsociaciones getInstance() {
    return repositorioAsociaciones;
  }

  public List<Asociacion> getAsociaciones() {
    return asociaciones;
  }

  public List<ParDePreguntas> getPreguntasGeneralesParaTodasLasAsociaciones() {
    return preguntasGeneralesParaTodasLasAsociaciones;
  }

  public void agregarPregunta(ParDePreguntas pregunta) {
    preguntasGeneralesParaTodasLasAsociaciones.add(pregunta);
  }

  public void eliminarPregunta(ParDePreguntas pregunta) {
    preguntasGeneralesParaTodasLasAsociaciones.remove(pregunta);
  }
}
