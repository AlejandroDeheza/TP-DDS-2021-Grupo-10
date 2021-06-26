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

    assertEquals(repositorioPublicaciones.getPublicaciones().size(), 0);
    repositorioPublicaciones.agregarPublicacion(publicacion);
    assertEquals(repositorioPublicaciones.getPublicaciones().size(), 1);
    assertEquals(publicacion, repositorioPublicaciones.getPublicaciones().get(0));

    assertEquals(repositorioPublicaciones.getPublicacionesEncontradas().size(), 0);
    repositorioPublicaciones.marcarPublicacionComoEncontrada(publicacion);
    assertEquals(repositorioPublicaciones.getPublicacionesEncontradas().size(), 1);
    assertEquals(publicacion, repositorioPublicaciones.getPublicacionesEncontradas().get(0));
  }

}
