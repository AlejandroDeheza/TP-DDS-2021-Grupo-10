package entregaTPA4.repositorios;

import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.notificacion.NotificadorCorreo;
import modelo.suscripcion.SuscripcionParaAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import utils.DummyData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RepositorioSuscripcionesParaAdopcionesTest extends NuestraAbstractPersistenceTest {

  RepositorioSuscripcionesParaAdopciones repository;

  @BeforeEach
  public void contextLoad() {
    repository = new RepositorioSuscripcionesParaAdopciones();
  }

  @Test
  @DisplayName("Agregar una suscripcion aumenta el numero de suscripciones en una")
  public void agregarSuscripcionTest(){
    SuscripcionParaAdopcion suscripcionParaAdopcion = DummyData.getSuscripcionParaAdopcion(new NotificadorCorreo("fake@gmail.com"),repository);
    int cantidadSuscripcionesPrevia = repository.getSuscripciones().size();
    repository.agregar(suscripcionParaAdopcion);
    assertEquals(cantidadSuscripcionesPrevia +1 , repository.getSuscripciones().size());
  }

  @Test
  @DisplayName("Dar de baja una suscripcion recibe una suscripcion con el flag isActivo cambiado y updatea el registro en la DB")
  public void darDeBajaSuscripcionTest(){
    SuscripcionParaAdopcion suscripcionParaAdopcion = DummyData.getSuscripcionParaAdopcion(new NotificadorCorreo("fake@gmail.com"),repository);
    repository.agregar(suscripcionParaAdopcion);
    repository.darDeBaja(suscripcionParaAdopcion);
    assertEquals(1,  repository.getSuscripcionesDeBaja().size());
  }

}
