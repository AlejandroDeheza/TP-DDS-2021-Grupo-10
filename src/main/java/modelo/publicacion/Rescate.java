package modelo.publicacion;

import modelo.informe.Ubicacion;
import modelo.mascota.MascotaEncontrada;
import modelo.usuario.Usuario;
import repositorios.RepositorioPublicaciones;
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
  private String cuerpoMensaje =
      "El dueño encontro una mascota que vos rescataste. Por favor comunicarse al ";

  public Rescate(DatosDeContacto contactoRescatista, Ubicacion ubicacionRescatista,
      Notificador notificador, RepositorioPublicaciones repositorioPublicaciones,
      MascotaEncontrada mascotaEncontrada) {
    super(contactoRescatista, ubicacionRescatista, notificador, repositorioPublicaciones);
    this.mascotaEncontrada = mascotaEncontrada;
  }

  public void notificarEncuentroAlRescatista(Usuario duenio) {
    Notificacion notificacion =
        new Notificacion(super.getContactoPosteador(), "Han Encontrado una mascota que rescataste!",
            null, cuerpoMensaje.concat(duenio.getPersona().getDatosDeContacto().getEmail()),
            "Hogar de Patitas");
    super.getNotificador().notificar(notificacion);
  }

  public MascotaEncontrada getMascotaEncontrada() {
    return mascotaEncontrada;
  }
}
