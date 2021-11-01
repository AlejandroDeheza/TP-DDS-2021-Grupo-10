package entregaTPA4.repositorios;

import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import modelo.notificacion.Notificador;
import modelo.notificacion.NotificadorCorreo;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.suscripcion.SuscripcionParaAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RepositorioSuscripcionesParaAdopcionesTest extends NuestraAbstractPersistenceTest {

  RepositorioSuscripcionesParaAdopciones repository = new RepositorioSuscripcionesParaAdopciones();

  TipoNotificadorPreferido tipoNotificadorPreferido;
  Notificador notificadorMockeado;

  @BeforeEach
  public void contextLoad() {
    tipoNotificadorPreferido = mock(TipoNotificadorPreferido.class);
    when(tipoNotificadorPreferido.getNotificador(any())).thenReturn(notificadorMockeado);
  }

  @Test
  @DisplayName("Agregar una suscripcion aumenta el numero de suscripciones en una")
  public void agregarSuscripcionTest(){
    SuscripcionParaAdopcion suscripcionParaAdopcion =
        DummyData.getSuscripcionParaAdopcion(tipoNotificadorPreferido);
    int cantidadSuscripcionesPrevia = repository.getSuscripciones().size();
    entityManager().persist(suscripcionParaAdopcion);
    assertEquals(cantidadSuscripcionesPrevia +1, repository.getSuscripciones().size());
  }

  @Test
  @DisplayName("Dar de baja una suscripcion recibe una suscripcion con el flag isActivo cambiado y updatea el registro en la DB")
  public void darDeBajaSuscripcionTest(){
    SuscripcionParaAdopcion suscripcionParaAdopcion =
        DummyData.getSuscripcionParaAdopcion(tipoNotificadorPreferido);
    entityManager().persist(suscripcionParaAdopcion);
    suscripcionParaAdopcion.desactivar();
    assertEquals(1,  repository.getSuscripcionesDeBaja().size());
  }

}
