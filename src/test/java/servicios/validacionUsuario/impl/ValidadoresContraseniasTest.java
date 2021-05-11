package servicios.validacionUsuario.impl;

import excepciones.ContraseniaInvalidaException;
import modelo.usuario.Administrador;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidadoresContraseniasTest {

  @Test
  @DisplayName("si se crea un usuario, y se registra una contrasenia valida, no se generan problemas")
  public void contraseniaValidaTest() {
    assertDoesNotThrow(() -> new Administrador(null, "flashbacksDeSistemasOperativos", null));
  }

  @Test
  @DisplayName("si se crea un usuario, y se registra una contrasenia comun, se genera ContraseniaInvalidaException")
  public void ContraseniaInvalidaExceptionTest() {
    assertThrows(ContraseniaInvalidaException.class,
        () -> new Administrador(null, "password", null));
  }

  @Test
  @DisplayName("si se crea un usuario, y se registra una contrasenia con menos de 8 caracteres, se genera " +
      "ContraseniaInvalidaException")
  public void ContraseniaInvalidaExceptionTest2() {
    assertThrows(ContraseniaInvalidaException.class,
        () -> new Administrador(null, "*?)(/%#", null));
  }

}
