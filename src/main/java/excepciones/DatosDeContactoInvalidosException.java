package excepciones;

public class DatosDeContactoInvalidosException extends RuntimeException {
  public DatosDeContactoInvalidosException(String s) {
    super("Los datos no se pueden ingresar porque: " + s);
  }
}
