package modelo.pregunta;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class RespuestaTest {
  ParDePreguntas parDePreguntas = DummyData.getParDePreguntas1();
  Respuesta respuesta = new Respuesta("Si", parDePreguntas);
  List<Respuesta> =

  @BeforeEach
  public void contextLoad() {
  }

  @Test
  @DisplayName("Una Respuesta matchea con alguna de las respuestas")
  public void respuestaMatcheaConAlgunaDeLasRespuestas() {
    assertTrue(respuesta.matcheaConAlguna());
  }

  @Test
  @DisplayName("Una Respuesta no matchea con alguna de las respuestas")
  public void respuestaNoMatcheaConAlgunaDeLasRespuestas() {
  }
}
