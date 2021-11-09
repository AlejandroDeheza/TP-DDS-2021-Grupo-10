package entregaTPA4;

import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.informe.InformeConQR;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.usuario.TipoUsuario;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransientTest extends NuestraAbstractPersistenceTest implements WithGlobalEntityManager {

  @Test
  public void transientTestDeUsuario() {
    Usuario usuario = new Usuario(
        "Gonzalo",
        "soyGonzalo",
        TipoUsuario.NORMAL,
        DummyData.getPersona(TipoNotificadorPreferido.CORREO)
    );
    Usuario elMismoUsuario = persistirYsacarDeLaDB(usuario, "from Usuario");
    elMismoUsuario.autenticarUsuario("soyGonzalo"); // uso un metodo que romperia si el atributo @Transient esta null
  }

  @Test
  public void transientTestDeInformeRescate() {
    InformeConQR informe = new InformeConQR(
        DummyData.getPersona(TipoNotificadorPreferido.CORREO),
        DummyData.getUbicacion(),
        DummyData.getMascotaEncontrada(DummyData.getFotos()),
        new ReceptorHogares(),
        DummyData.getMascotaRegistrada(TipoNotificadorPreferido.CORREO)
    );
    InformeConQR elMismoInforme = persistirYsacarDeLaDB(informe, "from InformeConQR");
    elMismoInforme.getHogaresCercanos(100); // uso un metodo que romperia si el atributo @Transient esta null
  }

  public <T> T persistirYsacarDeLaDB(T instancia, String jql) {
    entityManager().persist(instancia);
    assertEquals(1, entityManager().createQuery(jql, instancia.getClass()).getResultList().size());
    return (T) entityManager().createQuery(jql, instancia.getClass()).getResultList().get(0);
  }

}
