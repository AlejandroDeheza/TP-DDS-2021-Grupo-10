package entregaTPA3;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import modelo.asociacion.Asociacion;
import modelo.mascota.Animal;
import modelo.notificacion.NotificadorCorreo;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.pregunta.RespuestaDelAdoptante;
import modelo.suscripcion.Preferencia;
import modelo.suscripcion.SuscripcionParaAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DummyData;
import java.util.Arrays;

public class Punto4 {

  NotificadorCorreo notificadorCorreo;
  RespuestaDelAdoptante respuestaAdoptante1 = new RespuestaDelAdoptante("Si", DummyData.getParDePreguntas1());
  RespuestaDelAdoptante respuestaAdoptante2 = new RespuestaDelAdoptante("3", DummyData.getParDePreguntas2());
  TipoNotificadorPreferido tipoNotificadorPreferido;

  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
    tipoNotificadorPreferido = mock(TipoNotificadorPreferido.class);
    when(tipoNotificadorPreferido.getNotificador(any())).thenReturn(notificadorCorreo);
  }

  @Test
  public void sePuedeGenerarUnaPublicacionQueMuestraInteresDeAdopcion() {
    assertDoesNotThrow(() -> new SuscripcionParaAdopcion(
        DummyData.getUsuario(tipoNotificadorPreferido),
        new Asociacion("", DummyData.getUbicacion()),
        new Preferencia(DummyData.getCaracteristicasParaMascota(), Animal.PERRO),
        Arrays.asList(respuestaAdoptante1, respuestaAdoptante2)
    ));
  }

}
