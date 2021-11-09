package modelo.pregunta;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import utils.CascadeTypeCheck;
import utils.DummyData;

public class RespuestaDelAdoptanteTest extends NuestraAbstractPersistenceTest {
  ParDePreguntas parDePreguntas = DummyData.getParDePreguntas1();
  RespuestaDelAdoptante respuestaDelAdoptante = new RespuestaDelAdoptante("No", parDePreguntas);
  CascadeTypeCheck cascadeTypeCheck = new CascadeTypeCheck(respuestaDelAdoptante);

  @Test
  @DisplayName("Al eliminar una RespuestaDelAdoptante, no se elimina el ParDePreguntas asociado")
  public void eliminarUnaRespuestaDelAdoptanteNoEliminaAlParDePreguntasAsociado() {
    ParDePreguntas unParDePreguntas = respuestaDelAdoptante.getParDePreguntas();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(unParDePreguntas, 1, 1, 0, 1));
    assertEquals(unParDePreguntas.getId(), entityManager().createQuery("from ParDePreguntas", ParDePreguntas.class).getResultList().get(0).getId());
  }
}
