package modelo.publicacion;

import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import excepciones.NoHayPreguntasObligatoriasException;
import excepciones.PreguntaSinRespuestaException;
import modelo.notificacion.NotificadorCorreo;
import repositorios.RepositorioCaracteristicas;
import utils.DummyData;

public class PublicacionAdopcionTest {

  /*
   * TODO: Las preguntas obligatorias deberían ser conseguidas del "repositorio de preguntas".
   * Integrar cambios posteriormente.
   */
  PreguntaConRespuesta preguntaObligatoriaConRespuesta1;
  PreguntaConRespuesta preguntaObligatoriaConRespuesta2;
  PreguntaConRespuesta preguntaObligatoriaSinRespuesta1;
  PreguntaConRespuesta preguntaObligatoriaSinRespuesta2;

  /* Listas de preguntas obligatorias que son comunes a todas las asociaciones */
  List<PreguntaConRespuesta> preguntasObligatoriasCorrectas;
  List<PreguntaConRespuesta> preguntasObligatoriasSinRespuesta;
  List<PreguntaConRespuesta> sinPreguntas;

  /*
   * TODO: Las preguntas de la asociación deberían ser conseguidas de la asociación. Integrar
   * cambios posteriormente.
   */
  PreguntaConRespuesta preguntaAsociacionConRespuesta;
  PreguntaConRespuesta preguntaAsociacionSinRespuesta;

  /* Listas de preguntas particulares a cada asociación */
  List<PreguntaConRespuesta> preguntasAsociacion;

  NotificadorCorreo notificadorCorreo;

  @BeforeEach
  public void contextLoad() {

    /* Preguntas obligatorias */
    this.preguntaObligatoriaConRespuesta1 =
        new StubPreguntaConRespuesta("¿Su mascota come mucho?", "Sí, bastante");
    this.preguntaObligatoriaConRespuesta2 = new StubPreguntaConRespuesta(
        "¿Cuánto dinero gasta por mes en alimentos de su mascota?", "$5000");
    this.preguntaObligatoriaSinRespuesta1 = new StubPreguntaConRespuesta(
        "¿Cuántas veces lleva a su mascota a pasear usualmente?", null);
    this.preguntaObligatoriaSinRespuesta2 = new StubPreguntaConRespuesta(
        "¿Cómo se comporta su mascota frente a otros tipos de mascotas?", null);

    this.preguntasObligatoriasCorrectas =
        Arrays.asList(this.preguntaObligatoriaConRespuesta1, this.preguntaObligatoriaConRespuesta2);
    this.preguntasObligatoriasSinRespuesta =
        Arrays.asList(this.preguntaObligatoriaConRespuesta1, this.preguntaObligatoriaSinRespuesta1);
    this.sinPreguntas = new ArrayList<>();

    /* Preguntas de cada asociación (supuestas no obligatorias) */
    this.preguntaAsociacionConRespuesta =
        new StubPreguntaConRespuesta("¿Su mascota es muy activo?", "No");
    this.preguntaAsociacionSinRespuesta =
        new StubPreguntaConRespuesta("¿Su mascota es vegano?", null);

    this.preguntasAsociacion =
        Arrays.asList(this.preguntaAsociacionConRespuesta, this.preguntaAsociacionSinRespuesta);

    this.notificadorCorreo = mock(NotificadorCorreo.class);
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerUnaListaDePreguntasAsociadasEnNulo() {
    Assertions.assertThrows(NoHayPreguntasObligatoriasException.class,
        () -> new DarEnAdopcion(DummyData.getDatosDeContacto(), DummyData.getUbicacion(), null,
            null /* La asociación no posee preguntas en particular */,
            DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()), notificadorCorreo));
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerUnaListaDePreguntasAsociadasVacia() {
    Assertions.assertThrows(NoHayPreguntasObligatoriasException.class,
        () -> new DarEnAdopcion(DummyData.getDatosDeContacto(), DummyData.getUbicacion(),
            this.sinPreguntas, null /* La asociación no posee preguntas en particular */,
            DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()), notificadorCorreo));
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerPreguntasSinRespuesta() {
    Assertions.assertThrows(PreguntaSinRespuestaException.class,
        () -> new DarEnAdopcion(DummyData.getDatosDeContacto(), DummyData.getUbicacion(),
            this.preguntasObligatoriasSinRespuesta,
            null /* La asociación no posee preguntas en particular */,
            DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()), notificadorCorreo));
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionPuedeTenerPreguntasParticularesDeLaAsociacion() {
    DarEnAdopcion postDeAdopcion = new DarEnAdopcion(DummyData.getDatosDeContacto(),
        DummyData.getUbicacion(), this.preguntasObligatoriasCorrectas, this.preguntasAsociacion,
        DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()), notificadorCorreo);
    Assertions.assertEquals(postDeAdopcion.getPreguntasDeLaAsociacion().size(), 2);
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
