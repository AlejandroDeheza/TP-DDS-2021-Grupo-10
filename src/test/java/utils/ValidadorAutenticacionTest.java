package utils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import excepciones.AutenticacionInvalidaException;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import java.time.LocalTime;

public class ValidadorAutenticacionTest {

  Usuario usuario = DummyData.getUsuario(null);
  ValidadorAutenticacionNuevo validadorAutenticacionNuevo = new ValidadorAutenticacionNuevo(LocalTime.now(), 0);

  @Test
  @DisplayName("si un usuario dummy se autentica correctamente, no se genera ningun problema")
  public void autenticacionCorrectaTest() {
    assertDoesNotThrow(() -> validadorAutenticacionNuevo.autenticarUsuario(usuario, "Password1234"));
  }

  @Test
  @DisplayName("si un usuario dummy se autentica mal, se genera AutenticacionInvalidaException")
  public void autenticacionInvalidaExceptionTest() {
    assertThrows(AutenticacionInvalidaException.class, () -> validadorAutenticacionNuevo.autenticarUsuario(usuario, "Password12"));
  }
}
