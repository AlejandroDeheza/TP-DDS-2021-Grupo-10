package excepciones;

public class RepositorioPropertiesException extends RuntimeException {
  public RepositorioPropertiesException(String e) {
    super("Hubo un problema con path del property: " + e);
  }
}
