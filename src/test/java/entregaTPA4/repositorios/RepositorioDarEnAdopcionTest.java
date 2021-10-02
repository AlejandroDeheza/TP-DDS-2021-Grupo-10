package entregaTPA4.repositorios;

import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import modelo.notificacion.NotificadorCorreo;
import modelo.publicacion.DarEnAdopcion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositorioDarEnAdopcionTest extends NuestraAbstractPersistenceTest {

  RepositorioDarEnAdopcion repository = new RepositorioDarEnAdopcion();

  @Test
  @DisplayName("Agregar una publicacion de adopcion, aumenta el numero de publicaciones en uno")
  public void agregarPublicacionDeAdopcionTest(){
    DarEnAdopcion publicacionDeDarEnAdopcion =
        DummyData.getPublicacionDeDarEnAdopcion(new NotificadorCorreo("hola@gmail.com"));
    entityManager().persist(publicacionDeDarEnAdopcion);
    assertEquals(1,  repository.getPublicaciones().size());
  }
}
