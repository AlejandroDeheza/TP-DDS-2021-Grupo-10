package repositorios;

import modelo.publicacion.Rescate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.*;

public class RepositorioPublicacionesTest {

  @BeforeEach
  public void contextLoad() {
  }

  @Test
  @DisplayName("Procesar una publicacion no genera problemas")
  public void procesaroUnaPublicacionNoGeneraProblemas() {
    Rescate publicacion = DummyData.getPublicacion(null);
    RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();

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
