package modelo.publicacion;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import excepciones.NoHayPreguntasObligatoriasException;
import excepciones.PreguntaSinRespuestaException;
import modelo.notificacion.NotificadorCorreo;
import repositorios.RepositorioCaracteristicas;
import utils.DummyData;

public class PublicacionAdopcionTest {

  /*
   * TODO: Las preguntas obligatorias deberían ser conseguidas del "repositorio de preguntas". Las
   * preguntas de la asociación deberían ser conseguidas de la asociación. Integrar cambios
   * posteriormente.
   */
  PreguntaConRespuesta preguntaObligatoriaConRespuesta1;
  PreguntaConRespuesta preguntaObligatoriaConRespuesta2;
  PreguntaConRespuesta preguntaObligatoriaSinRespuesta1;
  PreguntaConRespuesta preguntaObligatoriaSinRespuesta2;

  NotificadorCorreo notificadorCorreo;
  List<PreguntaConRespuesta> preguntasObligatoriasCorrectas;
  List<PreguntaConRespuesta> preguntasObligatoriasSinRespuesta;
  List<PreguntaConRespuesta> sinPreguntas;

  @BeforeEach
  public void contextLoad() {
    this.notificadorCorreo = mock(NotificadorCorreo.class);
    this.preguntaObligatoriaConRespuesta1 =
        new StubPreguntaConRespuesta("¿Su mascota come mucho?", "Sí, bastante");
    this.preguntaObligatoriaConRespuesta2 = new StubPreguntaConRespuesta(
        "¿Cuánto dinero gasta por mes en alimentos de su mascota?", "$5000");
    this.preguntaObligatoriaSinRespuesta1 = new StubPreguntaConRespuesta(
        "¿Cuántas veces lleva a su mascota a pasear usualmente?", null);
    this.preguntaObligatoriaSinRespuesta2 = new StubPreguntaConRespuesta(
        "¿Cómo se comporta su mascota frente a otros tipos de mascotas?", null);
    this.preguntasObligatoriasCorrectas =
        Arrays.asList(preguntaObligatoriaConRespuesta1, preguntaObligatoriaConRespuesta2);
    this.preguntasObligatoriasSinRespuesta =
        Arrays.asList(preguntaObligatoriaConRespuesta1, preguntaObligatoriaSinRespuesta1);
    this.sinPreguntas = new ArrayList<>();
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerUnaListaDePreguntasAsociadasEnNulo() {
    assertThrows(NoHayPreguntasObligatoriasException.class,
        () -> new DarEnAdopcion(DummyData.getDatosDeContacto(), DummyData.getUbicacion(), null,
            null /* La asociación no posee preguntas extras */,
            DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()), notificadorCorreo));
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerUnaListaDePreguntasAsociadasVacia() {
    assertThrows(NoHayPreguntasObligatoriasException.class,
        () -> new DarEnAdopcion(DummyData.getDatosDeContacto(), DummyData.getUbicacion(),
            this.sinPreguntas, null /* La asociación no posee preguntas extras */,
            DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()), notificadorCorreo));
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerPreguntasSinRespuesta() {
    assertThrows(PreguntaSinRespuestaException.class,
        () -> new DarEnAdopcion(DummyData.getDatosDeContacto(), DummyData.getUbicacion(),
            this.preguntasObligatoriasSinRespuesta,
            null /* La asociación no posee preguntas extras */,
            DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()), notificadorCorreo));
  }

  // @Test
  // @DisplayName("Se puede generar una publicación para dar en adopción a una mascota")
  // public void unaPersonaPuedeGenerarUnaPublicacionParaDarEnAdopcionASuMascota() {
  //
  //
  //
  // verify(notificadorCorreo).notificar(any());
  // }

}
