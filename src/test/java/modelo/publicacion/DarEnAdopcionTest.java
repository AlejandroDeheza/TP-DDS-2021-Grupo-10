package modelo.publicacion;

import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.RespuestaDelAdoptante;
import modelo.pregunta.RespuestaDelDador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;
import utils.MockNotificador;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DarEnAdopcionTest {

  MockNotificador mockNotificador;
  ParDePreguntas parDePreguntas = DummyData.getParDePreguntas1();
  DarEnAdopcion darEnAdopcion;

  @BeforeEach
  public void contextLoad() {
    mockNotificador = DummyData.getMockNotificador();
    List<RespuestaDelDador> respuestasDelDador = new ArrayList<>();
    respuestasDelDador.add(new RespuestaDelDador("No", parDePreguntas));
    darEnAdopcion = new DarEnAdopcion(DummyData.getUsuario(mockNotificador.getTipo()),
        DummyData.getMascotaRegistrada(mockNotificador.getTipo()), respuestasDelDador, DummyData.getAsociacion());
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
