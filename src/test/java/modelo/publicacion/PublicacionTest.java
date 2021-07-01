package modelo.publicacion;

import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.ParDeRespuestas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import modelo.notificacion.NotificadorCorreo;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioRescates;
import utils.DummyData;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PublicacionTest {

  NotificadorCorreo notificadorCorreo;

  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
  }

  @Test
  @DisplayName("Si un usuario encuentra a su mascota en una publicacion, se envia una Notificacion al rescatista")
  public void encontrarUnaMascotaPerdidaEnviaUnaNotificacion() {
    Rescate publicacion = DummyData.getPublicacionDeRescate(notificadorCorreo, new RepositorioRescates());
    publicacion.notificarAlPosteador(DummyData.getUsuario());
    verify(notificadorCorreo, times(1)).notificar(any());
  }

  @Test
  @DisplayName("Si un adoptante desea adoptar una mascota de una publicacion, se envia una Notificacion al duenio")
  public void procesarUnaPublicacionDeDarEnAdopcionProcesaEnviaUnaNotificacion() {
    DarEnAdopcion publicacion = DummyData.getPublicacionDeDarEnAdopcion(notificadorCorreo, new RepositorioDarEnAdopcion(), null);
    publicacion.notificarAlPosteador(DummyData.getUsuario());
    verify(notificadorCorreo, times(1)).notificar(any());
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
