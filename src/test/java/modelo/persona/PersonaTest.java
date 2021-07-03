package modelo.persona;

import excepciones.DatosDeContactoIncompletosException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

public class PersonaTest {

  @Test
  @DisplayName("si se crea una Persona valida, no se generan problemas")
  public void personaValidaTest() {
    assertDoesNotThrow(() -> DummyData.getPersona(null));
  }

  @Test
  @DisplayName("si se crea otra Persona valida, sin telefono, no se generan problemas")
  public void personaValidaTest2() {
    assertDoesNotThrow(DummyData::getPersonaSinTelefono);
  }

  @Test
  @DisplayName("si se crea una Persona sin referencia a DatosDeContacto, se genera NullPointerException")
  public void personaSinDatosDeContactoTest() {
    assertThrows(NullPointerException.class, DummyData::getPersonaSinDatosDeContacto);
  }

  @Test
  @DisplayName("si se crea una Persona sin telefono, ni mail, ni nombre y apellido, se genera "
      + "DatosDeContactoIncompletosException")
  public void DatosDeContactoIncompletosExceptionTest() {
    assertThrows(DatosDeContactoIncompletosException.class, DummyData::getPersonaSinDatosDeContactoNiNombreNiApellido);
  }

  @Test // si llegaramos a usar Twilio, no deberiamos testear esto
  public void UnaPersonaDebeTenerUnCorreoAsociadoEnSuDatoDeContactoTest() {
    assertThrows(DatosDeContactoIncompletosException.class, DummyData::getPersonaSinCorreo);
  }

}
