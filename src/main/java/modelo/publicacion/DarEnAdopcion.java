package modelo.publicacion;

import modelo.asociacion.Asociacion;
import modelo.mascota.MascotaRegistrada;
import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;
import modelo.persona.DatosDeContacto;
import modelo.pregunta.Respuesta;
import modelo.usuario.Usuario;
import repositorios.RepositorioDarEnAdopcion;

import java.util.List;

public class DarEnAdopcion extends Publicacion {

  private List<Respuesta> respuestasDelDador;
  private MascotaRegistrada mascotaEnAdopcion;
  private RepositorioDarEnAdopcion repositorio;
  private String cuerpoMensaje;

  public DarEnAdopcion(DatosDeContacto contactoPosteador, Notificador notificador, MascotaRegistrada mascotaEnAdopcion,
                       RepositorioDarEnAdopcion repositorio, List<Respuesta> respuestasDelDador,
                       Asociacion asociacion) {
    super(contactoPosteador, notificador, asociacion);
    this.mascotaEnAdopcion = mascotaEnAdopcion;
    this.repositorio = repositorio;
    this.cuerpoMensaje = "Encontramos una persona que quiere adoptar a " + mascotaEnAdopcion.getNombre()
        + ". Podes comunicarte con el adoptante por este mail: ";
    this.respuestasDelDador = respuestasDelDador;
  }

  @Override
  public void notificarAlPosteador(Usuario adoptante) {
    repositorio.marcarComoProcesada(this);
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

  public List<Respuesta> getRespuestasDelDador() {
    return respuestasDelDador;
  }

}
