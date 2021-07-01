package entregaTPA3;

import modelo.notificacion.NotificadorCorreo;
import modelo.publicacion.DarEnAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioDarEnAdopcion;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TestsEntrega3 {

  NotificadorCorreo notificadorCorreo;

  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
  }

  @Test
  public void sePuedeGenerarUnaPublicacionParaDarEnAdopcionAUnaMascota() {
    RepositorioDarEnAdopcion repositorio = new RepositorioDarEnAdopcion();

    DarEnAdopcion publicacion = new DarEnAdopcion(
        DummyData.getDatosDeContacto(),
        notificadorCorreo,
        DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()),
        repositorio
    );

    repositorio.agregar(publicacion);
    assertEquals(repositorio.getDarEnAdopcion().size(), 1);
  }
}
