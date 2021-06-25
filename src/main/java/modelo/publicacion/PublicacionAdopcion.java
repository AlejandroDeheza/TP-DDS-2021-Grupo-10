package modelo.publicacion;

import modelo.informe.Ubicacion;
import modelo.persona.DatosDeContacto;
import modelo.usuario.Usuario;

public class PublicacionAdopcion {
  // TODO: ¿En realidad es la clase Publicacion de entregas anteriores? Origen de duda: Publicacion
  // posee MascotaRegistrada -> Puede que el dueño nunca haya perdido a la mascota, por lo que no
  // sería una MascotaEncontrada (este a su vez posee fechaEncuentro, por lo que en caso de nunca
  // haberse perdido la mascota, no tendría una fecha de encuentro).

  private DatosDeContacto contactoPosteador;
  private Ubicacion ubicacionPosteador;
  // private RepositorioAsociaciones repositorioAsociaciones; // TODO: Integrar en futuras ocasiones

  public PublicacionAdopcion(DatosDeContacto contactoPosteador, Ubicacion ubicacionPosteador) {
    // TODO: Hace falta validación? o confiar en lo de adentro?
    this.contactoPosteador = contactoPosteador;
    this.ubicacionPosteador = ubicacionPosteador;
  }

  public void notificarPosteador(Usuario emisor) {
    // TODO: Usa un Notificador y marca la publicacion como publicacionProcesada.
  }

  public DatosDeContacto getContactoPosteador() {
    return contactoPosteador;
  }

  public Ubicacion getUbicacionPosteador() {
    return ubicacionPosteador;
  }

}
