package utils;

import excepciones.RepositorioPropertiesException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReceptorPropertiesTest {
  String pathTest = "\\src\\test\\resources\\app.properties";

  @Test
  public void cargarPropertiesValido() {
    assertDoesNotThrow(() -> new ReceptorProperties(pathTest));
  }

  @Test
  public void noSePuedeCargarPropertiesInvalido() {
    assertThrows(RepositorioPropertiesException.class, () -> new ReceptorProperties("\\llaal"));
  }
}
