package modelo.pregunta;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import utils.DummyData;
import java.util.ArrayList;
import java.util.List;

public class RespuestaDelAdoptanteTest extends NuestraAbstractPersistenceTest {
  ParDePreguntas parDePreguntas = DummyData.getParDePreguntas1();
  RespuestaDelAdoptante respuestaDelAdoptante = new RespuestaDelAdoptante("No", parDePreguntas);
  List<RespuestaDelAdoptante> respuestasDelAdoptante = new ArrayList<>();

  @BeforeEach
  public void contextLoad() {
    respuestasDelAdoptante.add(respuestaDelAdoptante);
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
  @DisplayName("Al eliminar una RespuestaDelAdoptante, no se elimina el ParDePreguntas asociado")
  public void eliminarUnaRespuestaDelAdoptanteNoEliminaAlParDePreguntasAsociado() {
    ParDePreguntas unParDePreguntas = respuestaDelAdoptante.getParDePreguntas();

    entityManager().persist(respuestaDelAdoptante);
    assertEquals(1, entityManager().createQuery("from RespuestaDelAdoptante", RespuestaDelAdoptante.class).getResultList().size());
    assertEquals(1, entityManager().createQuery("from ParDePreguntas", ParDePreguntas.class).getResultList().size());

    entityManager().remove(respuestaDelAdoptante);
    assertEquals(0, entityManager().createQuery("from RespuestaDelAdoptante", RespuestaDelAdoptante.class).getResultList().size());
    assertEquals(unParDePreguntas.getId(), entityManager().createQuery("from ParDePreguntas", ParDePreguntas.class).getResultList().get(0).getId());
  }
}
