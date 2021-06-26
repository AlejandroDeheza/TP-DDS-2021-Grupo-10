package modelo.publicacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import excepciones.NoHayPreguntasObligatoriasException;
import excepciones.PreguntaSinRespuestaException;
import modelo.informe.Ubicacion;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.MascotaRegistrada;
import modelo.notificacion.Notificacion;
import modelo.notificacion.NotificadorCorreo;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
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
  // RepositorioPublicaciones repositorioPublicacionesMock;

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
   * TODO: Las preguntas de la asociación deberían ser conseguidas de la asociación. Integrar cambios
   * posteriormente.
   */
  PreguntaConRespuesta preguntaAsociacionConRespuesta;
  PreguntaConRespuesta preguntaAsociacionSinRespuesta;

  /* Listas de preguntas particulares a cada asociación */
  List<PreguntaConRespuesta> preguntasAsociacion;

  /* Posts de adopción */
  DarEnAdopcion publicacionDeDarEnAdopcionCorrecta;

  /* Etc */
  Usuario unUsuario;
  Persona unaPersona;
  Ubicacion unaUbicacion;
  MascotaRegistrada unaMascotaRegistrada;
  MascotaEncontrada unaMascotaEncontrada;

  @BeforeEach
  public void contextLoad() {

    this.notificadorCorreo = mock(NotificadorCorreo.class);
    // this.repositorioPublicacionesMock = mock(RepositorioPublicaciones.class);

    /* Preguntas obligatorias */
    this.preguntaObligatoriaConRespuesta1 = DummyData.getPreguntaConRespuesta("¿Su mascota come mucho?", "Sí, bastante");
    this.preguntaObligatoriaConRespuesta2 = DummyData.getPreguntaConRespuesta("¿Cuánto dinero gasta por mes en alimentos de su mascota?", "$5000");
    this.preguntaObligatoriaSinRespuesta1 = DummyData.getPreguntaConRespuesta("¿Cuántas veces lleva a su mascota a pasear usualmente?", null);
    this.preguntaObligatoriaSinRespuesta2 = DummyData.getPreguntaConRespuesta("¿Cómo se comporta su mascota frente a otros tipos de mascotas?", null);

    this.preguntasObligatoriasCorrectas = DummyData.getListadoPreguntasConRespuesta(this.preguntaObligatoriaConRespuesta1, this.preguntaObligatoriaConRespuesta2);
    this.preguntasObligatoriasSinRespuesta = DummyData.getListadoPreguntasConRespuesta(this.preguntaObligatoriaConRespuesta1, this.preguntaObligatoriaSinRespuesta1);

    /* Preguntas de cada asociación */
    this.preguntaAsociacionConRespuesta = DummyData.getPreguntaConRespuesta("¿Su mascota es muy activo?", "No");
    this.preguntaAsociacionSinRespuesta = DummyData.getPreguntaConRespuesta("¿Su mascota es vegano?", null);

    this.preguntasAsociacion = DummyData.getListadoPreguntasConRespuesta(this.preguntaAsociacionConRespuesta, this.preguntaAsociacionSinRespuesta);

    /* Publicaciones de DarEnAdopcion */
    publicacionDeDarEnAdopcionCorrecta = DummyData.getPublicacionDeDarEnAdopcionCorrecta(this.notificadorCorreo, new RepositorioPublicaciones(), this.preguntasObligatoriasCorrectas, this.preguntasAsociacion);

    /* Etc. */
    unUsuario = DummyData.getUsuario();
    unaPersona = this.unUsuario.getPersona();
    unaUbicacion = DummyData.getUbicacion();
    unaMascotaRegistrada = DummyData.getMascotaRegistrada(new RepositorioCaracteristicas());
    unaMascotaEncontrada = DummyData.getMascotaEncontrada(DummyData.getFotos());

  }

  @Test
  @DisplayName("Si un usuario encuentra a su mascota en una publicacion, se envia una Notificacion al rescatista")
  public void encontrarUnaMascotaPerdidaEnviaUnaNotificacion() {
    NotificadorCorreo notificadorCorreo = mock(NotificadorCorreo.class);
    // DummyData.getPublicacionDeRescate(notificadorCorreo, new
    // RepositorioPublicaciones()).notificarEncuentroAlRescatista(DummyData.getUsuario());
    RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();
    Usuario rescatista = DummyData.getUsuario();
    DummyData.getPublicacionDeRescate(notificadorCorreo, repositorioPublicaciones).notificarPosteador(rescatista, DummyData.getPublicacionDeRescate(this.notificadorCorreo, repositorioPublicaciones).generarNotificacion(rescatista));
    verify(notificadorCorreo).notificar(any());
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerUnaListaDePreguntasAsociadasEnNulo() {
    assertThrows(NoHayPreguntasObligatoriasException.class, () -> DummyData.getPublicacionDeDarEnAdopcionConPreguntasEnNulo(this.notificadorCorreo, new RepositorioPublicaciones()));
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerUnaListaDePreguntasAsociadasVacia() {
    assertThrows(NoHayPreguntasObligatoriasException.class, () -> DummyData.getPublicacionDeDarEnAdopcionConNingunaPregunta(this.notificadorCorreo));
  }

  @Test
  public void unaPublicacionDeDarEnAdopcionNoPuedeTenerPreguntasSinRespuesta() {
    assertThrows(PreguntaSinRespuestaException.class, () -> new DarEnAdopcion(DummyData.getDatosDeContacto(), DummyData.getUbicacion(), notificadorCorreo, DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()), new RepositorioPublicaciones(), this.preguntasObligatoriasSinRespuesta, null /* La asociación no posee preguntas en particular */));
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

  @Test
  public void procesarUnaPublicacionDeDarEnAdopcionProcesaNoEnviaUnaNotificacion() {
    RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();

    DarEnAdopcion publicacionDarEnAdopcion = new DarEnAdopcion(this.unaPersona.getDatosDeContacto(), this.unaUbicacion, this.notificadorCorreo, this.unaMascotaRegistrada, repositorioPublicaciones, this.preguntasObligatoriasCorrectas, null);

    Notificacion unaNotificacion = publicacionDarEnAdopcion.generarNotificacion(this.unUsuario);

    assertEquals(repositorioPublicaciones.getPublicacionesPendientes().size(), 0);
    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 0);

    repositorioPublicaciones.agregarPublicacion(publicacionDarEnAdopcion);
    assertEquals(repositorioPublicaciones.getPublicacionesPendientes().size(), 1);
    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 0);

    publicacionDarEnAdopcion.lograrObjetivoDeLaPublicacion(unUsuario, unaNotificacion);
    assertEquals(repositorioPublicaciones.getPublicacionesPendientes().size(), 0);
    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 1);

    verify(this.notificadorCorreo, times(0)).notificar(unaNotificacion);
  }

  @Test
  public void procesarUnaPublicacionDeRescateEnviaUnaNotificacion() {
    RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();

    Rescate publicacionRescate = new Rescate(this.unaPersona.getDatosDeContacto(), this.unaUbicacion, this.notificadorCorreo, repositorioPublicaciones, this.unaMascotaEncontrada);

    Notificacion unaNotificacion = publicacionRescate.generarNotificacion(unUsuario);

    assertEquals(repositorioPublicaciones.getPublicacionesPendientes().size(), 0);
    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 0);

    repositorioPublicaciones.agregarPublicacion(publicacionRescate);
    assertEquals(repositorioPublicaciones.getPublicacionesPendientes().size(), 1);
    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 0);

    publicacionRescate.lograrObjetivoDeLaPublicacion(unUsuario, unaNotificacion);
    assertEquals(repositorioPublicaciones.getPublicacionesPendientes().size(), 0);
    assertEquals(repositorioPublicaciones.getPublicacionesProcesadas().size(), 1);

    verify(this.notificadorCorreo, times(1)).notificar(unaNotificacion);
  }
}
