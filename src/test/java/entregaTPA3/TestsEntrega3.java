package entregaTPA3;

import modelo.asociacion.Asociacion;
import modelo.notificacion.NotificadorCorreo;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.ParDeRespuestas;
import modelo.pregunta.Respuesta;
import modelo.publicacion.DarEnAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioDarEnAdopcion;
import utils.DummyData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TestsEntrega3 {

  NotificadorCorreo notificadorCorreo;
  List<Respuesta> listaRespuestas;

  ParDePreguntas parDePreguntas1 = getParDePreguntas1();
  ParDePreguntas parDePreguntas2 = getParDePreguntas2();

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
  public void sePuedeGenerarUnaPublicacionParaDarEnAdopcionAUnaMascota() {
    RepositorioDarEnAdopcion repositorio = new RepositorioDarEnAdopcion();

    DarEnAdopcion publicacion = new DarEnAdopcion(
        DummyData.getDatosDeContacto(),
        notificadorCorreo,
        DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()),
        repositorio,
        asociacion
    );
    assertEquals(0, asociacion.getPreguntas().size());
    publicacion.addRespuesta(respuestaAdoptante1);
    publicacion.addRespuesta(respuestaAdoptante2);
    assertEquals(2, asociacion.getPreguntas().size());

    repositorio.agregar(publicacion);
    assertEquals(repositorio.getDarEnAdopcion().size(), 1);
  }

  @Test
  public void sePuedeGenerarUnaPublicacionDeIntencionDeApcion() {
    RepositorioDarEnAdopcion repositorio = new RepositorioDarEnAdopcion();

    DarEnAdopcion publicacion = new DarEnAdopcion(
        DummyData.getDatosDeContacto(),
        notificadorCorreo,
        DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()),
        repositorio,
        asociacion
    );
    assertEquals(0, asociacion.getPreguntas().size());
    publicacion.addRespuesta(respuestaAdoptante1);
    publicacion.addRespuesta(respuestaAdoptante2);
    assertEquals(2, asociacion.getPreguntas().size());

    repositorio.agregar(publicacion);
    assertEquals(repositorio.getDarEnAdopcion().size(), 1);
  }

  private ParDePreguntas getParDePreguntas1(){
    ParDePreguntas preguntas = new ParDePreguntas("La mascota sufre si está mucho tiempo sola?", "Va a estar la mascota mucho tiempo sola?");
    ParDeRespuestas respuesta1 = new ParDeRespuestas("Si", "No");
    ParDeRespuestas respuesta2 = new ParDeRespuestas("No", "Si");
    ParDeRespuestas respuesta3 = new ParDeRespuestas("No", "No");

    preguntas.agregarRespuesta(respuesta1);
    preguntas.agregarRespuesta(respuesta2);
    preguntas.agregarRespuesta(respuesta3);

    return preguntas;
  }

  private ParDePreguntas getParDePreguntas2(){
    ParDePreguntas preguntas = new ParDePreguntas("Cuantas veces necesita salir la mascota al dia?", "Cuantas veces sacarás a pasear a tu mascota al dia?");
    ParDeRespuestas respuesta1 = new ParDeRespuestas("1", "1");
    ParDeRespuestas respuesta2 = new ParDeRespuestas("1", "2");
    ParDeRespuestas respuesta3 = new ParDeRespuestas("2", "2");
    ParDeRespuestas respuesta4 = new ParDeRespuestas("1", "+2");
    ParDeRespuestas respuesta5 = new ParDeRespuestas("2", "+2");
    ParDeRespuestas respuesta6 = new ParDeRespuestas("+2", "+2");

    preguntas.agregarRespuesta(respuesta1);
    preguntas.agregarRespuesta(respuesta2);
    preguntas.agregarRespuesta(respuesta3);
    preguntas.agregarRespuesta(respuesta4);
    preguntas.agregarRespuesta(respuesta5);
    preguntas.agregarRespuesta(respuesta6);

    return preguntas;
  }
}
