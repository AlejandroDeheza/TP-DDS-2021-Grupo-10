package excepciones;

public class MascotaYaRegistradaException extends RuntimeException {
  public MascotaYaRegistradaException(String error) {
    super(error);
  }
}
