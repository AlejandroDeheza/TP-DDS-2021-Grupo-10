package modelo.publicacion;

import java.util.List;
import java.util.stream.Collectors;

import modelo.asociacion.Asociacion;
import modelo.mascota.MascotaRegistrada;
import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;
import modelo.persona.DatosDeContacto;
import modelo.pregunta.Respuesta;
import modelo.usuario.Usuario;

public class IntencionDeAdopcion extends Publicacion {

  private List<Respuesta> comodidadesDelAdoptante;
  private Preferencia preferenciaDelAdoptante;
  private String cuerpoMensaje;

  public IntencionDeAdopcion(DatosDeContacto contactoPosteador, Notificador notificador,
                             Preferencia preferenciaDelAdoptante, List<Respuesta> comodidadesDelAdoptante,
                             Asociacion asociacion) {
    super(contactoPosteador, notificador, asociacion);
    this.preferenciaDelAdoptante = preferenciaDelAdoptante;
    this.cuerpoMensaje =
        "Tu intención de querer adoptar a una mascota se publicó exitosamente. " +
            "¡Semanalmente recibirás recomendaciones de adopción que se ajusten a tus preferencias y comodidades!";
    this.comodidadesDelAdoptante = comodidadesDelAdoptante;
  }
  
  public void enviarRecomendaciones(List<MascotaRegistrada> recomendaciones) {
    String mascotas = recomendaciones.stream().map(MascotaRegistrada::getNombre).collect(Collectors.joining(","));
    Notificacion notificacion = new Notificacion(
        this.getContactoPosteador(),
        "Recomendaciones de adopcion",
        "Hola,",
        "Encontramos las siguientes mascotas que creemos que pueden interesarte adoptar: " + mascotas,
        "Hogar de patitas");
    getNotificador().notificar(notificacion);
  }

  public void enviarLinkDeBaja() {
    notificarAlPosteador(null);
  }

  @Override
  protected Notificacion generarNotificacion(Usuario usuario) {
    return new Notificacion(
        this.getContactoPosteador(),
        "Publicación exitosa",
        "Hola,",
        cuerpoMensaje + ". Si deseas dar de baja tu publicación, " +
            "lo podrás hacer clickeando el siguiente link: <Inserte Link Aqui>",
        "Hogar de patitas");
  } // se usa con --> notificarAlPosteador(null) --> el cual se usa en --> enviarLinkDeBaja()

  public Preferencia getPreferenciaDelAdoptante() {
    return preferenciaDelAdoptante;
  }

  public List<Respuesta> getComodidadesDelAdoptante() {
    return this.comodidadesDelAdoptante;
  }

}
