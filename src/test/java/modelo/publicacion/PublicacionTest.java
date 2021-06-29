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
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioRescates;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PublicacionTest {

  NotificadorCorreo notificadorCorreo;

  DarEnAdopcion publicacionDeDarEnAdopcionCorrecta;

  Usuario unUsuario;
  Persona unaPersona;
  MascotaRegistrada unaMascotaRegistrada;
  MascotaEncontrada unaMascotaEncontrada;

  @BeforeEach
  public void contextLoad() {

    this.notificadorCorreo = mock(NotificadorCorreo.class);

    publicacionDeDarEnAdopcionCorrecta = DummyData.getPublicacionDeDarEnAdopcionCorrecta(this.notificadorCorreo, new RepositorioDarEnAdopcion());

    unUsuario = DummyData.getUsuario();
    unaPersona = this.unUsuario.getPersona();
    unaMascotaRegistrada = DummyData.getMascotaRegistrada(new RepositorioCaracteristicas());
    unaMascotaEncontrada = DummyData.getMascotaEncontrada(DummyData.getFotos());
  }

  @Test
  @DisplayName("Si un usuario encuentra a su mascota en una publicacion, se envia una Notificacion al rescatista")
  public void encontrarUnaMascotaPerdidaEnviaUnaNotificacion() {
    NotificadorCorreo notificadorCorreo = mock(NotificadorCorreo.class);
    RepositorioRescates repositorio = new RepositorioRescates();
    Usuario rescatista = DummyData.getUsuario();
    DummyData.getPublicacionDeRescate(notificadorCorreo, repositorio).notificarAlPosteador(rescatista);
    verify(notificadorCorreo).notificar(any());
  }

  @Test
  public void sePuedeGenerarUnaPublicacionParaDarEnAdopcionAUnaMascota() {
    RepositorioDarEnAdopcion repositorio = new RepositorioDarEnAdopcion();
    DarEnAdopcion publicacion =
        new DarEnAdopcion(this.unaPersona.getDatosDeContacto(), this.notificadorCorreo, this.unaMascotaRegistrada, repositorio);
    repositorio.agregar(publicacion);
    assertEquals(repositorio.getDarEnAdopcion().size(), 1);
  }

  @Test
  public void procesarUnaPublicacionDeDarEnAdopcionProcesaEnviaUnaNotificacion() {
    RepositorioDarEnAdopcion repositorio = new RepositorioDarEnAdopcion();

    DarEnAdopcion publicacion =
        new DarEnAdopcion(this.unaPersona.getDatosDeContacto(), this.notificadorCorreo, this.unaMascotaRegistrada, repositorio);

    assertEquals(repositorio.getDarEnAdopcion().size(), 0);
    assertEquals(repositorio.getPublicacionesProcesadas().size(), 0);

    repositorio.agregar(publicacion);
    assertEquals(repositorio.getDarEnAdopcion().size(), 1);
    assertEquals(repositorio.getPublicacionesProcesadas().size(), 0);

    publicacion.notificarAlPosteador(unUsuario);
    assertEquals(repositorio.getDarEnAdopcion().size(), 0);
    assertEquals(repositorio.getPublicacionesProcesadas().size(), 1);

    verify(this.notificadorCorreo, times(1)).notificar(any());
  }

  @Test
  public void procesarUnaPublicacionDeRescateEnviaUnaNotificacion() {
    RepositorioRescates repositorio = new RepositorioRescates();

    Rescate publicacion = new Rescate(this.unaPersona.getDatosDeContacto(), this.notificadorCorreo, repositorio, this.unaMascotaEncontrada);

    assertEquals(repositorio.getRescates().size(), 0);
    assertEquals(repositorio.getPublicacionesProcesadas().size(), 0);

    repositorio.agregar(publicacion);
    assertEquals(repositorio.getRescates().size(), 1);
    assertEquals(repositorio.getPublicacionesProcesadas().size(), 0);

    publicacion.notificarAlPosteador(unUsuario);
    assertEquals(repositorio.getRescates().size(), 0);
    assertEquals(repositorio.getPublicacionesProcesadas().size(), 1);

    verify(this.notificadorCorreo, times(1)).notificar(any());
  }
}
