package entregaTPA4;

import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import modelo.informe.InformeConQR;
import modelo.notificacion.TipoNotificadorPreferido;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioInformes;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Prueba extends NuestraAbstractPersistenceTest {

  RepositorioInformes repositorioInformes = new RepositorioInformes();

  @Test
  @DisplayName("si se utiliza informesDeUltimosNDias(), este devuelve un registro insertado previamente")
  public void listarMascotasEncontradasEnLosUltimos10DiasTest() {
    TipoNotificadorPreferido notificadorCorreo = TipoNotificadorPreferido.CORREO;

    InformeConQR informe = new InformeConQR(DummyData.getPersona(notificadorCorreo), DummyData.getUbicacion(),
        DummyData.getMascotaEncontrada(DummyData.getFotos()), null,
        DummyData.getMascotaRegistrada(notificadorCorreo));

    assertEquals(repositorioInformes.informesDeUltimosNDias(10).size(), 0);
    entityManager().persist(informe);
    assertEquals(repositorioInformes.informesDeUltimosNDias(10).size(), 1);
    assertEquals(informe.getId(), repositorioInformes.informesDeUltimosNDias(10).get(0).getId());
  }
}
