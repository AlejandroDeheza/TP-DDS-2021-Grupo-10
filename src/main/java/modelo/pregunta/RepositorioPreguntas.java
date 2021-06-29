package modelo.pregunta;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPreguntas {
  private List<ParDePreguntas> preguntasObligatorias = new ArrayList<>();
  private static RepositorioPreguntas repositorioPreguntas = new RepositorioPreguntas();

  //el repositorio, en codigo de produccion, lo inyectamos por constructor
  //usamos el constructor solo para tests
  public RepositorioPreguntas() {
  }

  //usamos el getInstance en Main
  public static RepositorioPreguntas getInstance() {
    return repositorioPreguntas;
  }

  public List<ParDePreguntas> getPreguntasObligatorias() {
    return preguntasObligatorias;
  }

  public void agregarPregunta(ParDePreguntas pregunta) {
    preguntasObligatorias.add(pregunta);
  }

  public void eliminarPregunta(ParDePreguntas pregunta) {
    preguntasObligatorias.remove(pregunta);
  }
}
