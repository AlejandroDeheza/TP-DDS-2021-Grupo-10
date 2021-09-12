package repositorios;

import modelo.pregunta.ParDePreguntas;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPreguntasObligatorias {
  private List<ParDePreguntas> preguntasObligatorias = new ArrayList<>();
  private static RepositorioPreguntasObligatorias repo = new RepositorioPreguntasObligatorias();

  public List<ParDePreguntas> getPreguntas() {
    return preguntasObligatorias;
  }

  public void agregarPregunta(ParDePreguntas pregunta) {
    preguntasObligatorias.add(pregunta);
  }

  public void eliminarPregunta(ParDePreguntas pregunta) {
    preguntasObligatorias.remove(pregunta);
  }


  //el repositorio, en codigo de produccion, lo inyectamos por constructor
  //usamos el constructor solo para tests
  public RepositorioPreguntasObligatorias() {
  }
  //usamos el getInstance en Main
  public static RepositorioPreguntasObligatorias getInstance() {
    return repo;
  }
}
