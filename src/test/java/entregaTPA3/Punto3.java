package entregaTPA3;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import modelo.notificacion.NotificadorCorreo;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.pregunta.RespuestaDelDador;
import modelo.publicacion.DarEnAdopcion;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

public class Punto3 {

  TipoNotificadorPreferido tipoNotificadorPreferido;
  NotificadorCorreo notificadorMockeado;

  @BeforeEach
  public void contextLoad() {
    notificadorMockeado = mock(NotificadorCorreo.class);
    tipoNotificadorPreferido = mock(TipoNotificadorPreferido.class);
    when(tipoNotificadorPreferido.getNotificador(any())).thenReturn(notificadorMockeado);
  }

  @Test
  @DisplayName("Si un interesado desea adoptar una mascota de una publicacion, se envia una Notificacion al duenio")
  public void procesarUnaPublicacionDeDarEnAdopcionEnviaUnaNotificacion() {
    DarEnAdopcion publicacion = DummyData.getPublicacionDeDarEnAdopcion(tipoNotificadorPreferido);
    Usuario adoptante = DummyData.getUsuario(tipoNotificadorPreferido);
    publicacion.notificarAlPublicador(adoptante);
    verify(tipoNotificadorPreferido.getNotificador(any()), times(1)).notificarQuierenAdoptarTuMascota(any(), any());
  }
}
