package entregaTPA3;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

import modelo.asociacion.Asociacion;
import modelo.notificacion.NotificadorCorreo;
import modelo.pregunta.RespuestaDelDador;
import modelo.publicacion.DarEnAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DummyData;
import java.util.Arrays;

public class Punto1 {

  NotificadorCorreo notificadorCorreo;
  RespuestaDelDador respuestaDelDadorDador1 = new RespuestaDelDador("Si", DummyData.getParDePreguntas1());
  RespuestaDelDador respuestaDelDadorDador2 = new RespuestaDelDador("2", DummyData.getParDePreguntas2());

  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
  }

  @Test
  public void sePuedeGenerarUnaPublicacionParaDarEnAdopcionAUnaMascota() {
    assertDoesNotThrow(
        () -> new DarEnAdopcion(
            DummyData.getUsuario(notificadorCorreo),
            DummyData.getMascotaRegistrada(notificadorCorreo),
            null,
            Arrays.asList(respuestaDelDadorDador1, respuestaDelDadorDador2),
            new Asociacion("", DummyData.getUbicacion())
    ));

  }
}
