package entregaTPA3;

import modelo.asociacion.Asociacion;
import modelo.notificacion.NotificadorCorreo;
import modelo.pregunta.RespuestaDelDador;
import modelo.publicacion.DarEnAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import utils.DummyData;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

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
    RepositorioDarEnAdopcion repositorio = new RepositorioDarEnAdopcion();

    DarEnAdopcion publicacion = new DarEnAdopcion(
        DummyData.getUsuario(notificadorCorreo),
        DummyData.getMascotaRegistrada(notificadorCorreo),
        repositorio,
        Arrays.asList(respuestaDelDadorDador1, respuestaDelDadorDador2),
        new Asociacion("", DummyData.getUbicacion())
    );

    repositorio.agregar(publicacion);
    assertEquals(repositorio.getPublicaciones().size(), 1);
  }
}
