package modelo.suscripcion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import modelo.asociacion.Asociacion;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.pregunta.RespuestaDelAdoptante;
import modelo.usuario.Usuario;
import utils.CascadeTypeCheck;
import utils.DummyData;
import utils.MockNotificador;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SuscripcionTest extends NuestraAbstractPersistenceTest {

  MockNotificador mockNotificador;
  SuscripcionParaAdopcion suscripcion;
  CascadeTypeCheck cascadeTypeCheck;

  @BeforeEach
  public void contextLoad() {
    mockNotificador = DummyData.getMockNotificador();
    suscripcion = DummyData.getSuscripcionParaAdopcion(mockNotificador.getTipo());
    cascadeTypeCheck = new CascadeTypeCheck(suscripcion);
  }

  @Test
  @DisplayName("Si se envia el link de baja al posteador, se envia una Notificacion al posteador")
  public void enviarUnLinkDeBajaEnviaUnaNotificacion() {
    suscripcion.enviarLinkDeBaja();
    verify(mockNotificador.getNotificador(), times(1)).notificarLinkDeBajaSuscripcionAdopciones(any());
  }

  @Test
  @DisplayName("Si se envian recomendacion de adopcion, se envia una Notificacion al adoptante")
  public void enviarRecomendacionesEnviaUnaNotificacion() {
    suscripcion.enviarRecomendaciones(
        Arrays.asList(
            DummyData.getPublicacionDeDarEnAdopcion(mockNotificador.getTipo()),
            DummyData.getPublicacionDeDarEnAdopcion(mockNotificador.getTipo())
        )
    );
    verify(mockNotificador.getNotificador(), times(1)).notificarRecomendacionesDeAdopciones(any());
  }

  @Test
  @DisplayName("Si se envian recomendacion de adopcion con lista vacia, no se envia ninguna Notificacion")
  public void enviarRecomendacionesConListaVaciaNoEnviaUnaNotificacion() {
    suscripcion.enviarRecomendaciones(Collections.emptyList());
    verify(mockNotificador.getNotificador(), times(0)).notificarRecomendacionesDeAdopciones(any());
  }

  @Test
  @DisplayName("Al eliminar una SuscripcionParaAdopcion, no se elimina su Usuario suscriptor asociado")
  public void eliminarUnaSuscripcionParaAdopcionNoEliminaSuUsuarioSuscriptorAsociado() {
    Usuario usuarioAsociado = suscripcion.getSuscriptor();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(usuarioAsociado, 1, 1, 0, 1));
    assertEquals(usuarioAsociado.getId(), entityManager().createQuery("from Usuario", Usuario.class).getResultList().get(0).getId());
  }

  @Test
  @DisplayName("Al eliminar una SuscripcionParaAdopcion, no se elimina su Asociación asociada")
  public void eliminarUnaSuscripcionParaAdopcionNoEliminaSuAsociacionAsociada() {
    Asociacion asociacion = suscripcion.getAsociacion();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(asociacion, 1, 1, 0, 1));
    assertEquals(asociacion.getId(), entityManager().createQuery("from Asociacion", Asociacion.class).getResultList().get(0).getId());
  }

  @Test
  @DisplayName("Al eliminar una SuscripcionParaAdopcion, se elimina sus RespuestaDelAdoptante asociadas")
  public void eliminarUnaSuscripcionParaAdopcionEliminaSusRespuestaDelAdoptanteAsociadas() {
    List<RespuestaDelAdoptante> respuestasDelAdoptante = suscripcion.getComodidadesDelAdoptante();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(respuestasDelAdoptante, 1, 2, 0, 0));
  }

  @Test
  @DisplayName("Al eliminar una SuscripcionParaAdopcion, se elimina la lista de Características asociada a la Preferencia del adoptante")
  public void eliminarUnaPreferenciaEliminaLaListaDeCaracteristicasAsociada() {
    List<Caracteristica> caracteristicas = suscripcion.getPreferenciaDelAdoptante().getCaracteristicas();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(caracteristicas, 1, 1, 0, 0));
  }
}
