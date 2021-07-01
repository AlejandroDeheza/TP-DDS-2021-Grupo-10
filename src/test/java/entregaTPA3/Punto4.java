package entregaTPA3;

import modelo.asociacion.Asociacion;
import modelo.mascota.Animal;
import modelo.notificacion.NotificadorCorreo;
import modelo.pregunta.Respuesta;
import modelo.publicacion.IntencionDeAdopcion;
import modelo.publicacion.Preferencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioIntencionesDeAdopcion;
import utils.DummyData;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class Punto4 {

  NotificadorCorreo notificadorCorreo;
  Respuesta respuestaAdoptante1 = new Respuesta("Si", DummyData.getParDePreguntas1());
  Respuesta respuestaAdoptante2 = new Respuesta("3", DummyData.getParDePreguntas2());

  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
  }

  @Test
  public void sePuedeGenerarUnaPublicacionDeIntencionDeApcion() {
    RepositorioIntencionesDeAdopcion repositorio = new RepositorioIntencionesDeAdopcion();

    IntencionDeAdopcion publicacion = new IntencionDeAdopcion(
        DummyData.getDatosDeContacto(),
        notificadorCorreo,
        new Preferencia(DummyData.getCaracteristicasParaMascota(new RepositorioCaracteristicas()), Animal.PERRO),
        Arrays.asList(respuestaAdoptante1, respuestaAdoptante2),
        new Asociacion(DummyData.getUbicacion())
    );

    repositorio.agregar(publicacion);
    assertEquals(repositorio.getIntencionesDeAdopcion().size(), 1);
  }

}
