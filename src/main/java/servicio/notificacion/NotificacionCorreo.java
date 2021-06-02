package servicio.notificacion;

import modelo.persona.DatosDeContacto;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class NotificacionCorreo implements NotificacionSender{


  @Override
  public void enviarNotificacion(DatosDeContacto dc) {
    Session session = configurarConexionCorreo();
    this.enviarMensaje(dc, session);

  }

  private Session configurarConexionCorreo(){
    Properties props = new Properties();

    props.setProperty("mail.smtp.host", "smtp.gmail.com");

    props.setProperty("mail.smtp.starttls.enable", "true");

    props.setProperty("mail.smtp.port","587");

    props.setProperty("mail.smtp.user", "dds2021g10@gmail.com");

    props.setProperty("mail.smtp.auth", "true");

    Session session = Session.getDefaultInstance(props);
    session.setDebug(true);

    return  session;
  }

  private MimeMessage crearMensaje(Session session, DatosDeContacto dc) {
    MimeMessage message = new MimeMessage(session);
    try {
      message.setFrom(new InternetAddress("dds2021g10@gmail.com"));

      message.addRecipient(Message.RecipientType.TO, new InternetAddress(dc.getEmail()));

      message.setSubject("Rescate de patitas: Encontramos a su mascota");
      message.setText("<p>Hola!</p> <p>Encontramos tu mascota</p>" +
          "<p>Comunicate con nosotros</p>" +
          "<p>Gracias.</p>" +
          "<p>Saludos Rescate de patitas</p>", "UTF-8", "html");
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return message;
  }

  private void enviarMensaje(DatosDeContacto datosDeContacto, Session session){
    try {
      MimeMessage message = crearMensaje(session, datosDeContacto);
      Transport t = session.getTransport("smtp");

      t.connect("rescatepatitasdds21@gmail.com","viernesNoche21");

      t.sendMessage(message,message.getAllRecipients());

      t.close();
    } catch (MessagingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();

    }

  }

}
