package excepciones;

public class MascotaInvalidaException extends RuntimeException {
  public MascotaInvalidaException(String error) {
    super(error);
  }
}
