package servicio.notificacion;

import modelo.persona.DatosDeContacto;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class NotificacionCorreo implements NotificacionSender{


  @Override
  public void enviarNotificacion(DatosDeContacto datosDeContacto, MensajeAMandar mensaje) {
    Session session = configurarConexionCorreo();
    this.enviarMensaje(datosDeContacto, mensaje, session);

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

  private MimeMessage crearMensaje(Session session, DatosDeContacto datosDeContacto, MensajeAMandar mensaje) {
    MimeMessage message = new MimeMessage(session);
    try {
      message.setFrom(new InternetAddress(datosDeContacto.getEmail()));

      message.addRecipient(Message.RecipientType.TO, new InternetAddress(datosDeContacto.getEmail()));

      message.setSubject(mensaje.getAsunto());
      message.setText(mensaje.getMensajeBody(), "UTF-8", "html");
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return message;
  }

  private void enviarMensaje(DatosDeContacto datosDeContacto,MensajeAMandar mensaje, Session session){
    try {
      MimeMessage message = crearMensaje(session, datosDeContacto, mensaje);
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
