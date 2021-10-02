package entregaTPA4.repositorios;

import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import repositorios.RepositorioCaracteristicas;
import utils.DummyData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositorioCaracteristicasTest extends NuestraAbstractPersistenceTest {

  RepositorioCaracteristicas repository;

  @BeforeEach
  public void contextLoad() {
    repository = new RepositorioCaracteristicas();
  }

  @Test
  @DisplayName("Agregar una caractersitica con dos valores posibles en un repositorio, aumenta el numero de caracteristicas en dos")
  public void agregarCaracteristicasConDosValoresPosiblesTest(){
    CaracteristicaConValoresPosibles caracteristicaParaAdmin = DummyData.getCaracteristicaParaAdmin();
    repository.agregarCaracteristicasConValoresPosibles(caracteristicaParaAdmin);
    assertEquals(caracteristicaParaAdmin.listarCaracteristicas().size(),  repository.getCaracteristicas().size());
  }

  @Test
  @DisplayName("Eliminar una caractersitica con dos valores posibles en un repositorio, decrementa en dos el numero de caracteristicas")
  public void eliminarCaracteristicasConDosValoresPosiblesTest(){
    CaracteristicaConValoresPosibles caracteristicaParaAdmin = DummyData.getCaracteristicaParaAdmin();
    repository.agregarCaracteristicasConValoresPosibles(caracteristicaParaAdmin);
    repository.eliminarCaracteristicasConValoresPosibles(caracteristicaParaAdmin);
    assertEquals(0,  repository.getCaracteristicas().size());
  }

  @Test
  @DisplayName("Si agrego una caracteristica con dos valores posibles la cantidad de caractersiticas que devuelve el repo es una")
  public void listarCaracteristicasConDosValoresPosiblesTest(){
    CaracteristicaConValoresPosibles unaCaracteristicaConDosValoresPosibles = DummyData.getCaracteristicaParaAdmin();
    repository.agregarCaracteristicasConValoresPosibles(unaCaracteristicaConDosValoresPosibles);
    assertEquals(1,  repository.getCaracteristicasConValoresPosibles().size());
    assertEquals(unaCaracteristicaConDosValoresPosibles.getValoresCaracteristicas().get(0),
        repository.getCaracteristicas().get(0).getValorCaracteristica());
    assertEquals(unaCaracteristicaConDosValoresPosibles.getValoresCaracteristicas().get(1),
        repository.getCaracteristicas().get(1).getValorCaracteristica());
  }

  @Test
  @DisplayName("Agregar una caracteristica en un repositorio, aumenta el numero de caracteristicas en dos")
  public void agregarCaracteristicaTest(){
    Caracteristica unaCaracteristica = DummyData.getCaracteristicasParaMascota().get(0);
    entityManager().persist(unaCaracteristica);
    assertEquals(1,  repository.getCaracteristicas().size());
  }

  @Test
  @DisplayName("Eliminar una caracteristica en un repositorio, decrementa el numero de caracteristicas en dos")
  public void eliminarCaracteristicaTest(){
    Caracteristica unaCaracteristica = DummyData.getCaracteristicasParaMascota().get(0);
    entityManager().persist(unaCaracteristica);
    entityManager().remove(unaCaracteristica);
    assertEquals(0,  repository.getCaracteristicas().size());
  }

  @Test
  @DisplayName("Listar caracteristicas me devuelve una lista con las caractersiticas agregadas")
  public void listarCaracteristicaTest(){
    Caracteristica unaCaracteristica = DummyData.getCaracteristicasParaMascota().get(0);
    entityManager().persist(unaCaracteristica);
    List<Caracteristica> listaFromRepo = repository.getCaracteristicas();
    assertEquals(listaFromRepo.get(0),  unaCaracteristica);
    assertEquals(1,  repository.getCaracteristicas().size());
  }

}
