package excepciones;

public class InformeMascotaConDuenioInvalidaException extends RuntimeException {
  public InformeMascotaConDuenioInvalidaException(String error) {
    super(error);
  }
}
