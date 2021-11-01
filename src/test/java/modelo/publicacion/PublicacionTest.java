package modelo.publicacion;

import modelo.notificacion.NotificadorCorreo;
import modelo.notificacion.TipoNotificadorPreferido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PublicacionTest {

  NotificadorCorreo notificadorCorreo;
  TipoNotificadorPreferido tipoNotificadorPreferido;

  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
    tipoNotificadorPreferido = mock(TipoNotificadorPreferido.class);
    when(tipoNotificadorPreferido.getNotificador(any())).thenReturn(notificadorCorreo);
  }

  @Test
  @DisplayName("Si un usuario encuentra a su mascota en una publicacion, se envia una Notificacion al rescatista")
  public void encontrarUnaMascotaPerdidaEnviaUnaNotificacion() {
    Rescate publicacion = DummyData.getPublicacionDeRescate(tipoNotificadorPreferido);
    publicacion.notificarAlPublicador(DummyData.getUsuario(tipoNotificadorPreferido));
    verify(notificadorCorreo, times(1)).notificarDuenioReclamaSuMacota(any());
  }

  @Test
  @DisplayName("Si un adoptante desea adoptar una mascota de una publicacion, se envia una Notificacion al duenio")
  public void procesarUnaPublicacionDeDarEnAdopcionEnviaUnaNotificacion() {
    DarEnAdopcion publicacion = DummyData.getPublicacionDeDarEnAdopcion(tipoNotificadorPreferido);
    publicacion.notificarAlPublicador(DummyData.getUsuario(tipoNotificadorPreferido));
    verify(notificadorCorreo, times(1)).notificarQuierenAdoptarTuMascota(any(), any());
  }

}
