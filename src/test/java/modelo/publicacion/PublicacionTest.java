package modelo.publicacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import modelo.notificacion.NotificadorCorreo;
import utils.DummyData;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PublicacionTest {

  @BeforeEach
  public void contextLoad() {}

  @Test
  @DisplayName("Si un usuario encuentra a su mascota en una publicacion, se envia una Notificacion al rescatista")
  public void encontrarUnaMascotaPerdidaEnviaUnaNotificacion() {
    NotificadorCorreo notificadorCorreo = mock(NotificadorCorreo.class);
    DummyData.getPublicacion(notificadorCorreo).notificarEncuentroAlRescatista(DummyData.getUsuario());
    verify(notificadorCorreo).notificar(any());
  }
}
