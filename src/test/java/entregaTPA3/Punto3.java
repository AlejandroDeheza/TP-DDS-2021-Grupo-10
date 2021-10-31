package entregaTPA3;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import modelo.notificacion.NotificadorCorreo;
import modelo.publicacion.DarEnAdopcion;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

public class Punto3 {

  NotificadorCorreo notificadorCorreo;

  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
  }

  @Test
  @DisplayName("Si un interesado desea adoptar una mascota de una publicacion, se envia una Notificacion al duenio")
  public void procesarUnaPublicacionDeDarEnAdopcionEnviaUnaNotificacion() {
    DarEnAdopcion publicacion = DummyData.getPublicacionDeDarEnAdopcion(notificadorCorreo);
    Usuario adoptante = DummyData.getUsuario(notificadorCorreo);
    publicacion.notificarAlPublicador(adoptante);
    verify(notificadorCorreo, times(1)).notificarQuierenAdoptarTuMascota(any(), any());
  }
}
