package servicio.notificacion;

import modelo.persona.DatosDeContacto;

public class Notificacion {
  private DatosDeContacto destinatario;
  private String nombreSaludo;
  private String cuerpoMensaje;
  private String asunto;
  private String saludo = "Hogar de Patitas";

  public Notificacion(DatosDeContacto destinatario, String nombreSaludo, String cuerpoMensaje, String saludo, String asunto) {
    this.destinatario = destinatario;
    this.nombreSaludo = nombreSaludo;
    this.cuerpoMensaje = cuerpoMensaje;
    this.saludo = saludo;
    this.asunto = asunto;
  }

  public DatosDeContacto getDestinatario() {
    return destinatario;
  }

  public String getNombreSaludo() {
    return nombreSaludo;
  }

  public String getCuerpoMensaje() {
    return cuerpoMensaje;
  }

  public String getSaludo() {
    return saludo;
  }

  public String getAsunto() {
    return this.asunto;
  }

  public String getMensajeBody() {
    return "<p> Hola, " + this.getNombreSaludo() + "</p> </br></br>" +
            "<p>" + this.getCuerpoMensaje() + "</p></br></br>" +
            "<p> Saludos, </br>" +  this.getSaludo();
  }
}
