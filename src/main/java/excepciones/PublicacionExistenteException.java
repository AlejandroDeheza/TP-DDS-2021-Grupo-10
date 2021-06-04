package excepciones;

public class PublicacionExistenteException extends RuntimeException {
    public PublicacionExistenteException() {
        super("La publicacion ya existe");
    }
}
