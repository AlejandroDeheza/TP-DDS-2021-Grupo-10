package modelo.usuario;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import modelo.persona.Persona;
import repositorios.RepositorioPersonas;
import utils.CascadeTypeCheck;
import utils.DummyData;

public class UsuarioTest extends NuestraAbstractPersistenceTest {
  Usuario usuario = DummyData.getUsuario(null);
  CascadeTypeCheck cascadeTypeCheck = new CascadeTypeCheck(usuario);
  RepositorioPersonas repositorioPersonas = new RepositorioPersonas();

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

  @Test
  @DisplayName("Al eliminar un Usuario, se elimina su Persona asociada")
  public void eliminarUnUsuarioEliminaSuPersonaAsociada() {
    Persona personaAsociada = usuario.getPersona();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(personaAsociada, 1, 1, 0, 0));
    assertNull(repositorioPersonas.buscarPorId(personaAsociada.getId()));
  }
}
