package modelo.publicacion;

import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import excepciones.NoHayPreguntasObligatoriasException;
import excepciones.PreguntaSinRespuestaException;
import modelo.notificacion.NotificadorCorreo;
import repositorios.RepositorioCaracteristicas;
import utils.DummyData;

public class PublicacionAdopcionTest {

  NotificadorCorreo notificadorCorreo;

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

  /* Posts de adopción */
  DarEnAdopcion postDeAdopcion;

  @BeforeEach
  public void contextLoad() {

    this.notificadorCorreo = mock(NotificadorCorreo.class);

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

    /* Posts de adopción */
    postDeAdopcion = new DarEnAdopcion(DummyData.getDatosDeContacto(), DummyData.getUbicacion(),
        notificadorCorreo, DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()),
        this.preguntasObligatoriasCorrectas, this.preguntasAsociacion);
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerUnaListaDePreguntasAsociadasEnNulo() {
    assertThrows(NoHayPreguntasObligatoriasException.class,
        () -> new DarEnAdopcion(DummyData.getDatosDeContacto(), DummyData.getUbicacion(),
            notificadorCorreo, DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()),
            null, null /* La asociación no posee preguntas en particular */));
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerUnaListaDePreguntasAsociadasVacia() {
    assertThrows(NoHayPreguntasObligatoriasException.class,
        () -> new DarEnAdopcion(DummyData.getDatosDeContacto(), DummyData.getUbicacion(),
            notificadorCorreo, DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()),
            this.sinPreguntas, null /* La asociación no posee preguntas en particular */));
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerPreguntasSinRespuesta() {
    assertThrows(PreguntaSinRespuestaException.class,
        () -> new DarEnAdopcion(DummyData.getDatosDeContacto(), DummyData.getUbicacion(),
            notificadorCorreo, DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()),
            this.preguntasObligatoriasSinRespuesta,
            null /* La asociación no posee preguntas en particular */));
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionPuedeTenerPreguntasParticularesDeLaAsociacion() {
    assertEquals(postDeAdopcion.getPreguntasDeLaAsociacion().size(), 2);
  }

  // @Test
  // @DisplayName("Se puede generar una publicación para dar en adopción a una mascota")
  // public void unaPersonaPuedeGenerarUnaPublicacionParaDarEnAdopcionASuMascota() {
  // postDeAdopcion.notificarPosteador(DummyData.getUsuario());
  // verify(notificadorCorreo).notificar(any());
  // }

}
