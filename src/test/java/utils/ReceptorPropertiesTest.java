package repositorios;

import excepciones.RepositorioPropertiesException;

import org.junit.jupiter.api.Test;
import util.ReceptorProperties;


import static org.junit.jupiter.api.Assertions.*;

public class ReceptorPropertiesTest {
  String pathTest = "src/test/resources/app.properties";

  @Test
  public void cargarPropertiesValido(){
    assertDoesNotThrow(()-> new ReceptorProperties(pathTest));
  }


  @Test
  public void noSePuedecargarPropertiesValido(){
    assertThrows(RepositorioPropertiesException.class, ()-> new ReceptorProperties("llaal").getProperties());
  }
}
