package entregaTPA4;

import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.informe.InformeConQR;
import modelo.notificacion.TipoNotificadorPreferido;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioInformes;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransientTest extends NuestraAbstractPersistenceTest {

  @Test
  public void transientTestDeInformeRescate() {
    InformeConQR informe = new InformeConQR(
        DummyData.getPersona(TipoNotificadorPreferido.CORREO),
        DummyData.getUbicacion(),
        DummyData.getMascotaEncontrada(DummyData.getFotos()),
        new ReceptorHogares(),
        DummyData.getMascotaRegistrada(TipoNotificadorPreferido.CORREO)
    );
    RepositorioInformes repositorioInformes = new RepositorioInformes();
    repositorioInformes.agregar(informe);
    assertEquals(1, repositorioInformes.listarInformesConQR().size());
    InformeConQR elMismoInforme = repositorioInformes.listarInformesConQR().get(0);
    elMismoInforme.getHogaresCercanos(100); // uso un metodo que romperia si el atributo @Transient esta null
  }

}
