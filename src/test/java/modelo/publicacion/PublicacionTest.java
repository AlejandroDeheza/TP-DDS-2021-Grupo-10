package modelo.publicacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import excepciones.NoHayPreguntasObligatoriasException;
import excepciones.PreguntaSinRespuestaException;
import modelo.notificacion.NotificadorCorreo;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioPublicaciones;
import utils.DummyData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.List;

/**
 * @see RepositorioPublicacionesTest para ver la forma en cómo se procesa una publicación
 */
public class PublicacionTest {

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
  DarEnAdopcion publicacionDeDarEnAdopcionCorrecta;

  @BeforeEach
  public void contextLoad() {

    this.notificadorCorreo = mock(NotificadorCorreo.class);

    /* Preguntas obligatorias */
    this.preguntaObligatoriaConRespuesta1 =
        DummyData.getPreguntaConRespuesta("¿Su mascota come mucho?", "Sí, bastante");
    this.preguntaObligatoriaConRespuesta2 = DummyData.getPreguntaConRespuesta(
        "¿Cuánto dinero gasta por mes en alimentos de su mascota?", "$5000");
    this.preguntaObligatoriaSinRespuesta1 = DummyData
        .getPreguntaConRespuesta("¿Cuántas veces lleva a su mascota a pasear usualmente?", null);
    this.preguntaObligatoriaSinRespuesta2 = DummyData.getPreguntaConRespuesta(
        "¿Cómo se comporta su mascota frente a otros tipos de mascotas?", null);

    this.preguntasObligatoriasCorrectas = DummyData.getListadoPreguntasConRespuesta(
        this.preguntaObligatoriaConRespuesta1, this.preguntaObligatoriaConRespuesta2);
    this.preguntasObligatoriasSinRespuesta = DummyData.getListadoPreguntasConRespuesta(
        this.preguntaObligatoriaConRespuesta1, this.preguntaObligatoriaSinRespuesta1);

    /* Preguntas de cada asociación */
    this.preguntaAsociacionConRespuesta =
        DummyData.getPreguntaConRespuesta("¿Su mascota es muy activo?", "No");
    this.preguntaAsociacionSinRespuesta =
        DummyData.getPreguntaConRespuesta("¿Su mascota es vegano?", null);

    this.preguntasAsociacion = DummyData.getListadoPreguntasConRespuesta(
        this.preguntaAsociacionConRespuesta, this.preguntaAsociacionSinRespuesta);

    publicacionDeDarEnAdopcionCorrecta = DummyData.getPublicacionDeDarEnAdopcionCorrecta(
        this.notificadorCorreo, new RepositorioPublicaciones(), this.preguntasObligatoriasCorrectas,
        this.preguntasAsociacion);
  }

  @Test
  @DisplayName("Si un usuario encuentra a su mascota en una publicacion, se envia una Notificacion al rescatista")
  public void encontrarUnaMascotaPerdidaEnviaUnaNotificacion() {
    NotificadorCorreo notificadorCorreo = mock(NotificadorCorreo.class);
    DummyData.getPublicacionDeRescate(notificadorCorreo, new RepositorioPublicaciones())
        .notificarEncuentroAlRescatista(DummyData.getUsuario());
    verify(notificadorCorreo).notificar(any());
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerUnaListaDePreguntasAsociadasEnNulo() {
    assertThrows(NoHayPreguntasObligatoriasException.class,
        () -> DummyData.getPublicacionDeDarEnAdopcionConPreguntasEnNulo(this.notificadorCorreo,
            new RepositorioPublicaciones()));
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerUnaListaDePreguntasAsociadasVacia() {
    assertThrows(NoHayPreguntasObligatoriasException.class,
        () -> DummyData.getPublicacionDeDarEnAdopcionConNingunaPregunta(this.notificadorCorreo));
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerPreguntasSinRespuesta() {
    assertThrows(PreguntaSinRespuestaException.class,
        () -> new DarEnAdopcion(DummyData.getDatosDeContacto(), DummyData.getUbicacion(),
            notificadorCorreo, DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()),
            new RepositorioPublicaciones(), this.preguntasObligatoriasSinRespuesta,
            null /* La asociación no posee preguntas en particular */));
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionPuedeTenerPreguntasParticularesDeLaAsociacion() {
    assertEquals(publicacionDeDarEnAdopcionCorrecta.getPreguntasDeLaAsociacion().size(), 2);
  }

  @Test
  public void sePuedeGenerarUnaPublicacionParaDarEnAdopcionAUnaMascota() {
    RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();
    repositorioPublicaciones.agregarPublicacion(publicacionDeDarEnAdopcionCorrecta);
    assertEquals(repositorioPublicaciones.getPublicacionesPendientes().size(), 1);
  }
}
