package utils;

import excepciones.RepositorioPropertiesException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReceptorPropertiesTest {

  @Test
  public void cargarPropertiesValido() {
    assertDoesNotThrow(() -> new ReceptorProperties(DummyData.getRutaProperties()));
  }

  @Test
  public void noSePuedeCargarPropertiesInvalido() {
    assertThrows(RepositorioPropertiesException.class, () -> new ReceptorProperties("\\llaal"));
  }
}
