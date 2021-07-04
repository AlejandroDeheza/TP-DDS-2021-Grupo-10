package entregaTPA3;

import modelo.asociacion.Asociacion;
import modelo.mascota.Animal;
import modelo.notificacion.NotificadorCorreo;
import modelo.pregunta.RespuestaDelAdoptante;
import modelo.suscripcion.SuscripcionParaAdopcion;
import modelo.suscripcion.Preferencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import utils.DummyData;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class Punto4 {

  NotificadorCorreo notificadorCorreo;
  RespuestaDelAdoptante respuestaAdoptante1 = new RespuestaDelAdoptante("Si", DummyData.getParDePreguntas1());
  RespuestaDelAdoptante respuestaAdoptante2 = new RespuestaDelAdoptante("3", DummyData.getParDePreguntas2());

  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
  }

  @Test
  public void sePuedeGenerarUnaPublicacionQueMuestraInteresDeAdopcion() {
    RepositorioSuscripcionesParaAdopciones repositorio = new RepositorioSuscripcionesParaAdopciones();

    SuscripcionParaAdopcion publicacion = new SuscripcionParaAdopcion(
        DummyData.getDatosDeContacto(notificadorCorreo),
        new Asociacion(DummyData.getUbicacion()),
        new Preferencia(DummyData.getCaracteristicasParaMascota(), Animal.PERRO),
        Arrays.asList(respuestaAdoptante1, respuestaAdoptante2)
    );

    repositorio.agregar(publicacion);
    assertEquals(repositorio.getSuscripciones().size(), 1);
  }

}
