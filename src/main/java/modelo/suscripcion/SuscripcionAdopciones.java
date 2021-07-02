package modelo.suscripcion;

import java.util.List;
import java.util.stream.Collectors;

import modelo.asociacion.Asociacion;
import modelo.mascota.MascotaRegistrada;
import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;
import modelo.persona.DatosDeContacto;
import modelo.pregunta.Respuesta;

public class SuscripcionAdopciones {

  private DatosDeContacto contactoSuscriptor;
  private Notificador notificador;
  private Asociacion asociacion;
  private Preferencia preferenciaDelAdoptante;
  private List<Respuesta> comodidadesDelAdoptante;
  private String cuerpoMensajeConLinkDeBaja;
  private String cuerpoMensajeConRecomendacion;

  public SuscripcionAdopciones(DatosDeContacto contactoSuscriptor, Notificador notificador, Asociacion asociacion,
                               Preferencia preferenciaDelAdoptante, List<Respuesta> comodidadesDelAdoptante) {
    this.contactoSuscriptor = contactoSuscriptor;
    this.notificador = notificador;
    this.asociacion = asociacion;
    this.preferenciaDelAdoptante = preferenciaDelAdoptante;
    this.comodidadesDelAdoptante = comodidadesDelAdoptante;
    this.cuerpoMensajeConLinkDeBaja =
        "Tu intención de querer adoptar a una mascota se publicó exitosamente. " +
            "¡Semanalmente recibirás recomendaciones de adopción que se ajusten a tus preferencias y comodidades!" +
            " Si deseas dar de baja tu publicación, lo podrás hacer clickeando el siguiente link: <Inserte Link Aqui>";
    this.cuerpoMensajeConRecomendacion =
        "Encontramos las siguientes mascotas que creemos que pueden interesarte adoptar: ";
  }
  
  public void enviarRecomendaciones(List<MascotaRegistrada> recomendaciones) {
    String mascotas = recomendaciones.stream().map(MascotaRegistrada::getNombre).collect(Collectors.joining(","));
    notificarAlSuscriptor("Recomendaciones de adopcion", cuerpoMensajeConRecomendacion + mascotas);
  }

  public void enviarLinkDeBaja() {
    notificarAlSuscriptor("Publicación exitosa", cuerpoMensajeConLinkDeBaja);
  }

  private void notificarAlSuscriptor(String asunto, String cuerpoMensaje) {
    Notificacion notificacion = new Notificacion(
        contactoSuscriptor,
        asunto,
        "Hola,",
        cuerpoMensaje,
        "Hogar de patitas"
    );
    notificador.notificar(notificacion);
  }

  public Asociacion getAsociacion() {
    return asociacion;
  }

  public Preferencia getPreferenciaDelAdoptante() {
    return preferenciaDelAdoptante;
  }

  public List<Respuesta> getComodidadesDelAdoptante() {
    return this.comodidadesDelAdoptante;
  }

}
