package modelo.publicacion;

import modelo.informe.Ubicacion;
import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;
import modelo.persona.DatosDeContacto;
import modelo.usuario.Usuario;
import repositorios.RepositorioPublicaciones;

public abstract class Publicacion {

  private DatosDeContacto contactoPosteador;
  private Ubicacion ubicacionPosteador;
  private Notificador notificador;
  private RepositorioPublicaciones repositorioPublicaciones;
  // private RepositorioAsociaciones repositorioAsociaciones; // TODO: Integrar en futuras ocasiones
  // private Asociacion asociacion; // TODO: Integrar en futuras ocasiones

  public Publicacion(DatosDeContacto contactoPosteador, Ubicacion ubicacionPosteador,
      Notificador notificador, RepositorioPublicaciones repositorioPublicaciones) {
    // TODO: Hace falta validación? o confiar en lo de adentro?
    this.contactoPosteador = contactoPosteador;
    this.ubicacionPosteador = ubicacionPosteador;
    this.notificador = notificador;
    this.repositorioPublicaciones = repositorioPublicaciones;
  }

  /**
   * Marca la publicacion como publicacionProcesada y usa un Notificador
   */
  public void notificarPosteador(Usuario emisor, Notificacion notificacion) {
    this.repositorioPublicaciones.marcarPublicacionComoProcesada(this);
    // TODO: ¿Instancias de DarEnAdopcion también envían notificación?
    notificador.notificar(notificacion);
  }

  public DatosDeContacto getContactoPosteador() {
    return this.contactoPosteador;
  }

  public Ubicacion getUbicacionPosteador() {
    return this.ubicacionPosteador;
  }

  public Notificador getNotificador() {
    return this.notificador;
  }

}
