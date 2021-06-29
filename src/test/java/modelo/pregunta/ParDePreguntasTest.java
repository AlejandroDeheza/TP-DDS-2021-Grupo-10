package modelo.pregunta;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ParDePreguntasTest {

  RepositorioPreguntas repositorioPreguntas;
  ParDePreguntas parDePreguntas;

  @BeforeEach
  public void loadContext() {
    repositorioPreguntas = new RepositorioPreguntas();
  }

  @Test
  @DisplayName("Se puede agregar una pregunta al repositorio de preguntas")
  public void agregarParDePreguntasAlRepositorio() {
    parDePreguntas = getParDePreguntas();
    assertEquals(0, repositorioPreguntas.getPreguntasObligatorias().size());
    repositorioPreguntas.agregarPregunta(parDePreguntas);
    assertEquals(1, repositorioPreguntas.getPreguntasObligatorias().size());
  }

  private ParDePreguntas getParDePreguntas(){
    ParDePreguntas preguntas = new ParDePreguntas("La mascota sufre si está mucho tiempo sola?", "Va a estar la mascota mucho tiempo sola?");
    ParDeRespuestas respuesta1 = new ParDeRespuestas("Si", "No");
    ParDeRespuestas respuesta2 = new ParDeRespuestas("No", "Si");
    ParDeRespuestas respuesta3 = new ParDeRespuestas("Si", "Si");
    ParDeRespuestas respuesta4 = new ParDeRespuestas("No", "No");
    preguntas.agregarRespuesta(respuesta1);
    preguntas.agregarRespuesta(respuesta2);
    preguntas.agregarRespuesta(respuesta3);
    preguntas.agregarRespuesta(respuesta4);
    return preguntas;
  }

}
