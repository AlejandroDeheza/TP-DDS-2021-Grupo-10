package excepciones;

public class CaracteristicasInvalidaException extends RuntimeException {
  public CaracteristicasInvalidaException(String error) {
    super(error);
  }
}
