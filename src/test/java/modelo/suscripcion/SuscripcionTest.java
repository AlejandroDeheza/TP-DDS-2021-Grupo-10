package modelo.suscripcion;

import modelo.notificacion.NotificadorCorreo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import utils.DummyData;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SuscripcionTest {

  NotificadorCorreo notificadorCorreo;

  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
  }

  @Test
  @DisplayName("Si se envia el link de baja al posteador, se envia una Notificacion al posteador")
  public void enviarUnLinkDeBajaEnviaUnaNotificacion() {
    SuscripcionAdopciones suscripcion = DummyData.getSuscripcionAdopciones(notificadorCorreo);
    suscripcion.enviarLinkDeBaja();
    verify(notificadorCorreo, times(1)).notificar(any());
  }

  @Test
  @DisplayName("Si se envian recomendacion de adopcion, se envia una Notificacion al adoptante")
  public void enviarRecomendacionesEnviaUnaNotificacion() {
    SuscripcionAdopciones suscripcion = DummyData.getSuscripcionAdopciones(notificadorCorreo);
    suscripcion.enviarRecomendaciones(
        Arrays.asList(
            DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()),
            DummyData.getMascotaRegistrada(new RepositorioCaracteristicas())
        )
    );
    verify(notificadorCorreo, times(1)).notificar(any());
  }
}
