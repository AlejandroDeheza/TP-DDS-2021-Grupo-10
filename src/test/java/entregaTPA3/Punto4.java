package entregaTPA3;

import modelo.asociacion.Asociacion;
import modelo.mascota.Animal;
import modelo.notificacion.NotificadorCorreo;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.Respuesta;
import modelo.publicacion.IntencionDeAdopcion;
import modelo.publicacion.Preferencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioIntencionesDeAdopcion;
import utils.DummyData;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TestsEntrega3 {

  NotificadorCorreo notificadorCorreo;
  List<Respuesta> listaRespuestas;

  ParDePreguntas parDePreguntas1 = DummyData.getParDePreguntas1();
  ParDePreguntas parDePreguntas2 = DummyData.getParDePreguntas2();

  Respuesta respuestaDador1 = new Respuesta("Si", parDePreguntas1);
  Respuesta respuestaAdoptante1 = new Respuesta("Si", parDePreguntas1);

  Respuesta respuestaDador2 = new Respuesta("2", parDePreguntas2);
  Respuesta respuestaAdoptante2 = new Respuesta("3", parDePreguntas2);

  Asociacion asociacion = new Asociacion(DummyData.getUbicacion());


  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
    asociacion.agregarPregunta(parDePreguntas1);
    asociacion.agregarPregunta(parDePreguntas2);
  }

  @Test
  public void sePuedeGenerarUnaPublicacionDeIntencionDeApcion() {
    RepositorioIntencionesDeAdopcion repositorio = new RepositorioIntencionesDeAdopcion();

    IntencionDeAdopcion publicacion = new IntencionDeAdopcion(
        DummyData.getDatosDeContacto(),
        notificadorCorreo,
        new Preferencia(DummyData.getCaracteristicasParaMascota(new RepositorioCaracteristicas()), Animal.PERRO),
        Arrays.asList(respuestaAdoptante1, respuestaAdoptante2),
        asociacion
    );

    repositorio.agregar(publicacion);
    assertEquals(repositorio.getIntencionesDeAdopcion().size(), 1);
  }

}
