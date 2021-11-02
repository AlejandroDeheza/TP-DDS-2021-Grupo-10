package entregaTPA3;

import modelo.asociacion.Asociacion;
import modelo.pregunta.RespuestaDelDador;
import modelo.publicacion.DarEnAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DummyData;
import utils.MockNotificador;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class Punto1 {

  MockNotificador mockNotificador;
  RespuestaDelDador respuestaDelDadorDador1 = new RespuestaDelDador("Si", DummyData.getParDePreguntas1());
  RespuestaDelDador respuestaDelDadorDador2 = new RespuestaDelDador("2", DummyData.getParDePreguntas2());

  @BeforeEach
  public void contextLoad() {
    mockNotificador = DummyData.getMockNotificador();
  }

  @Test
  public void sePuedeGenerarUnaPublicacionParaDarEnAdopcionAUnaMascota() {
    assertDoesNotThrow(
        () -> new DarEnAdopcion(
            DummyData.getUsuario(mockNotificador.getTipo()),
            DummyData.getMascotaRegistrada(mockNotificador.getTipo()),
            Arrays.asList(respuestaDelDadorDador1, respuestaDelDadorDador2),
            new Asociacion("", DummyData.getUbicacion())
    ));

  }
}
