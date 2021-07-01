package modelo.publicacion;

import java.util.List;
import modelo.mascota.MascotaRegistrada;
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
  private String cuerpoMensaje;

  public IntencionDeAdopcion(DatosDeContacto contactoPosteador, Notificador notificador,
      RepositorioIntencionesDeAdopcion repositorioIntencionesDeAdopcion,
      Preferencia preferenciaDelAdoptante /* , List<Respuesta> comodidadesDelAdoptante */) {
    super(contactoPosteador, notificador);
    this.repositorioIntencionesDeAdopcion = repositorioIntencionesDeAdopcion;
    this.preferenciaDelAdoptante = preferenciaDelAdoptante;
    this.cuerpoMensaje =
        "Tu intención de querer adoptar a una mascota se publicó exitosamente. ¡Semanalmente recibirás recomendaciones de adopción que se ajusten a tus preferencias y comodidades!";
  }
  
  public void enviarRecomendaciones(List<MascotaRegistrada> recomendaciones) {
    // TODO: Feature #37
  }

  @Override
  protected Notificacion generarNotificacion(Usuario usuario) {
    return new Notificacion(
        this.getContactoPosteador(),
        "Publicación exitosa",
        "Hola,",
        cuerpoMensaje + ". Si deseas dar de baja tu publicación, lo podrás hacer clickeando el siguiente link: <Inserte Link Aqui>",
        "Hogar de patitas");
  }

  public Preferencia getPreferenciaDelAdoptante() {
    return this.preferenciaDelAdoptante;
  }

  public RepositorioIntencionesDeAdopcion getRepositorioIntencionesDeAdopcion() {
    return this.repositorioIntencionesDeAdopcion;
  }
}
