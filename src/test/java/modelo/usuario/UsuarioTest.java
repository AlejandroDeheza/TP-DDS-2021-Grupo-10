package modelo.usuario;

import modelo.persona.Persona;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import utils.DummyData;

public class UsuarioTest {

  @Test
  @DisplayName("Si se crea un usuario valido, no se genera ningun problema")
  public void usuarioValidoTest() {
    assertDoesNotThrow(DummyData::getDummyUsuario);
  }

  @Test
  @DisplayName("Se puede generar una persona Voluntaria")
  public void usuarioVoluntarioTest(){
    Usuario usuarioVoluntario = generarVoluntario();
    assertEquals(usuarioVoluntario.getTipo(), TipoUsuario.VOLUNTARIO);
  }

  public Usuario generarVoluntario(){
    Persona personaVoluntaria = DummyData.getDummyPersona();
    Usuario usuarioVoluntario = new Usuario("User Voluntario", "Password1159Hard", TipoUsuario.VOLUNTARIO, personaVoluntaria);
    return usuarioVoluntario;
  }

}


