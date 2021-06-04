package servicio.notificacion;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class NotificacionCorreo implements NotificacionSender {

  Session session = configurarConexionCorreo();

  @Override
  public void enviarNotificacion(Notificacion notificacion) {
    MimeMessage mensaje = this.crearMensaje(notificacion);
    this.enviarMensaje(mensaje);
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

  private void enviarMensaje(MimeMessage mensaje){
      try {
        Transport t = session.getTransport("smtp");
        t.connect("rescatepatitasdds21@gmail.com","viernesNoche21");
        t.sendMessage(mensaje,mensaje.getAllRecipients());
        t.close();
      } catch (MessagingException e) {
        e.printStackTrace();
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
      e.printStackTrace();
    }
    return message;
  }



}
