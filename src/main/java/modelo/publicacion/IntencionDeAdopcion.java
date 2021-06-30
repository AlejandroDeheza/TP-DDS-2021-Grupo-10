package modelo.publicacion;

import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;
import modelo.persona.DatosDeContacto;
import modelo.usuario.Usuario;
import repositorios.RepositorioIntencionesDeAdopcion;

public class IntencionDeAdopcion extends Publicacion {

  // TODO: Descomentar ante implementación de Respuesta
  // private List<Respuesta> comodidadesDelAdoptante;
  private Preferencia preferenciaDelAdoptante;

  private RepositorioIntencionesDeAdopcion repositorioIntencionesDeAdopcion;

  public IntencionDeAdopcion(DatosDeContacto contactoPosteador, Notificador notificador,
      RepositorioIntencionesDeAdopcion repositorioIntencionesDeAdopcion,
      Preferencia preferenciaDelAdoptante /* , List<Respuesta> comodidadesDelAdoptante */) {
    super(contactoPosteador, notificador);
    this.repositorioIntencionesDeAdopcion = repositorioIntencionesDeAdopcion;
    this.preferenciaDelAdoptante = preferenciaDelAdoptante;
  }

  @Override
  protected Notificacion generarNotificacion(Usuario usuario) {
    // TODO Auto-generated method stub
    return null;
  }

  public Preferencia getPreferenciaDelAdoptante() {
    return this.preferenciaDelAdoptante;
  }

  public RepositorioIntencionesDeAdopcion getRepositorioIntencionesDeAdopcion() {
    return this.repositorioIntencionesDeAdopcion;
  }
}
