package modelo.publicacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import servicio.notificacion.NotificacionCorreo;
import utils.DummyData;

import javax.mail.MessagingException;
import javax.mail.Transport;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PublicacionTest {
  Transport transportMockeado;
  NotificacionCorreo notificacionCorreo;

  @BeforeEach
  public void contextLoad() {
    transportMockeado = mock(Transport.class);
    notificacionCorreo = new NotificacionCorreo(session -> transportMockeado);
  }

  @Test
  @DisplayName("si se encuentra una Mascota, se envia una Notificacion")
  public void encontrarUnaMascotaPerdidaEnviaUnaNotificacion() throws MessagingException {
    Publicacion publicacion = DummyData.getDummyPublicacion(notificacionCorreo);
    publicacion.notificarEncuentroAlRescatista(DummyData.getDummyUsuario());
    verify(transportMockeado).sendMessage(any(), any());
  }
}
