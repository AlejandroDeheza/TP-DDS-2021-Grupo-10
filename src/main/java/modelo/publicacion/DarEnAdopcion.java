package modelo.publicacion;

import modelo.informe.Ubicacion;
import modelo.mascota.MascotaRegistrada;
import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;
import modelo.persona.DatosDeContacto;
import modelo.usuario.Usuario;
import repositorios.RepositorioPublicaciones;

public class DarEnAdopcion extends Publicacion {

  // TODO: Descomentar ante implementación de Respuesta
  // private List<Respuesta> respuestasDelDador;
  private MascotaRegistrada mascotaEnAdopcion;

  public DarEnAdopcion(DatosDeContacto contactoPosteador, Ubicacion ubicacionPosteador, Notificador notificador, MascotaRegistrada mascotaEnAdopcion,
      RepositorioPublicaciones repositorioPublicaciones /* , List<Respuesta> respuestasDelDador */) {
    super(contactoPosteador, ubicacionPosteador, notificador, repositorioPublicaciones);
    this.mascotaEnAdopcion = mascotaEnAdopcion;
    // this.respuestasDelDador = respuestasDelDador;
  }

  /**
   * @see Publicacion::notificarPosteador/2
   */
  @Override
  public Notificacion generarNotificacion(Usuario usuario) {
    return null; // TODO: No sé qué tan correcto sea devolver un null
  }

  // public List<Respuesta> getRespuestasDelDador() {
  // return this.respuestasDelDador;
  // }

  public MascotaRegistrada getMascotaEnAdopcion() {
    return mascotaEnAdopcion;
  }
}
