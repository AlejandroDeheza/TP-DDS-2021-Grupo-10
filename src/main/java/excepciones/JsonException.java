package excepciones;

public class JsonException extends RuntimeException {
  public JsonException(Exception e) {
    super(e.toString());
  }
}
