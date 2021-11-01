package modelo.publicacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import modelo.notificacion.NotificadorCorreo;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.RespuestaDelAdoptante;
import modelo.pregunta.RespuestaDelDador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import utils.DummyData;
import java.util.ArrayList;
import java.util.List;

public class DarEnAdopcionTest {

  ParDePreguntas parDePreguntas = DummyData.getParDePreguntas1();
  NotificadorCorreo notificadorCorreo;
  TipoNotificadorPreferido tipoNotificadorPreferido;
  DarEnAdopcion darEnAdopcion;

  @BeforeEach
  public void contextLoad() {
    List<RespuestaDelDador> respuestasDelDador = new ArrayList<>();
    notificadorCorreo = mock(NotificadorCorreo.class);
    respuestasDelDador.add(new RespuestaDelDador("No", parDePreguntas));
    darEnAdopcion = new DarEnAdopcion(DummyData.getUsuario(tipoNotificadorPreferido),
        DummyData.getMascotaRegistrada(tipoNotificadorPreferido), respuestasDelDador, DummyData.getAsociacion());
    tipoNotificadorPreferido = mock(TipoNotificadorPreferido.class);
    when(tipoNotificadorPreferido.getNotificador(any())).thenReturn(notificadorCorreo);
  }

  @Test
  @DisplayName("Si hay una sola respuesta y es valida la cantidad de Matches es uno")
  public void siEsUnaRespuestaValidaLaCantidadDePreguntasQueMatcheanEsUno() {
    List<RespuestaDelAdoptante> respuestasIncorrecta = new ArrayList<>();
    respuestasIncorrecta.add(new RespuestaDelAdoptante("Si", parDePreguntas));
    assertEquals(1, darEnAdopcion.cantidadConLasQueMatchea(respuestasIncorrecta));
  }

  @Test
  @DisplayName("Si hay una sola respuesta y es invalida la cantidad de Matches es cero")
  public void siEsUnaRespuestaInvalidaLaCantidadDePreguntasQueMatcheanEsCero() {
    List<RespuestaDelAdoptante> respuestasIncorrecta = new ArrayList<>();
    respuestasIncorrecta.add(new RespuestaDelAdoptante("bla", parDePreguntas));
    assertEquals(0, darEnAdopcion.cantidadConLasQueMatchea(respuestasIncorrecta));
  }
}
