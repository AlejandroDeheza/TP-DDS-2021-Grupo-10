package excepciones;

public class PublicacionInexistenteException extends RuntimeException {
    public PublicacionInexistenteException() {
        super("La publicacion no existe");
    }
}
