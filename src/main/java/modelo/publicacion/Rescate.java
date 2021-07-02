package modelo.publicacion;

import modelo.asociacion.Asociacion;
import modelo.mascota.MascotaEncontrada;
import modelo.usuario.Usuario;
import repositorios.RepositorioRescates;
import modelo.persona.DatosDeContacto;
import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;

/**
 * Representa a Publicacion de la 2da entrega
 *
 * @since  Entrega 3
 */
public class Rescate extends Publicacion {

  private MascotaEncontrada mascotaEncontrada;
  private String cuerpoMensaje = "El dueño encontro una mascota que vos rescataste. Por favor comunicarse al ";
  private RepositorioRescates repositorioRescates;

  public Rescate(DatosDeContacto contactoRescatista, Notificador notificador, RepositorioRescates repositorioRescates,
                 MascotaEncontrada mascotaEncontrada, Asociacion asociacion) {
    super(contactoRescatista, notificador, asociacion);
    this.mascotaEncontrada = mascotaEncontrada;
    this.repositorioRescates = repositorioRescates;
  }

  @Override
  public void notificarAlPosteador(Usuario duenio) {
    repositorioRescates.marcarComoProcesada(this);
    super.notificarAlPosteador(duenio);
  }

  @Override
  protected Notificacion generarNotificacion(Usuario duenio) {
    return new Notificacion(
        this.getContactoPosteador(),
        "Han Encontrado una mascota que rescataste!",
        "Hola, ",
        cuerpoMensaje + duenio.getPersona().getDatosDeContacto().getEmail(),
        "Hogar de Patitas"
    );
  }

  public MascotaEncontrada getMascotaEncontrada() {
    return mascotaEncontrada;
  }

}
