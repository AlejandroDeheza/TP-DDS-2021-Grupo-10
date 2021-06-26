package modelo.publicacion;

import java.util.List;
import excepciones.PreguntaSinRespuestaException;
import excepciones.NoHayPreguntasObligatoriasException;
import modelo.informe.Ubicacion;
import modelo.mascota.MascotaRegistrada;
import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;
import modelo.persona.DatosDeContacto;
import modelo.usuario.Usuario;
import repositorios.RepositorioPublicaciones;

public class DarEnAdopcion extends Publicacion {

  private List<PreguntaConRespuesta> preguntasObligatorias; // TODO: PreguntaConRespuesta fue
                                                            // stubeado
  private List<PreguntaConRespuesta> preguntasDeLaAsociacion; // TODO: PreguntaConRespuesta fue
                                                              // stubeado
  private MascotaRegistrada mascotaEnAdopcion;

  public DarEnAdopcion(DatosDeContacto contactoPosteador, Ubicacion ubicacionPosteador, Notificador notificador, MascotaRegistrada mascotaEnAdopcion, RepositorioPublicaciones repositorioPublicaciones, List<PreguntaConRespuesta> preguntasObligatorias, List<PreguntaConRespuesta> preguntasDeLaAsociacion) {
    super(contactoPosteador, ubicacionPosteador, notificador, repositorioPublicaciones);
    this.mascotaEnAdopcion = mascotaEnAdopcion;

    this.validarEntradas(preguntasObligatorias);
    // TODO: Si las preguntas obligatorias-mancomunadas entre las asociaciones está en el
    // repositorio de asociaciones, debería hacer un
    // this.repositorioAsociaciones.getPreguntasObligatorias();
    this.preguntasObligatorias = preguntasObligatorias;

    // TODO: En realidad creo que debería hacer un this.asociacion.getPreguntas();
    this.preguntasDeLaAsociacion = preguntasDeLaAsociacion;
  }

  public void validarEntradas(List<PreguntaConRespuesta> preguntasObligatorias) {
    if (preguntasObligatorias == null || preguntasObligatorias.isEmpty()) {
      throw new NoHayPreguntasObligatoriasException("Debe haber preguntas obligatorias por parte de las asociaciones");
    } else {
      if (preguntasObligatorias.stream().anyMatch(po -> po.esPreguntaSinRespuesta())) {
        throw new PreguntaSinRespuestaException("Se deben responder todas las preguntas obligatorias");
      }
    }
  }

  /**
   * @see Publicacion::notificarPosteador/2
   */
  @Override
  public Notificacion generarNotificacion(Usuario usuario) {
    return null;
  }

  public List<PreguntaConRespuesta> getPreguntasObligatorias() {
    return preguntasObligatorias;
  }

  public List<PreguntaConRespuesta> getPreguntasDeLaAsociacion() {
    return preguntasDeLaAsociacion;
  }

  public MascotaRegistrada getMascotaEnAdopcion() {
    return mascotaEnAdopcion;
  }
}
