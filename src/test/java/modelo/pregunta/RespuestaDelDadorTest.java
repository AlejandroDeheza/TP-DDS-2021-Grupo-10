package modelo.pregunta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import utils.DummyData;

public class RespuestaDelDadorTest extends NuestraAbstractPersistenceTest {
  ParDePreguntas parDePreguntas = DummyData.getParDePreguntas1();
  RespuestaDelDador respuestaDelDador = new RespuestaDelDador("No", parDePreguntas);

  @Test
  @DisplayName("Al eliminar una RespuestaDelDador, no se elimina el ParDePreguntas asociado")
  public void eliminarUnaRespuestaDelDadorNoEliminaAlParDePreguntasAsociado() {
    ParDePreguntas unParDePreguntas = respuestaDelDador.getParDePreguntas();

    entityManager().persist(respuestaDelDador);
    assertEquals(1, entityManager().createQuery("from RespuestaDelDador", RespuestaDelDador.class).getResultList().size());
    assertEquals(1, entityManager().createQuery("from ParDePreguntas", ParDePreguntas.class).getResultList().size());

    entityManager().remove(respuestaDelDador);
    assertEquals(0, entityManager().createQuery("from RespuestaDelDador", RespuestaDelDador.class).getResultList().size());
    assertEquals(unParDePreguntas.getId(), entityManager().createQuery("from ParDePreguntas", ParDePreguntas.class).getResultList().get(0).getId());
  }
}
