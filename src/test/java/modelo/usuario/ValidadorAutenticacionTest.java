package modelo.usuario;

import excepciones.AutenticacionInvalidaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidadorAutenticacionTest {

  Usuario usuario = DummyData.getUsuario(null);

  @Test
  @DisplayName("si un usuario dummy se autentica correctamente, no se genera ningun problema")
  public void autenticacionCorrectaTest() {
    assertDoesNotThrow(() -> usuario.autenticarUsuario("Password1234"));
  }

  @Test
  @DisplayName("si un usuario dummy se autentica mal, se genera AutenticacionInvalidaException")
  public void autenticacionInvalidaExceptionTest() {
    assertThrows(AutenticacionInvalidaException.class, () -> usuario.autenticarUsuario("Password12"));
  }
}