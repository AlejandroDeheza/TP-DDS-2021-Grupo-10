package excepciones;

public class CaracteristicaInvalida extends RuntimeException{
  public CaracteristicaInvalida(String error){
    super(error);
  }


}
