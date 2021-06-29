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
  private String cuerpoMensaje;

  public DarEnAdopcion(DatosDeContacto contactoPosteador, Notificador notificador, MascotaRegistrada mascotaEnAdopcion,
                       RepositorioDarEnAdopcion repositorioDarEnAdopcion /* , List<Respuesta> respuestasDelDador */) {
    super(contactoPosteador, notificador);
    this.mascotaEnAdopcion = mascotaEnAdopcion;
    this.repositorioDarEnAdopcion = repositorioDarEnAdopcion;
    this.cuerpoMensaje = "Encontramos una persona que quiere adoptar a " + mascotaEnAdopcion.getNombre()
        + ". Podes comunicarte con el adoptante por este mail: ";
    // this.respuestasDelDador = respuestasDelDador;
  }

  @Override
  public void notificarAlPosteador(Usuario adoptante) {
    repositorioDarEnAdopcion.marcarComoProcesada(this);
    super.notificarAlPosteador(adoptante);
  }

  @Override
  protected Notificacion generarNotificacion(Usuario adoptante) {
    return new Notificacion(
        this.getContactoPosteador(),
        "Una persona quiere adoptar a tu mascota",
        "Hola, ",
        cuerpoMensaje + adoptante.getPersona().getDatosDeContacto().getEmail(),
        "Hogar de patitas"
    );
  }

  public MascotaRegistrada getMascotaEnAdopcion() {
    return mascotaEnAdopcion;
  }

  // public List<Respuesta> getRespuestasDelDador() {
  // return this.respuestasDelDador;
  // }

}
