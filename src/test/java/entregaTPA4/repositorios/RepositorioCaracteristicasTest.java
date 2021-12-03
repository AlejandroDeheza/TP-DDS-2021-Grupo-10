package entregaTPA4.repositorios;

import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import repositorios.RepositorioCaracteristicas;
import utils.DummyData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositorioCaracteristicasTest extends NuestraAbstractPersistenceTest {

  RepositorioCaracteristicas repo = new RepositorioCaracteristicas();

  @Test
  @DisplayName("Agregar una caractersitica con dos valores posibles en un repositorio, aumenta el numero de caracteristicas en dos")
  public void agregarCaracteristicasConDosValoresPosiblesTest(){
    CaracteristicaConValoresPosibles caracteristicaParaAdmin = DummyData.getCaracteristicaParaAdmin();
    repo.agregarCaracteristicasConValoresPosibles(caracteristicaParaAdmin);
    assertEquals(caracteristicaParaAdmin.listarCaracteristicas().size(),  repo.listarTodos().size());
  }

  @Test
  @DisplayName("Eliminar una caractersitica con dos valores posibles en un repositorio, decrementa en dos el numero de caracteristicas")
  public void eliminarCaracteristicasConDosValoresPosiblesTest(){
    CaracteristicaConValoresPosibles caracteristicaParaAdmin = DummyData.getCaracteristicaParaAdmin();
    repo.agregarCaracteristicasConValoresPosibles(caracteristicaParaAdmin);
    repo.eliminarCaracteristicasConValoresPosibles(caracteristicaParaAdmin);
    assertEquals(0,  repo.listarTodos().size());
  }

  @Test
  @DisplayName("Si agrego una caracteristica con dos valores posibles la cantidad de caractersiticas que devuelve el repo es una")
  public void listarCaracteristicasConDosValoresPosiblesTest(){
    CaracteristicaConValoresPosibles unaCaracteristicaConDosValoresPosibles = DummyData.getCaracteristicaParaAdmin();
    repo.agregarCaracteristicasConValoresPosibles(unaCaracteristicaConDosValoresPosibles);
    assertEquals(1,  repo.getCaracteristicasConValoresPosibles().size());
    assertEquals(unaCaracteristicaConDosValoresPosibles.getValoresCaracteristicas().get(0),
        repo.listarTodos().get(0).getValorCaracteristica());
    assertEquals(unaCaracteristicaConDosValoresPosibles.getValoresCaracteristicas().get(1),
        repo.listarTodos().get(1).getValorCaracteristica());
  }

  @Test
  @DisplayName("Agregar una caracteristica en un repositorio, aumenta el numero de caracteristicas en dos")
  public void agregarCaracteristicaTest(){
    Caracteristica unaCaracteristica = DummyData.getCaracteristicasParaMascota().get(0);
    repo.agregar(unaCaracteristica);
    assertEquals(1,  repo.listarTodos().size());
  }

  @Test
  @DisplayName("Eliminar una caracteristica en un repositorio, decrementa el numero de caracteristicas en dos")
  public void eliminarCaracteristicaTest(){
    Caracteristica unaCaracteristica = DummyData.getCaracteristicasParaMascota().get(0);
    repo.agregar(unaCaracteristica);
    repo.eliminar(unaCaracteristica);
    assertEquals(0,  repo.listarTodos().size());
  }

  @Test
  @DisplayName("Listar caracteristicas me devuelve una lista con las caractersiticas agregadas")
  public void listarCaracteristicaTest(){
    Caracteristica unaCaracteristica = DummyData.getCaracteristicasParaMascota().get(0);
    repo.agregar(unaCaracteristica);
    List<Caracteristica> listaFromRepo = repo.listarTodos();
    assertEquals(listaFromRepo.get(0),  unaCaracteristica);
    assertEquals(1,  repo.listarTodos().size());
  }

}
