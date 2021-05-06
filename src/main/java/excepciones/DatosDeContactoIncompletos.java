package excepciones;

public class DatosDeContactoIncompletos extends RuntimeException{
  public DatosDeContactoIncompletos(String error){
    super(error);
  }


}
