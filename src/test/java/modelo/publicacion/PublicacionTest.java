package modelo.publicacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import modelo.notificacion.NotificadorCorreo;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioRescates;
import utils.DummyData;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PublicacionTest {

  NotificadorCorreo notificadorCorreo;

  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
  }

  @Test
  @DisplayName("Si un usuario encuentra a su mascota en una publicacion, se envia una Notificacion al rescatista")
  public void encontrarUnaMascotaPerdidaEnviaUnaNotificacion() {
    Rescate publicacion = DummyData.getPublicacionDeRescate(notificadorCorreo, new RepositorioRescates());
    publicacion.notificarAlPosteador(DummyData.getUsuario());
    verify(notificadorCorreo, times(1)).notificar(any());
  }

  @Test
  @DisplayName("Si un adoptante desea adoptar una mascota de una publicacion, se envia una Notificacion al duenio")
  public void procesarUnaPublicacionDeDarEnAdopcionEnviaUnaNotificacion() {
    DarEnAdopcion publicacion = DummyData.getPublicacionDeDarEnAdopcion(notificadorCorreo, new RepositorioDarEnAdopcion());
    publicacion.notificarAlPosteador(DummyData.getUsuario());
    verify(notificadorCorreo, times(1)).notificar(any());
  }

  @Test
  @DisplayName("Si se envia el link de baja al posteador, se envia una Notificacion al posteador")
  public void enviarUnLinkDeBajaEnviaUnaNotificacion() {
    SuscripcionAdopciones publicacion = DummyData.getPublicacionDeIntencionDeAdopcion(notificadorCorreo);
    publicacion.enviarLinkDeBaja();
    verify(notificadorCorreo, times(1)).notificar(any());
  }

  @Test
  @DisplayName("Si se envian recomendacion de adopcion, se envia una Notificacion al adoptante")
  public void enviarRecomendacionesEnviaUnaNotificacion() {
    SuscripcionAdopciones publicacion = DummyData.getPublicacionDeIntencionDeAdopcion(notificadorCorreo);
    publicacion.enviarRecomendaciones(
        Arrays.asList(
            DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()),
            DummyData.getMascotaRegistrada(new RepositorioCaracteristicas())
        )
    );
    verify(notificadorCorreo, times(1)).notificar(any());
  }

}
