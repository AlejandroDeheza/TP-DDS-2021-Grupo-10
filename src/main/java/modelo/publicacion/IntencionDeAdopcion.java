package modelo.publicacion;

import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;
import modelo.persona.DatosDeContacto;
import modelo.usuario.Usuario;
import repositorios.RepositorioPublicaciones;

public class IntencionDeAdopcion extends Publicacion {

  public IntencionDeAdopcion(DatosDeContacto contactoPosteador, Notificador notificador, RepositorioPublicaciones repositorioPublicaciones) {
    super(contactoPosteador, notificador, repositorioPublicaciones);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected Notificacion generarNotificacion(Usuario usuario) {
    // TODO Auto-generated method stub
    return null;
  }
}
