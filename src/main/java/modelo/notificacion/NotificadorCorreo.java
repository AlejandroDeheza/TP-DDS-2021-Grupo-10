package modelo.notificacion;

import excepciones.NotificacionCorreoException;
import modelo.mascota.MascotaRegistrada;
import modelo.publicacion.DarEnAdopcion;
import modelo.usuario.Usuario;
import utils.ReceptorProperties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue(value = "Correo")
public class NotificadorCorreo extends Notificador {

  @Transient
  private Session session = configurarConexionCorreo();

  @Transient
  private String email;

  @Transient
  private Function<Session, Transport> funcion = (sesion -> {
    Transport t = null;
    try {
      t = sesion.getTransport("smtp");
    } catch (MessagingException e) {
      throw new NotificacionCorreoException("Algo salio mal al generar el Transport en NotificacionCorreo", e);
    }
    return t;
  });

  // el notificador, en codigo de produccion, lo inyectamos por constructor

  // solo para tests
  public NotificadorCorreo(String email, Function<Session, Transport> funcion) {
    this.funcion = funcion;
    this.email = email;
  }
  // para Main
  public NotificadorCorreo(String email) {
    this.email = email;
  }

  // para hibernate
  private NotificadorCorreo() {

  }

  @Override
  public void notificarEncontramosTuMascota(MascotaRegistrada mascotaRegistrada) {
    Properties properties = new ReceptorProperties().getProperties();
    notificar(
        properties.getProperty("asunto_InformeMascotaConDuenio"),
        properties.getProperty("cuerpoMensaje_InformeMascotaConDuenio")
    );
  }

  @Override
  public void notificarQuierenAdoptarTuMascota(Usuario adoptante, MascotaRegistrada mascotaEnAdopcion) {
    notificar(
        "Una persona quiere adoptar a tu mascota",
        "Encontramos una persona que quiere adoptar a " + mascotaEnAdopcion.getNombre() + ". Podes " +
            "comunicarte con el adoptante por este mail: " + adoptante.getPersona().getDatosDeContacto().getEmail()
    );
  }

  @Override
  public void notificarDuenioReclamaSuMacota(Usuario duenio) {
    notificar(
        "Han Encontrado una mascota que rescataste!",
        "El dueño encontro una mascota que vos rescataste. Por favor comunicarse al: "
            + duenio.getPersona().getDatosDeContacto().getEmail()
    );
  }

  @Override
  public void notificarLinkDeBajaSuscripcionAdopciones(String linkDeBaja) {
    notificar(
        "Publicación exitosa",
        "Tu intención de querer adoptar a una mascota se publicó exitosamente. " +
            "¡Semanalmente recibirás recomendaciones de adopción que se ajusten a tus preferencias y comodidades! " +
            "Si deseas dar de baja tu publicación, lo podrás hacer clickeando el siguiente link: " + linkDeBaja
    );
  }

  @Override
  public void notificarRecomendacionesDeAdopciones(List<DarEnAdopcion> recomendaciones) {
    String mascotas = recomendaciones.stream()
        .map(recomendacion -> recomendacion.getMascotaEnAdopcion().getNombre())
        .collect(Collectors.joining(","));
    notificar(
        "Recomendaciones de adopcion",
        "Encontramos las siguientes mascotas que creemos que pueden interesarte adoptar: " + mascotas
    );
  }

  private void notificar(String asunto, String cuerpoMensaje) {
    enviarMensaje(
        crearMensaje(asunto, cuerpoMensaje),
        funcion.apply(session)
    );
  }

  private Session configurarConexionCorreo() {
    Properties props = new Properties();
    props.setProperty("mail.smtp.host", "smtp.gmail.com");
    props.setProperty("mail.smtp.starttls.enable", "true");
    props.setProperty("mail.smtp.port", "587");
    props.setProperty("mail.smtp.user", "rescatepatitasdds21@gmail.com");
    props.setProperty("mail.smtp.auth", "true");
    Session session = Session.getDefaultInstance(props);
    session.setDebug(false);
    return session;
  }

  private MimeMessage crearMensaje(String asunto, String cuerpoMensaje) {
    MimeMessage message = new MimeMessage(session);
    try {
      message.setFrom(new InternetAddress(email));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
      message.setSubject(asunto);
      message.setText(getMensajeBody(cuerpoMensaje), "UTF-8", "html");
    } catch (MessagingException e) {
      throw new NotificacionCorreoException("Algo salio mal al crear el mensaje en NotificacionCorreo", e);
    }
    return message;
  }

  private String getMensajeBody(String cuerpoMensaje) {
    return "<p> Hola, </p> </br></br> <p>" + cuerpoMensaje + "</p> </br></br> <p> Saludos, </br> Hogar de Patitas";
  }

  private void enviarMensaje(MimeMessage mensaje, Transport t) {
    try {
      t.connect("rescatepatitasdds21@gmail.com", "viernesNoche21");
      t.sendMessage(mensaje, mensaje.getAllRecipients());
      t.close();
    } catch (MessagingException e) {
      throw new NotificacionCorreoException("Algo salio mal al enviar el mensaje en NotificacionCorreo", e);
    }
  }

}
