package modelo.pregunta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import utils.CascadeTypeCheck;
import utils.DummyData;

public class RespuestaDelDadorTest extends NuestraAbstractPersistenceTest {
  ParDePreguntas parDePreguntas = DummyData.getParDePreguntas1();
  RespuestaDelAdoptante respuestaDelAdoptante = new RespuestaDelAdoptante("No", parDePreguntas);
  RespuestaDelDador respuestaDelDador = new RespuestaDelDador("No", parDePreguntas);
  List<RespuestaDelAdoptante> respuestasDelAdoptante = new ArrayList<>();
  CascadeTypeCheck cascadeTypeCheck;

  @BeforeEach
  public void contextLoad() {
    respuestasDelAdoptante.add(respuestaDelAdoptante);
    cascadeTypeCheck = new CascadeTypeCheck();
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

  @Test
  @DisplayName("Al eliminar una RespuestaDelDador, no se elimina el ParDePreguntas asociado")
  public void eliminarUnaRespuestaDelDadorNoEliminaAlParDePreguntasAsociado() {
    ParDePreguntas unParDePreguntas = respuestaDelDador.getParDePreguntas();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(respuestaDelDador, unParDePreguntas, 1, 1, 0, 1));
    assertEquals(unParDePreguntas.getId(), entityManager().createQuery("from ParDePreguntas", ParDePreguntas.class).getResultList().get(0).getId());
  }
}
