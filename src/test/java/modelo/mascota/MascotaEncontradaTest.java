package modelo.mascota;

import excepciones.FotosMascotaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import utils.DummyData;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MascotaEncontradaTest {

  @BeforeEach
  public void contextLoad() {}

  @Test
  @DisplayName("si se genera una MascotaEncontrada sin fotos en lista, se genera FotosMascotaException")
  public void InformeMascotaEncontradaInvalidaExceptionTest() {
    assertThrows(FotosMascotaException.class, () -> DummyData.getMascotaEncontrada(new ArrayList<>()));
  }

  @Test
  @DisplayName("si se genera una MascotaEncontrada sin lista de fotos, se genera FotosMascotaException")
  public void InformeMascotaEncontradaInvalidaExceptionTestConNull() {
    assertThrows(FotosMascotaException.class, () -> DummyData.getMascotaEncontrada(null));
  }
}
