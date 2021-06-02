package excepciones;

public class RolSinAutorizacionException extends RuntimeException {
    public RolSinAutorizacionException() {
        super("El usuario no tiene permiso para procesar el informe");
    }
}
