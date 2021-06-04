package modelo.publicacion;

import modelo.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;
import static org.junit.jupiter.api.Assertions.*;

public class PublicacionTest {
  @Test
  @DisplayName("Agregar un Duenio a Una Publicacion")
  public void agregarDuenioAPublicacion() {
    Publicacion publicacion= new Publicacion(DummyData.getDummyDatosDeContacto(),DummyData.getDummyFotosMascota());
    Usuario duenio=DummyData.getDummyUsuario();

    publicacion.agregarDuenio(duenio);
    assertTrue(publicacion.tieneDuenio());
  }

  @Test
  @DisplayName("Si no agrego Duenio a Publicacion publicacion no tiene Duenio")
  public void unaPublicacionSinDuenioNoTieneDuenio() {
    Publicacion publicacion= new Publicacion(DummyData.getDummyDatosDeContacto(),DummyData.getDummyFotosMascota());

    assertFalse(publicacion.tieneDuenio());
  }
}
