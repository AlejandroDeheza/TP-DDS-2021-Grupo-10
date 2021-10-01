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
//
//  @Test
//  @DisplayName("Si agrego una caracteristica con dos valores posibles la cantidad de caractersiticas que devuelve el repo es una")
//  public void listarCaracteristicasConDosValoresPosiblesTest(){
//    CaracteristicaConValoresPosibles unaCaracteristicaConDosValoresPosibles = DummyData.getCaracteristicaParaAdmin();
//    repository.agregarCaracteristicasConValoresPosibles(unaCaracteristicaConDosValoresPosibles);
//    assertEquals(1,  repository.getCaracteristicasConValoresPosibles().size());
//    assertEquals(unaCaracteristicaConDosValoresPosibles.getValoresCaracteristicas().get(0),repository.getCaracteristicas().get(0).getValorCaracteristica());
//    assertEquals(unaCaracteristicaConDosValoresPosibles.getValoresCaracteristicas().get(1),repository.getCaracteristicas().get(1).getValorCaracteristica());
//  }
//
//  @Test
//  @DisplayName("Agregar una caracteristica en un repositorio, aumenta el numero de caracteristicas en dos")
//  public void agregarCaracteristicaTest(){
//    Caracteristica unaCaracteristica = DummyData.getCaracteristicasParaMascota().get(0);
//    repository.agregarCaracteristica(unaCaracteristica); //Esto agrega una caracteristicas a la DB.
//    assertEquals(1,  repository.getCaracteristicas().size());
//  }
//
//  @Test
//  @DisplayName("Eliminar una caracteristica en un repositorio, decrementa el numero de caracteristicas en dos")
//  public void eliminarCaracteristicaTest(){
//    Caracteristica unaCaracteristica = DummyData.getCaracteristicasParaMascota().get(0);
//    repository.agregarCaracteristica(unaCaracteristica); //Esto agrega una caracteristicas a la DB.
//    repository.eliminarCaracteristica(unaCaracteristica);
//    assertEquals(0,  repository.getCaracteristicas().size());
//  }
//
//  @Test
//  @DisplayName("Listar caracteristicas me devuelve una lista con las caractersiticas agregadas")
//  public void listarCaracteristicaTest(){
//    Caracteristica unaCaracteristica = DummyData.getCaracteristicasParaMascota().get(0);
//    repository.agregarCaracteristica(unaCaracteristica); //Esto agrega una caracteristicas a la DB.
//    List<Caracteristica> listaFromRepo = repository.getCaracteristicas();
//    assertEquals(listaFromRepo.get(0),  unaCaracteristica);
//    assertEquals(1,  repository.getCaracteristicas().size());
//  }

}
