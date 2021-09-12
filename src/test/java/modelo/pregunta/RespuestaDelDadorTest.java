package modelo.pregunta;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RespuestaDelDadorTest {
  ParDePreguntas parDePreguntas = DummyData.getParDePreguntas1();
  List<RespuestaDelAdoptante> respuestasDelAdoptante = new ArrayList<>();

  @BeforeEach
  public void contextLoad() {
    respuestasDelAdoptante.add(new RespuestaDelAdoptante("No", parDePreguntas));
  }

  @Test
  @DisplayName("Una Respuesta matchea con alguna de las respuestas")
  public void respuestaMatcheaConAlgunaDeLasRespuestas() {
    RespuestaDelDador respuestaDelDador = new RespuestaDelDador("Si", parDePreguntas);
    assertTrue(respuestaDelDador.correspondeConAlguna(respuestasDelAdoptante));
  }

  @Test
  @DisplayName("Una Respuesta no matchea con alguna de las respuestas")
  public void respuestaNoMatcheaConAlgunaDeLasRespuestas() {
    RespuestaDelDador respuestaDelDador = new RespuestaDelDador("Bla", parDePreguntas);
    assertFalse(respuestaDelDador.correspondeConAlguna(respuestasDelAdoptante));
  }
}
