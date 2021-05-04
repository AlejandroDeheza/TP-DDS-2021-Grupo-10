package excepciones;

public class LoginInvalidoException extends RuntimeException{
	public LoginInvalidoException(String error){
		super(error);
	}
}
