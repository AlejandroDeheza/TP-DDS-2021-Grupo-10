package modelo.publicacion;

import java.util.List;
import excepciones.PreguntaSinRespuestaException;
import excepciones.NoHayPreguntasObligatoriasException;
import modelo.informe.Ubicacion;
import modelo.mascota.MascotaRegistrada;
import modelo.notificacion.Notificador;
import modelo.persona.DatosDeContacto;

public class DarEnAdopcion extends PublicacionAdopcion {

  private List<PreguntaConRespuesta> preguntasObligatorias; // TODO: PreguntaConRespuesta fue stubeado
  // TODO [preguntasObligatorias]: verificar que no sea null en constructor, verificar tambien que
  // las respuestas tampoco sean null, se obtienen del repositorio de preguntas
  private List<PreguntaConRespuesta> preguntasDeLaAsociacion; // TODO: PreguntaConRespuesta fue stubeado
  private MascotaRegistrada mascotaEnAdopcion;
  private Notificador notificador;
  // private Asociacion asociacion; // TODO: Integrar en futuras ocasiones
  // private RepositorioAsociaciones repositorioAsociaciones; // TODO: Integrar en futuras ocasiones

  public DarEnAdopcion(DatosDeContacto contactoPosteador, Ubicacion ubicacionPosteador,
      List<PreguntaConRespuesta> preguntasObligatorias,
      List<PreguntaConRespuesta> preguntasDeLaAsociacion, MascotaRegistrada mascotaEnAdopcion,
      Notificador notificador) {
    super(contactoPosteador, ubicacionPosteador);
    this.validarEntradas(preguntasObligatorias);
    this.preguntasObligatorias = preguntasObligatorias;
    this.preguntasDeLaAsociacion = preguntasDeLaAsociacion;
    this.mascotaEnAdopcion = mascotaEnAdopcion;
  }

  public void validarEntradas(List<PreguntaConRespuesta> preguntasObligatorias) {
    if (preguntasObligatorias == null || preguntasObligatorias.isEmpty()) {
      throw new NoHayPreguntasObligatoriasException(
          "Debe haber preguntas obligatorias por parte de las asociaciones");
    } else {
      if (preguntasObligatorias.stream().anyMatch(po -> po.esPreguntaSinRespuesta())) {
        throw new PreguntaSinRespuestaException(
            "Se deben responder todas las preguntas obligatorias");
      }
    }
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

  public Notificador getNotificador() {
    return notificador;
  }
}
