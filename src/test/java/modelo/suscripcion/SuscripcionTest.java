package modelo.suscripcion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import modelo.asociacion.Asociacion;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.pregunta.RespuestaDelAdoptante;
import modelo.usuario.Usuario;
import utils.DummyData;
import utils.MockNotificador;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SuscripcionTest extends NuestraAbstractPersistenceTest {

  MockNotificador mockNotificador;
  SuscripcionParaAdopcion suscripcion;
//  Preferencia preferencia = new Preferencia(DummyData.getCaracteristicasParaMascota(), Animal.GATO);

  @BeforeEach
  public void contextLoad() {
    mockNotificador = DummyData.getMockNotificador();
    suscripcion = DummyData.getSuscripcionParaAdopcion(mockNotificador.getTipo());
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

    entityManager().persist(suscripcion);
    assertEquals(1, entityManager().createQuery("from SuscripcionParaAdopcion", SuscripcionParaAdopcion.class).getResultList().size());
    assertEquals(1, entityManager().createQuery("from Usuario", Usuario.class).getResultList().size());

    entityManager().remove(suscripcion);
    assertEquals(0, entityManager().createQuery("from SuscripcionParaAdopcion", SuscripcionParaAdopcion.class).getResultList().size());
    assertEquals(usuarioAsociado.getId(), entityManager().createQuery("from Usuario", Usuario.class).getResultList().get(0).getId());
  }

  @Test
  @DisplayName("Al eliminar una SuscripcionParaAdopcion, no se elimina su Asociación asociada")
  public void eliminarUnaSuscripcionParaAdopcionNoEliminaSuAsociacionAsociada() {
    Asociacion asociacion = suscripcion.getAsociacion();

    entityManager().persist(suscripcion);
    assertEquals(1, entityManager().createQuery("from SuscripcionParaAdopcion", SuscripcionParaAdopcion.class).getResultList().size());
    assertEquals(1, entityManager().createQuery("from Asociacion", Asociacion.class).getResultList().size());

    entityManager().remove(suscripcion);
    assertEquals(0, entityManager().createQuery("from SuscripcionParaAdopcion", SuscripcionParaAdopcion.class).getResultList().size());
    assertEquals(asociacion.getId(), entityManager().createQuery("from Asociacion", Asociacion.class).getResultList().get(0).getId());
  }

  @Test
  @DisplayName("Al eliminar una SuscripcionParaAdopcion, se elimina sus RespuestaDelAdoptante asociadas")
  public void eliminarUnaSuscripcionParaAdopcionEliminaSusRespuestaDelAdoptanteAsociadas() {
    List<RespuestaDelAdoptante> respuestasDelAdoptante = suscripcion.getComodidadesDelAdoptante();

    entityManager().persist(suscripcion);
    assertEquals(1, entityManager().createQuery("from SuscripcionParaAdopcion", SuscripcionParaAdopcion.class).getResultList().size());
    assertEquals(respuestasDelAdoptante.get(0).getId(), entityManager().createQuery("from RespuestaDelAdoptante", RespuestaDelAdoptante.class).getResultList().get(0).getId());

    entityManager().remove(suscripcion);
    assertEquals(0, entityManager().createQuery("from SuscripcionParaAdopcion", SuscripcionParaAdopcion.class).getResultList().size());
    assertEquals(0, entityManager().createQuery("from RespuestaDelAdoptante", RespuestaDelAdoptante.class).getResultList().size());
  }

  @Test
  @DisplayName("Al eliminar una SuscripcionParaAdopcion, se elimina la lista de Características asociada a la Preferencia del adoptante")
  public void eliminarUnaPreferenciaEliminaLaListaDeCaracteristicasAsociada() {
    entityManager().persist(suscripcion);
    assertEquals(1, entityManager().createQuery("from SuscripcionParaAdopcion", SuscripcionParaAdopcion.class).getResultList().size());
    assertEquals(1, entityManager().createQuery("from Caracteristica", Caracteristica.class).getResultList().size());

    entityManager().remove(suscripcion);
    assertEquals(0, entityManager().createQuery("from SuscripcionParaAdopcion", SuscripcionParaAdopcion.class).getResultList().size());
    assertEquals(0, entityManager().createQuery("from Caracteristica", Caracteristica.class).getResultList().size());
  }
}
