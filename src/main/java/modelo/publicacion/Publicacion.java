package modelo.publicacion;

import modelo.mascota.MascotaEncontrada;
import modelo.usuario.Usuario;
import modelo.persona.DatosDeContacto;
import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;

public class Publicacion {
  private MascotaEncontrada mascotaEncontrada;
  private DatosDeContacto contactoRescatista;
  private Notificador notificador;
  private String cuerpoMensaje = "El dueño encontro una mascota que vos rescataste. Por favor comunicarse al ";

  public Publicacion(MascotaEncontrada mascotaEncontrada, DatosDeContacto contactoRescatista,
                     Notificador notificador) {
    this.mascotaEncontrada = mascotaEncontrada;
    this.contactoRescatista = contactoRescatista;
    this.notificador = notificador;
  }

  public void notificarEncuentroAlRescatista(Usuario duenio) {
    Notificacion notificacion = new Notificacion(
        contactoRescatista,
        "Han Encontrado una mascota que rescataste!",
        null,
        cuerpoMensaje.concat(duenio.getPersona().getDatosDeContacto().getEmail()),
        "Hogar de Patitas"
    );
    notificador.notificar(notificacion);
  }

  public MascotaEncontrada getMascotaEncontrada() {
    return mascotaEncontrada;
  }
}
