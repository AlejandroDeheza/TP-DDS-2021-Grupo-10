package servicio.notificacion;

import modelo.persona.DatosDeContacto;

public class Notificacion {
  private DatosDeContacto destinatario;
  private String asunto;
  private String nombreSaludo;
  private String cuerpoMensaje;
  private String saludo = "Hogar de Patitas";

  public Notificacion(DatosDeContacto destinatario, String asunto, String nombreSaludo, String cuerpoMensaje,
                      String saludo) {
    this.destinatario = destinatario;
    this.asunto = asunto;
    this.nombreSaludo = nombreSaludo;
    this.cuerpoMensaje = cuerpoMensaje;
    if (saludo != null) this.saludo = saludo;
  }

  public DatosDeContacto getDestinatario() {
    return destinatario;
  }

  public String getAsunto() {
    return asunto;
  }

  public String getMensajeBody() {
    return "<p> Hola, " + nombreSaludo + "</p> </br></br>" +
            "<p>" + cuerpoMensaje + "</p></br></br>" +
            "<p> Saludos, </br>" +  saludo;
  }
}
