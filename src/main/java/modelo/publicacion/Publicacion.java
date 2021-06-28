package modelo.publicacion;

import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;
import modelo.persona.DatosDeContacto;
import modelo.usuario.Usuario;
import repositorios.RepositorioPublicaciones;

public abstract class Publicacion {

  private DatosDeContacto contactoPosteador;
  private Notificador notificador;
  private RepositorioPublicaciones repositorioPublicaciones;
  // private Asociacion asociacion; // TODO: Integrar en futuras ocasiones

  public Publicacion(DatosDeContacto contactoPosteador, Notificador notificador, RepositorioPublicaciones repositorioPublicaciones) {
    this.contactoPosteador = contactoPosteador;
    this.notificador = notificador;
    this.repositorioPublicaciones = repositorioPublicaciones;
  }

  /**
   * Marca la publicacion como publicacionProcesada y usa un Notificador
   */
  public void notificarAlPosteador(Usuario usuario) {
    this.repositorioPublicaciones.marcarPublicacionComoProcesada(this);
    Notificacion notificacion = this.generarNotificacion(usuario);
    if (notificacion != null) {
      this.notificador.notificar(notificacion);
    }
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
