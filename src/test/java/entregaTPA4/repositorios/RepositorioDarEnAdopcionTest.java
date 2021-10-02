package entregaTPA4.repositorios;

import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.notificacion.NotificadorCorreo;
import modelo.publicacion.DarEnAdopcion;
import modelo.suscripcion.SuscripcionParaAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioDarEnAdopcion;
import utils.DummyData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RepositorioDarEnAdopcionTest extends NuestraAbstractPersistenceTest {

  RepositorioDarEnAdopcion repository;

  @BeforeEach
  public void contextLoad() {
    repository = new RepositorioDarEnAdopcion();
  }

  @Test
  @DisplayName("Agregar una publicacion de adopcion, aumenta el numero de publicaciones en uno")
  public void agregarPublicacionDeAdopcionTest(){
    DarEnAdopcion publicacionDeDarEnAdopcion = DummyData.getPublicacionDeDarEnAdopcion(new NotificadorCorreo("hola@gmail.com"),repository);
    repository.agregar(publicacionDeDarEnAdopcion);
    assertEquals(1,  repository.getPublicaciones().size());
  }
}
