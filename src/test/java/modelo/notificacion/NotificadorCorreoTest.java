package modelo.notificacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import javax.mail.Transport;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NotificadorCorreoTest {

  @BeforeEach
  public void contextLoad() {
  }

  @Test
  @DisplayName("notificar")
  public void notificarTest() throws MessagingException {
    Transport transportMockeado = mock(Transport.class);
    NotificadorCorreo notificadorCorreo = new NotificadorCorreo("dds2021g10@gmail.com", sesion -> transportMockeado);
    notificadorCorreo.notificarLinkDeBajaSuscripcionAdopciones("");

    verify(transportMockeado, times(1)).connect(any(), any());
    verify(transportMockeado, times(1)).sendMessage(any(), any());
    verify(transportMockeado, times(1)).close();

    verify(transportMockeado, atMostOnce()).connect(any(), any());
    verify(transportMockeado, atMostOnce()).sendMessage(any(), any());
    verify(transportMockeado, atMostOnce()).close();
  }

}
