package repositorios;

import static org.junit.jupiter.api.Assertions.*;
import modelo.informe.InformeConQR;
import org.junit.jupiter.api.*;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import utils.DummyData;

public class RepositorioInformesTest extends NuestraAbstractPersistenceTest {

  RepositorioInformes repositorioInformes = new RepositorioInformes();

  @Test
  @DisplayName("si se utiliza informesDeUltimosNDias(), este devuelve un registro insertado previamente")
  public void listarMascotasEncontradasEnLosUltimos10DiasTest() {
    InformeConQR informe = new InformeConQR(null, null,
        DummyData.getMascotaEncontrada(DummyData.getFotos()), null, null);

    assertEquals(repositorioInformes.informesDeUltimosNDias(10).size(), 0);
    repositorioInformes.agregar(informe);
    assertEquals(repositorioInformes.informesDeUltimosNDias(10).size(), 1);
    assertEquals(informe, repositorioInformes.informesDeUltimosNDias(10).get(0));
  }

}
