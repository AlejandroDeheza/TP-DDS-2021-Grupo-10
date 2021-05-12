package servicios.validacionUsuario.impl;

import excepciones.AutenticacionInvalidaException;
import modelo.usuario.DuenioMascota;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidadorAutenticacionTest {

  DuenioMascota duenioMascota = DummyData.getDummyDuenioMascota();

  @Test
  @DisplayName("si un usuario dummy se autentica correctamente, no se genera ningun problema")
  public void autenticacionCorrectaTest() {
    assertDoesNotThrow(() -> duenioMascota.autenticarUsuario("Password1234"));
  }

  @Test
  @DisplayName("si un usuario dummy se autentica mal, se genera AutenticacionInvalidaException")
  public void autenticacionInvalidaExceptionTest() {
    assertThrows(AutenticacionInvalidaException.class,
        () -> duenioMascota.autenticarUsuario("Password12"));
  }
}
