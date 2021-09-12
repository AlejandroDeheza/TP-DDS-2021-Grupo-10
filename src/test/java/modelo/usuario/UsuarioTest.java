package modelo.usuario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import utils.DummyData;

public class UsuarioTest {

  @Test
  @DisplayName("Si se crea un usuario valido, no se genera ningun problema")
  public void usuarioValidoTest() {
    assertDoesNotThrow(() -> DummyData.getUsuario(null));
  }

  @Test
  @DisplayName("Se puede generar una persona Voluntaria")
  public void usuarioVoluntarioTest() {
    assertEquals(DummyData.getUsuarioVoluntario().getTipo(), TipoUsuario.VOLUNTARIO);
  }

}


