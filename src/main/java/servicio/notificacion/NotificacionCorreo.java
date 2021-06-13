package servicio.notificacion;

import excepciones.NotificacionCorreoException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

public class NotificacionCorreo implements NotificacionSender {

  Session session = configurarConexionCorreo();

  Function<Session, Transport> funcion = (sesion -> {
    Transport t = null;
    try {
      t = sesion.getTransport("smtp");
    } catch (MessagingException e) {
      throw new NotificacionCorreoException("Algo salio mal al generar el Transport en NotificacionCorreo", e);
    }
    return t;
  });

  // solo para tests
  public NotificacionCorreo(Function<Session, Transport> funcion) {
    this.funcion = funcion;
  }

  // para codigo productivo
  public NotificacionCorreo(){}

  @Override
  public void enviarNotificacion(Notificacion notificacion) {
    MimeMessage mensaje = this.crearMensaje(notificacion);
    this.enviarMensaje(mensaje, funcion.apply(session));
  }

  private Session configurarConexionCorreo(){
    Properties props = new Properties();
    props.setProperty("mail.smtp.host", "smtp.gmail.com");
    props.setProperty("mail.smtp.starttls.enable", "true");
    props.setProperty("mail.smtp.port","587");
    props.setProperty("mail.smtp.user", "rescatepatitasdds21@gmail.com");
    props.setProperty("mail.smtp.auth", "true");
    Session session = Session.getDefaultInstance(props);
    //session.setDebug(true);
    session.setDebug(false);
    return  session;
  }

  private void enviarMensaje(MimeMessage mensaje, Transport t){
      try {
        t.connect("rescatepatitasdds21@gmail.com","viernesNoche21");
        t.sendMessage(mensaje, mensaje.getAllRecipients());
        t.close();
      } catch (MessagingException e) {
        throw new NotificacionCorreoException("Algo salio mal al enviar el mensaje en NotificacionCorreo", e);
      }
  }

  private MimeMessage crearMensaje(Notificacion notificacion) {
    MimeMessage message = new MimeMessage(session);
    String email = notificacion.getDestinatario().getEmail();
    try {
      message.setFrom(new InternetAddress(email));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

      message.setSubject(notificacion.getAsunto());
      message.setText(notificacion.getMensajeBody(), "UTF-8", "html");
    } catch (MessagingException e) {
      throw new NotificacionCorreoException("Algo salio mal al crear el mensaje en NotificacionCorreo", e);
    }
    return message;
  }



}
