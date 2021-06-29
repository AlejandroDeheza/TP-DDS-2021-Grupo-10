package modelo.publicacion;

import modelo.mascota.MascotaRegistrada;
import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;
import modelo.persona.DatosDeContacto;
import modelo.usuario.Usuario;
import repositorios.RepositorioDarEnAdopcion;

public class DarEnAdopcion extends Publicacion {

  // TODO: Descomentar ante implementación de Respuesta
  // private List<Respuesta> respuestasDelDador;
  private MascotaRegistrada mascotaEnAdopcion;
  private RepositorioDarEnAdopcion repositorioDarEnAdopcion;

  public DarEnAdopcion(DatosDeContacto contactoPosteador, Notificador notificador, MascotaRegistrada mascotaEnAdopcion,
      RepositorioDarEnAdopcion repositorioDarEnAdopcion /* , List<Respuesta> respuestasDelDador */) {
    super(contactoPosteador, notificador);
    this.mascotaEnAdopcion = mascotaEnAdopcion;
    this.repositorioDarEnAdopcion = repositorioDarEnAdopcion;
    // this.respuestasDelDador = respuestasDelDador;
  }

  @Override
  public void notificarAlPosteador(Usuario usuario) {
    this.getRepositorioDarEnAdopcion().marcarComoProcesada(this);
    super.notificarAlPosteador(usuario);
  }

  /**
   * @see Publicacion::notificarPosteador/2
   */
  @Override
  public Notificacion generarNotificacion(Usuario adoptante) {
    DatosDeContacto datosDeContacto = adoptante.getPersona().getDatosDeContacto();
    return new Notificacion(
        this.getContactoPosteador(), "Una persona quiere adoptar a tu mascota", "Hola, ", "Encontramos una persona que quiere adoptar a"
            + mascotaEnAdopcion.getNombre() + ". Podes comunicarte con el adoptante por este mail: " + datosDeContacto.getEmail(),
        "Hogar de patitas");
  }

  // public List<Respuesta> getRespuestasDelDador() {
  // return this.respuestasDelDador;
  // }

  public MascotaRegistrada getMascotaEnAdopcion() {
    return mascotaEnAdopcion;
  }

  public RepositorioDarEnAdopcion getRepositorioDarEnAdopcion() {
    return this.repositorioDarEnAdopcion;
  }
}
