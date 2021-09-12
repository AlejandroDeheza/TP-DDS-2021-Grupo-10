package repositorios;

import modelo.informe.InformeConQR;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import utils.DummyData;

public class RepositorioInformesTest {

  RepositorioInformes repositorioInformes;

  @BeforeEach
  public void contextLoad() {
    repositorioInformes = new RepositorioInformes();
  }

  @Test
  @DisplayName("si se utiliza informesDeUltimosNDias(), este devuelve un registro insertado previamente")
  public void listarMascotasEncontradasEnLosUltimos10DiasTest() {
    InformeConQR informe = new InformeConQR(null, null,
        DummyData.getMascotaEncontrada(DummyData.getFotos()), repositorioInformes, null, null);

    assertEquals(repositorioInformes.informesDeUltimosNDias(10).size(), 0);
    repositorioInformes.agregarInformeRescate(informe);
    assertEquals(repositorioInformes.informesDeUltimosNDias(10).size(), 1);
    assertEquals(informe, repositorioInformes.informesDeUltimosNDias(10).get(0));
  }

}
