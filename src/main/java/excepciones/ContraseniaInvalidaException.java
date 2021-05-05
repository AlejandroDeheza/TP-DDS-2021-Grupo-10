package excepciones;

public class ContraseniaInvalidaException extends RuntimeException {
  public ContraseniaInvalidaException(String s) {
    super("La contraseña es invalida porque: " + s);
  }
}
