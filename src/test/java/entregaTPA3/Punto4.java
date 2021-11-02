package entregaTPA3;

import modelo.asociacion.Asociacion;
import modelo.mascota.Animal;
import modelo.pregunta.RespuestaDelAdoptante;
import modelo.suscripcion.Preferencia;
import modelo.suscripcion.SuscripcionParaAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DummyData;
import utils.MockNotificador;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class Punto4 {

  MockNotificador mockNotificador;
  RespuestaDelAdoptante respuestaAdoptante1 = new RespuestaDelAdoptante("Si", DummyData.getParDePreguntas1());
  RespuestaDelAdoptante respuestaAdoptante2 = new RespuestaDelAdoptante("3", DummyData.getParDePreguntas2());

  @BeforeEach
  public void contextLoad() {
    mockNotificador = DummyData.getMockNotificador();
  }

  @Test
  public void sePuedeGenerarUnaPublicacionQueMuestraInteresDeAdopcion() {
    assertDoesNotThrow(() -> new SuscripcionParaAdopcion(
        DummyData.getUsuario(mockNotificador.getTipo()),
        new Asociacion("", DummyData.getUbicacion()),
        new Preferencia(DummyData.getCaracteristicasParaMascota(), Animal.PERRO),
        Arrays.asList(respuestaAdoptante1, respuestaAdoptante2)
    ));
  }

}
