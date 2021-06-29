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

  /**
   * Marca la publicacion como publicacionProcesada y usa un Notificador
   */
  public void notificarAlPosteador(Usuario usuario) {
    Notificacion notificacion = this.generarNotificacion(usuario);
    this.notificador.notificar(notificacion);
  }

  /**
   * @return La notificación dependiendo del tipo de publicación. En caso de que no genere una
   *         notificación devuelve null.
   */
  protected abstract Notificacion generarNotificacion(Usuario usuario);

  public DatosDeContacto getContactoPosteador() {
    return this.contactoPosteador;
  }

  public Notificador getNotificador() {
    return this.notificador;
  }
}
