package excepciones;

public class ArchivoException extends RuntimeException {
  public ArchivoException(String s, Exception e) {
    super(s + " ->> " + e.toString());
  }
}
