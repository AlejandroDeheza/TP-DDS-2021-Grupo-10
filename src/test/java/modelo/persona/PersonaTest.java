package modelo.persona;

import excepciones.DatosDeContactoIncompletosException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import java.time.LocalDate;

public class PersonaTest {

  @Test
  @DisplayName("si se crea una Persona valida, no se generan problemas")
  public void personaValidaTest() {
    assertDoesNotThrow(DummyData::getDummyPersona);
  }

  @Test
  @DisplayName("si se crea otra Persona valida, sin telefono ni mail, no se generan problemas")
  public void personaValidaTest2() {
    assertDoesNotThrow(
        () -> new Persona("Emi", "Mazzaglia", TipoDocumento.DNI, "35353535",
            new DatosDeContacto(null, null), LocalDate.now())
    );
  }

  @Test
  @DisplayName("si se crea una Persona sin referencia a DatosDeContacto, se genera NullPointerException")
  public void personaSinDatosDeContactoTest() {
    assertThrows(NullPointerException.class, DummyData::getDummyPersonaSinDatosDeContacto);
  }

  @Test
  @DisplayName("si se crea una Persona sin telefono, ni mail, ni nombre y apellido, se genera " +
      "DatosDeContactoIncompletosException")
  public void DatosDeContactoIncompletosExceptionTest() {
    assertThrows(DatosDeContactoIncompletosException.class,
        DummyData::getDummyPersonaSinDatosDeContactoNiNombreNiApellido);
  }

}
