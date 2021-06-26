package repositorios;

import modelo.notificacion.NotificadorCorreo;
import modelo.publicacion.DarEnAdopcion;
import modelo.publicacion.Rescate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.*;

public class RepositorioPublicacionesTest {

  @BeforeEach
  public void contextLoad() {}

  @Test
  @DisplayName("Procesar una publicacion de rescate no genera problemas")
  public void procesarUnaPublicacionDeRescateNoGeneraProblemas() {
    RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();
    Rescate publicacion = DummyData.getPublicacionDeRescate(null, repositorioPublicaciones);

    assertEquals(repositorioPublicaciones.getPublicacionesPendientes().size(), 0);
    repositorioPublicaciones.agregarPublicacion(publicacion);
    assertEquals(repositorioPublicaciones.getPublicacionesPendientes().size(), 1);
    assertEquals(publicacion, repositorioPublicaciones.getPublicacionesPendientes().get(0));

    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 0);
    repositorioPublicaciones.marcarPublicacionComoProcesada(publicacion);
    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 1);
    assertEquals(publicacion, repositorioPublicaciones.getPublicacionesProcesadas().get(0));
  }

  @Test
  @DisplayName("Procesar una publicacion de dar en adopción no genera problemas")
  public void procesarUnaPublicacionDeDarEnAdopcionNoGeneraProblemas() {
    RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();
    DarEnAdopcion publicacion =
        DummyData.getPublicacionDeDarEnAdopcionCorrecta(new NotificadorCorreo(),
            repositorioPublicaciones, DummyData.getListadoPreguntasConRespuesta(
                DummyData.getPreguntaConRespuesta("unaPregunta", "unaRespuesta")),
            null);

    assertEquals(repositorioPublicaciones.getPublicacionesPendientes().size(), 0);
    repositorioPublicaciones.agregarPublicacion(publicacion);
    assertEquals(repositorioPublicaciones.getPublicacionesPendientes().size(), 1);
    assertEquals(publicacion, repositorioPublicaciones.getPublicacionesPendientes().get(0));

    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 0);
    repositorioPublicaciones.marcarPublicacionComoProcesada(publicacion);
    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 1);
    assertEquals(publicacion, repositorioPublicaciones.getPublicacionesProcesadas().get(0));
  }

}
