package entregaTPA4.repositorios;

import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.publicacion.DarEnAdopcion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositorioDarEnAdopcionTest extends NuestraAbstractPersistenceTest {

  RepositorioDarEnAdopcion repositorioDarEnAdopcion = new RepositorioDarEnAdopcion();

  @Test
  @DisplayName("Agregar una publicacion de adopcion, aumenta el numero de publicaciones en uno")
  public void agregarPublicacionDeAdopcionTest(){
    DarEnAdopcion publicacionDeDarEnAdopcion =
        DummyData.getPublicacionDeDarEnAdopcion(TipoNotificadorPreferido.CORREO);
    repositorioDarEnAdopcion.agregar(publicacionDeDarEnAdopcion);
    assertEquals(1,  repositorioDarEnAdopcion.getPublicacionesActivas().size());
  }
}
