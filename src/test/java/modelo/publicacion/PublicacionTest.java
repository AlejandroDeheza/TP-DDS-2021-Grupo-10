package modelo.publicacion;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import modelo.notificacion.NotificadorCorreo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioRescates;
import utils.DummyData;

public class PublicacionTest {

  NotificadorCorreo notificadorCorreo;
  RepositorioDarEnAdopcion repositorioDarEnAdopcion;
  RepositorioRescates repositorioRescates;

  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
    repositorioDarEnAdopcion = mock(RepositorioDarEnAdopcion.class);
    repositorioRescates = mock(RepositorioRescates.class);
  }

  @Test
  @DisplayName("Si un usuario encuentra a su mascota en una publicacion, se envia una Notificacion al rescatista")
  public void encontrarUnaMascotaPerdidaEnviaUnaNotificacion() {
    Rescate publicacion = DummyData.getPublicacionDeRescate(notificadorCorreo, repositorioRescates);
    publicacion.notificarAlPublicador(DummyData.getUsuario(notificadorCorreo));
    verify(notificadorCorreo, times(1)).notificarDuenioReclamaSuMacota(any());
  }

  @Test
  @DisplayName("Si un adoptante desea adoptar una mascota de una publicacion, se envia una Notificacion al duenio")
  public void procesarUnaPublicacionDeDarEnAdopcionEnviaUnaNotificacion() {
    DarEnAdopcion publicacion = DummyData.getPublicacionDeDarEnAdopcion(notificadorCorreo, repositorioDarEnAdopcion);
    publicacion.notificarAlPublicador(DummyData.getUsuario(notificadorCorreo));
    verify(notificadorCorreo, times(1)).notificarQuierenAdoptarTuMascota(any(), any());
  }

}
