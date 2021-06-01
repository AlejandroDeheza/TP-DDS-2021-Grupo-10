package servicio.notificacion;

import modelo.persona.DatosDeContacto;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class NotificacionCorreo implements NotificacionSender{


  @Override
  public void enviarNotificacion(DatosDeContacto dc) {
    Session session = configurarConexionCorreo();
    this.crearMensaje(dc, session);

  }

  private Session configurarConexionCorreo(){
    Properties props = new Properties();
    // Nombre del host de correo, es smtp.gmail.com
    props.setProperty("mail.smtp.host", "smtp.gmail.com");

    // TLS si está disponible
    props.setProperty("mail.smtp.starttls.enable", "true");

    // Puerto de gmail para envio de correos
    props.setProperty("mail.smtp.port","587");

    // Nombre del usuario
    props.setProperty("mail.smtp.user", "dds2021g10@gmail.com");

    // Si requiere o no usuario y password para conectarse.
    props.setProperty("mail.smtp.auth", "true");

    //Con esto estamos en disposición de obtener nuestra instancia de Session.
    Session session = Session.getDefaultInstance(props);
    session.setDebug(true);

    return  session;
  }

  private void crearMensaje(DatosDeContacto datosDeContacto, Session session){
    MimeMessage message = new MimeMessage(session);


    try {
      // Quien envia el correo
      message.setFrom(new InternetAddress("dds2021g10@gmail.com"));

      // A quien va dirigido
      //message.addRecipient(Message.RecipientType.TO, new InternetAddress("marzm06@gmail.com"));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(datosDeContacto.getEmail()));
      //Ahora sólo queda llenar el asunto -subject que dirían los ingleses- del mensaje y el texto.
      //Como tenemos métodos para ello, setSubject() y setText(), no hay ningún problema.
      message.setSubject("Rescate de patitas: Encontramos a su mascota");
      message.setText("<p>Hola!</p> <p>Encontramos tu mascota</p>" +
          "<p>Comunicate con nosotros</p>" +
          "<p>Gracias.</p>"+
          "<p>Saludos Rescate de patitas</p>","UTF-8","html");

      //Para enviar el mensaje usamos la clase Transport, que se obtiene de Session.
      //El método getTransport() requiere un parámetro String con el nombre del protocolo a usar
      //Como el de gmail es smtp, pues ponemos smtp.
      Transport t = session.getTransport("smtp");

      //Ahora debemos establecer la conexión, dando el nombre de usuario y password.
      t.connect("dds2021g10@gmail.com","viernesNoche21");

      //y ahora simplemente enviamos el mensaje
      t.sendMessage(message,message.getAllRecipients());

      t.close();
    } catch (MessagingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
