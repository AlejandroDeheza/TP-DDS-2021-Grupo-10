package modelo.publicacion;

import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;
import modelo.persona.DatosDeContacto;
import modelo.usuario.Usuario;

public abstract class Publicacion {

  private DatosDeContacto contactoPosteador;
  private Notificador notificador;
  // private Asociacion asociacion; // TODO: Integrar en futuras ocasiones

  public Publicacion(DatosDeContacto contactoPosteador, Notificador notificador) {
    this.contactoPosteador = contactoPosteador;
    this.notificador = notificador;
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
