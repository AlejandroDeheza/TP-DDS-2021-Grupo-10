package servicios.validacionUsuario.impl;

import excepciones.ContraseniaInvalidaException;
import modelo.usuario.Administrador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidadoresContraseniasTest {

  @Test
  public void unaContraseniaValidaNoGeneraExcepciones() {
    Assertions
        .assertDoesNotThrow(() -> new Administrador(null, "flashbacksDeSistemasOperativos", null));
  }

  @Test
  public void unaContraseniaComunGeneraContraseniaInvalidaException() {
    Assertions.assertThrows(ContraseniaInvalidaException.class,
        () -> new Administrador(null, "password", null));
  }

  @Test
  public void unaContraseniaConMenosDe8CaracteresGeneraContraseniaInvalidaException() {
    Assertions.assertThrows(ContraseniaInvalidaException.class,
        () -> new Administrador(null, "*?)(/%#", null));
  }

}
