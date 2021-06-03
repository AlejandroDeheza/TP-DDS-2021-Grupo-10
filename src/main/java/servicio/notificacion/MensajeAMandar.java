package servicio.notificacion;

import modelo.persona.DatosDeContacto;
import modelo.persona.Persona;

public class MensajeAMandar {
  private String mensajeBody;

  private String asunto;

  public MensajeAMandar(Persona persona) {
    this.mensajeBody = "<p>Hola"+ persona.getNombre()+"!<br></p> " +
        "<p>Encontramos a tu mascota. Contactate con nosotros</p>"+
        "<p>Gracias.<br>" +
        "Saludos Rescate de patitas</p>";
    this.asunto = "Rescate de patitas: Encontramos a su mascota";
  }


  public String getMensajeBody() {
    return mensajeBody;
  }

  public String getAsunto() {
    return asunto;
  }


}
