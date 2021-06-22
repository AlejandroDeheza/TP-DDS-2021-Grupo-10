package excepciones;

public class RepositorioPropertiesException extends RuntimeException {
  public RepositorioPropertiesException(String msg) {
    super("Hubo un problema con path del property: "+msg);
  }

}
