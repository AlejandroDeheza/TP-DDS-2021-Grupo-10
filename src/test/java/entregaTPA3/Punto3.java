package entregaTPA3;

import modelo.notificacion.NotificadorCorreo;
import modelo.publicacion.DarEnAdopcion;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import utils.DummyData;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class Punto3 {

  NotificadorCorreo notificadorCorreo;

  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
  }

  @Test
  @DisplayName("Si un interesado desea adoptar una mascota de una publicacion, se envia una Notificacion al duenio")
  public void procesarUnaPublicacionDeDarEnAdopcionEnviaUnaNotificacion() {
    DarEnAdopcion publicacion = DummyData.getPublicacionDeDarEnAdopcion(notificadorCorreo, new RepositorioDarEnAdopcion());

    Usuario adoptante = DummyData.getUsuario(notificadorCorreo);
    publicacion.notificarAlPosteador(adoptante);
    verify(notificadorCorreo, times(1)).notificarQuierenAdoptarTuMascota(any(), any());
  }
}
