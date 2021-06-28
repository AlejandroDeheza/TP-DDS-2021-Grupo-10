package modelo.pregunta;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPreguntas {
  private List<ParDePreguntas> preguntasObligatorias = new ArrayList<>();
  private static RepositorioPreguntas repositorioPreguntas = new RepositorioPreguntas();

  public RepositorioPreguntas() {
  }

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
