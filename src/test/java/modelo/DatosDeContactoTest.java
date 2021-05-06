package modelo;

import excepciones.DatosDeContactoIncompletos;
import excepciones.DatosDeContactoInvalidosException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DatosDeContactoTest {

  @Test
  public void generarUnDatoDeContactoValidoNoDaProblemas(){
    Assertions.assertDoesNotThrow(
        () -> new Persona("Emi","Mazzaglia", TipoDocumento.DNI,"35353535",new DatosDeContacto(null,null), LocalDate.now()));
  }

  @Test
  public void unaPersonaConDatosDeContactoInvalidosGeneraUnaException(){
    Assertions.assertThrows(DatosDeContactoIncompletos.class,
        () -> new Persona(null,null, TipoDocumento.DNI,"35353535",new DatosDeContacto(null,null), LocalDate.now()));
  }
}
