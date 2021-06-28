package modelo.publicacion;

import modelo.mascota.MascotaEncontrada;
import modelo.usuario.Usuario;
import repositorios.RepositorioRescates;
import modelo.persona.DatosDeContacto;
import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;

/**
 * Representa a Publicacion de la 2da entrega
 * 
 * @since Entrega 3
 */
public class Rescate extends Publicacion {

  private MascotaEncontrada mascotaEncontrada;
  private String cuerpoMensaje = "El dueño encontro una mascota que vos rescataste. Por favor comunicarse al ";
  private RepositorioRescates repositorioRescates;

  public Rescate(DatosDeContacto contactoRescatista, Notificador notificador, RepositorioRescates repositorioRescates,
      MascotaEncontrada mascotaEncontrada) {
    super(contactoRescatista, notificador);
    this.mascotaEncontrada = mascotaEncontrada;
    this.repositorioRescates = repositorioRescates;
  }

  @Override
  public void notificarAlPosteador(Usuario usuario) {
    this.getRepositorioRescates().marcarComoProcesada(this);
    super.notificarAlPosteador(usuario);
  }

  @Override
  public Notificacion generarNotificacion(Usuario duenio) {
    return new Notificacion(super.getContactoPosteador(), "Han Encontrado una mascota que rescataste!", null,
        cuerpoMensaje.concat(duenio.getPersona().getDatosDeContacto().getEmail()), "Hogar de Patitas");
  }

  public MascotaEncontrada getMascotaEncontrada() {
    return mascotaEncontrada;
  }

  public RepositorioRescates getRepositorioRescates() {
    return this.repositorioRescates;
  }
}
