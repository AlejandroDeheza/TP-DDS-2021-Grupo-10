package repositorios;

import excepciones.RepositorioPropertiesException;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class RepositorioPropertiesTest {
  String pathTest = "src/test/resources/app.properties";

  @Test
  public void cargarPropertiesValido(){
    assertDoesNotThrow(()-> new RepositorioProperties(pathTest));
  }


  @Test
  public void noSePuedecargarPropertiesValido(){
    assertThrows(RepositorioPropertiesException.class,()->new RepositorioProperties("llaal"));
  }
}
