package servicios.validacionUsuario.impl;

import excepciones.ContraseniaInvalidaException;
import modelo.usuario.Administrador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidadoresContraseniasTest {

  @Test
  @DisplayName("si se crea un usuario, y se registra una contrasenia valida, no se generan problemas")
  public void contraseniaValidaTest() {
    Assertions
        .assertDoesNotThrow(() -> new Administrador(
            null, "flashbacksDeSistemasOperativos", null)
        );
  }

  @Test
  @DisplayName("si se crea un usuario, y se registra una contrasenia comun, se genera ContraseniaInvalidaException")
  public void ContraseniaInvalidaExceptionTest() {
    Assertions.assertThrows(ContraseniaInvalidaException.class,
        () -> new Administrador(null, "password", null));
  }

  @Test
  @DisplayName("si se crea un usuario, y se registra una contrasenia con menos de 8 caracteres, se genera " +
      "ContraseniaInvalidaException")
  public void ContraseniaInvalidaExceptionTest2() {
    Assertions.assertThrows(ContraseniaInvalidaException.class,
        () -> new Administrador(null, "*?)(/%#", null));
  }

}
