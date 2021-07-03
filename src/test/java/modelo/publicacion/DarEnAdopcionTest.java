package modelo.publicacion;

import modelo.notificacion.NotificadorCorreo;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.Respuesta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import utils.DummyData;

import javax.mail.Transport;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

public class DarEnAdopcionTest {
  ParDePreguntas parDePreguntas = DummyData.getParDePreguntas1();
  List<Respuesta> respuestas = new ArrayList<>();

  Transport transportMockeado = mock(Transport.class);
  NotificadorCorreo notificadorCorreo = new NotificadorCorreo(sesion -> transportMockeado);
  DarEnAdopcion darEnAdopcion;

  @BeforeEach
  public void contextLoad() {
    respuestas.add(new Respuesta("No",parDePreguntas));
    darEnAdopcion = new DarEnAdopcion(DummyData.getDatosDeContacto(),notificadorCorreo
        ,DummyData.getMascotaRegistrada()
        ,new RepositorioDarEnAdopcion(),respuestas,DummyData.getAsociacion());
  }

  @Test
  @DisplayName("Las mismas preguntas matchean con las de la publicacion Dar en Adopcion")
  public void laCantidadDePreguntasQueMatcheanConLasDeLaPublicacionSonIguales() {
    assertEquals(respuestas.size(),darEnAdopcion.cantidadConLasQueMatchea(respuestas));
  }
  @Test
  @DisplayName("Si hay una sola respuesta y es invalida la cantidad de Matches es cero")
  public void siEsUnaRespuestaInvalidaLaCantidadDePreguntasQueMatcheanEsCero() {
    List<Respuesta> respuestasIncorrecta = new ArrayList<>();
    respuestasIncorrecta.add(new Respuesta("bla",parDePreguntas));
    assertEquals(0,darEnAdopcion.cantidadConLasQueMatchea(respuestasIncorrecta));
  }
}
