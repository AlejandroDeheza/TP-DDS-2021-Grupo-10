package entregaTPA4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import modelo.informe.InformeConQR;
import modelo.notificacion.NotificadorCorreo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import repositorios.RepositorioInformes;
import utils.DummyData;

public class Prueba extends NuestraAbstractPersistenceTest {

  RepositorioInformes repositorioInformes;

  @BeforeEach
  public void contextLoad() {
    repositorioInformes = new RepositorioInformes();
  }

  @Test
  @DisplayName("si se utiliza informesDeUltimosNDias(), este devuelve un registro insertado previamente")
  public void listarMascotasEncontradasEnLosUltimos10DiasTest() {
    NotificadorCorreo notificadorCorreo = new NotificadorCorreo("un_email");

    InformeConQR informe = new InformeConQR(DummyData.getPersona(notificadorCorreo), DummyData.getUbicacion(),
        DummyData.getMascotaEncontrada(DummyData.getFotos()), null,
        DummyData.getMascotaRegistrada(notificadorCorreo));

    assertEquals(repositorioInformes.informesDeUltimosNDias(10).size(), 0);
    entityManager().persist(informe);
    assertEquals(repositorioInformes.informesDeUltimosNDias(10).size(), 1);
    assertEquals(informe.getId(), repositorioInformes.informesDeUltimosNDias(10).get(0).getId());
  }
}
