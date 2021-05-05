package modelo;

import excepciones.DatosDeContactoInvalidosException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DatosDeContactoTest {

  @Test
  public void generarUnDatoDeContactoValidoNoDaProblemas(){
    Assertions.assertDoesNotThrow(
        () -> new DatosDeContacto("", 12341234, "algo@email.com"));
  }

  @Test
  public void generarUnDatoDeContactoInvalidoGeneraDatosDeContactoInvalidosException(){
    Assertions.assertThrows(DatosDeContactoInvalidosException.class,
        () -> new DatosDeContacto(null, null, null));
  }
}
