package modelo.publicacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.MascotaRegistrada;
import modelo.notificacion.NotificadorCorreo;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioPublicaciones;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @see RepositorioPublicacionesTest para ver la forma en cómo se procesa una publicación
 */
public class PublicacionTest {

  NotificadorCorreo notificadorCorreo;

  /* Posts de adopción */
  DarEnAdopcion publicacionDeDarEnAdopcionCorrecta;

  /* Etc */
  Usuario unUsuario;
  Persona unaPersona;
  MascotaRegistrada unaMascotaRegistrada;
  MascotaEncontrada unaMascotaEncontrada;

  @BeforeEach
  public void contextLoad() {

    this.notificadorCorreo = mock(NotificadorCorreo.class);

    /* Publicaciones de DarEnAdopcion */
    publicacionDeDarEnAdopcionCorrecta = DummyData.getPublicacionDeDarEnAdopcionCorrecta(this.notificadorCorreo, new RepositorioPublicaciones());

    /* Etc. */
    unUsuario = DummyData.getUsuario();
    unaPersona = this.unUsuario.getPersona();
    unaMascotaRegistrada = DummyData.getMascotaRegistrada(new RepositorioCaracteristicas());
    unaMascotaEncontrada = DummyData.getMascotaEncontrada(DummyData.getFotos());
  }

  @Test
  @DisplayName("Si un usuario encuentra a su mascota en una publicacion, se envia una Notificacion al rescatista")
  public void encontrarUnaMascotaPerdidaEnviaUnaNotificacion() {
    NotificadorCorreo notificadorCorreo = mock(NotificadorCorreo.class);
    RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();
    Usuario rescatista = DummyData.getUsuario();
    DummyData.getPublicacionDeRescate(notificadorCorreo, repositorioPublicaciones).notificarAlPosteador(rescatista);
    verify(notificadorCorreo).notificar(any());
  }

  @Test
  public void sePuedeGenerarUnaPublicacionParaDarEnAdopcionAUnaMascota() {
    RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();
    repositorioPublicaciones.agregar(publicacionDeDarEnAdopcionCorrecta);
    assertEquals(repositorioPublicaciones.getDarEnAdopcion().size(), 1);
  }

  @Test
  public void procesarUnaPublicacionDeDarEnAdopcionProcesaNoEnviaUnaNotificacion() {
    RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();

    DarEnAdopcion publicacionDarEnAdopcion =
        new DarEnAdopcion(this.unaPersona.getDatosDeContacto(), this.notificadorCorreo, this.unaMascotaRegistrada, repositorioPublicaciones);

    assertEquals(repositorioPublicaciones.getDarEnAdopcion().size(), 0);
    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 0);

    repositorioPublicaciones.agregar(publicacionDarEnAdopcion);
    assertEquals(repositorioPublicaciones.getDarEnAdopcion().size(), 1);
    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 0);

    publicacionDarEnAdopcion.notificarAlPosteador(unUsuario);
    assertEquals(repositorioPublicaciones.getDarEnAdopcion().size(), 0);
    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 1);

    verify(this.notificadorCorreo, times(0)).notificar(any());
  }

  @Test
  public void procesarUnaPublicacionDeRescateEnviaUnaNotificacion() {
    RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();

    Rescate publicacionRescate =
        new Rescate(this.unaPersona.getDatosDeContacto(), this.notificadorCorreo, repositorioPublicaciones, this.unaMascotaEncontrada);

    assertEquals(repositorioPublicaciones.getRescates().size(), 0);
    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 0);

    repositorioPublicaciones.agregar(publicacionRescate);
    assertEquals(repositorioPublicaciones.getRescates().size(), 1);
    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 0);

    publicacionRescate.notificarAlPosteador(unUsuario);
    assertEquals(repositorioPublicaciones.getRescates().size(), 0);
    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 1);

    verify(this.notificadorCorreo, times(1)).notificar(any());
  }
}
