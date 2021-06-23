package modelo.usuario;

import excepciones.ContraseniaInvalidaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidadorContraseniasTest {

  @Test
  @DisplayName("si se crea un usuario, y se registra una contrasenia valida, no se generan problemas")
  public void contraseniaValidaTest() {
    assertDoesNotThrow(
        () -> new Usuario(null, "flashbacksDeSistemasOperativos", null, null));
  }

  @Test
  @DisplayName("si se crea un usuario, y se registra una contrasenia comun, se genera ContraseniaInvalidaException")
  public void ContraseniaInvalidaExceptionTest() {
    assertThrows(ContraseniaInvalidaException.class,
        () -> new Usuario(null, "password", null, null));
  }

  @Test
  @DisplayName("si se crea un usuario, y se registra una contrasenia con menos de 8 caracteres, se genera " +
      "ContraseniaInvalidaException")
  public void ContraseniaInvalidaExceptionTest2() {
    assertThrows(ContraseniaInvalidaException.class,
        () -> new Usuario(null, "*?)(/%#", null, null));
  }

}
