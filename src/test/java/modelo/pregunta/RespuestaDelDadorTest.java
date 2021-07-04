package modelo.pregunta;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class RespuestaTest {
  ParDePreguntas parDePreguntas = DummyData.getParDePreguntas1();
  List<Respuesta> respuestas = new ArrayList<>();


  @BeforeEach
  public void contextLoad() {
    respuestas.add(new Respuesta("No",parDePreguntas));
  }

  @Test
  @DisplayName("Una Respuesta matchea con alguna de las respuestas")
  public void respuestaMatcheaConAlgunaDeLasRespuestas() {
    Respuesta respuesta = new Respuesta("Si", parDePreguntas);
    assertTrue(respuesta.correspondeConAlguna(respuestas));
  }

  @Test
  @DisplayName("Una Respuesta no matchea con alguna de las respuestas")
  public void respuestaNoMatcheaConAlgunaDeLasRespuestas() {
    Respuesta respuesta = new Respuesta("Bla", parDePreguntas);
    assertFalse(respuesta.correspondeConAlguna(respuestas));
  }
}
