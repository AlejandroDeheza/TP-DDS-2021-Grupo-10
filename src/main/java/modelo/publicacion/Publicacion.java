package modelo.publicacion;

import modelo.informe.Ubicacion;
import modelo.notificacion.Notificador;
import modelo.persona.DatosDeContacto;
import modelo.usuario.Usuario;

public class Publicacion {

  private DatosDeContacto contactoPosteador;
  private Ubicacion ubicacionPosteador;
  private Notificador notificador;
  // private RepositorioAsociaciones repositorioAsociaciones; // TODO: Integrar en futuras ocasiones
  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  // private Asociacion asociacion; // TODO: Integrar en futuras ocasiones
  // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

  public Publicacion(DatosDeContacto contactoPosteador, Ubicacion ubicacionPosteador,
      Notificador notificador) {
    // TODO: Hace falta validación? o confiar en lo de adentro?
    this.contactoPosteador = contactoPosteador;
    this.ubicacionPosteador = ubicacionPosteador;
    this.notificador = notificador;
  }

  public void notificarPosteador(Usuario emisor) {
    // TODO: Usa un Notificador y marca la publicacion como publicacionProcesada.
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
