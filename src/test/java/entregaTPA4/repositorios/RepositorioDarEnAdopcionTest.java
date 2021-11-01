package entregaTPA4.repositorios;

import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import modelo.notificacion.Notificador;
import modelo.notificacion.NotificadorCorreo;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.publicacion.DarEnAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import utils.DummyData;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositorioDarEnAdopcionTest extends NuestraAbstractPersistenceTest {

  RepositorioDarEnAdopcion repository = new RepositorioDarEnAdopcion();
  TipoNotificadorPreferido tipoNotificadorPreferido;
  Notificador notificadorMockeado;

  @BeforeEach
  public void contextLoad() {
    tipoNotificadorPreferido = mock(TipoNotificadorPreferido.class);
    when(tipoNotificadorPreferido.getNotificador(any())).thenReturn(notificadorMockeado);
  }

  @Test
  @DisplayName("Agregar una publicacion de adopcion, aumenta el numero de publicaciones en uno")
  public void agregarPublicacionDeAdopcionTest(){
    DarEnAdopcion publicacionDeDarEnAdopcion =
        DummyData.getPublicacionDeDarEnAdopcion(tipoNotificadorPreferido);
    entityManager().persist(publicacionDeDarEnAdopcion);
    assertEquals(1,  repository.getPublicaciones().size());
  }
}
