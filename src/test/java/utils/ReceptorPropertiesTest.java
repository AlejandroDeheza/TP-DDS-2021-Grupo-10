package utils;

import static org.junit.jupiter.api.Assertions.*;

import excepciones.RepositorioPropertiesException;
import org.junit.jupiter.api.Test;
import java.io.InputStream;

public class ReceptorPropertiesTest {
  String pathTest = "app.properties";


  @Test
  public void cargarPropertiesValido() {
    assertDoesNotThrow(() -> new ReceptorProperties(pathTest));
  }

  @Test
  public void noSePuedeCargarPropertiesInvalido() {
    assertThrows(RepositorioPropertiesException.class, () -> new ReceptorProperties("llaal"));
  }
}
