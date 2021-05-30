package excepciones;

public class InformeMascotaEncontradaInvalidaException extends RuntimeException {
  public InformeMascotaEncontradaInvalidaException(String error) {
    super(error);
  }
}
