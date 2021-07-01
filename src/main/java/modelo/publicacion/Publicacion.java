package modelo.publicacion;

import modelo.asociacion.Asociacion;
import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;
import modelo.persona.DatosDeContacto;
import modelo.usuario.Usuario;

public abstract class Publicacion {

  private DatosDeContacto contactoPosteador;
  private Notificador notificador;
  private Asociacion asociacion;

  public Publicacion(DatosDeContacto contactoPosteador, Notificador notificador, Asociacion asociacion) {
    this.contactoPosteador = contactoPosteador;
    this.notificador = notificador;
    this.asociacion = asociacion;
  }

  public void notificarAlPosteador(Usuario usuario) {
    Notificacion notificacion = this.generarNotificacion(usuario);
    notificador.notificar(notificacion);
  }

  protected abstract Notificacion generarNotificacion(Usuario usuario);

  public DatosDeContacto getContactoPosteador() {
    return this.contactoPosteador;
  }
}
