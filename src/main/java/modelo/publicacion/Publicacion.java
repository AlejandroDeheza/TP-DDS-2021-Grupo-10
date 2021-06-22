package modelo.publicacion;

import modelo.mascota.Foto;
import modelo.usuario.Usuario;
import modelo.persona.DatosDeContacto;
import servicio.notificacion.Notificacion;
import servicio.notificacion.Notificador;

import java.util.List;

public class Publicacion {
  private DatosDeContacto datosDeContactoRescatista;
  private List<Foto> fotos;
  private Notificador notificacionCorreo;
  private String cuerpoMensaje = "El dueño encontro una mascota que vos rescataste. Por favor comunicarse al ";

  public Publicacion(DatosDeContacto datosDeContacto, List<Foto> fotos, Notificador notificacionCorreo) {
    this.datosDeContactoRescatista = datosDeContacto;
    this.fotos = fotos;
    this.notificacionCorreo = notificacionCorreo;
  }

  public void notificarEncuentroAlRescatista(Usuario duenio) {
    Notificacion notificacion = new Notificacion(
        datosDeContactoRescatista,
        null,
        cuerpoMensaje.concat(duenio.getPersona().getDatosDeContacto().getEmail()),
        "Hogar de Patitas",
        "Han Encontrado una mascota que rescataste!"
    );
    notificacionCorreo.enviarNotificacion(notificacion);
  }

  public List<Foto> getFotos() {
    return fotos;
  }
}
