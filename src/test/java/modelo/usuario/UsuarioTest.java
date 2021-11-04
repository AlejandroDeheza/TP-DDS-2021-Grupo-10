package modelo.usuario;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import modelo.persona.Persona;
import utils.DummyData;

public class UsuarioTest extends NuestraAbstractPersistenceTest {
  Usuario usuario = DummyData.getUsuario(null);

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

    entityManager().persist(usuario);
    assertEquals(1, entityManager().createQuery("from Usuario", Usuario.class).getResultList().size());
    assertEquals(personaAsociada.getId(), entityManager().createQuery("from Persona", Persona.class).getResultList().get(0).getId());

    entityManager().remove(usuario);
    assertEquals(0, entityManager().createQuery("from Usuario", Usuario.class).getResultList().size());
    assertEquals(0, entityManager().createQuery("from Persona", Persona.class).getResultList().size());
  }
}
