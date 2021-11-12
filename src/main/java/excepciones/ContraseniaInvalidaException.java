package excepciones;

public class ContraseniaInvalidaException extends RuntimeException {
  public ContraseniaInvalidaException(String s) {
    super("La contrasenia es invalida porque: " + s);
  }
}
