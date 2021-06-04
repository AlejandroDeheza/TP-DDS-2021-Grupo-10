package excepciones;

public class NotificacionCorreoException extends RuntimeException {
  public NotificacionCorreoException(String s, Exception e) {
    super(s + " ->> " + e.toString());
  }
}
