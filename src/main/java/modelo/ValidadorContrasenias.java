package modelo;

import java.util.ArrayList;
import java.util.List;

public class ValidadorContrasenias {

  private List<Validador> validadores;

  public ValidadorContrasenias(){
    this.validadores = new ArrayList<>();
    this.validadores.add(new ValidadorContraseniasComunes());
    this.validadores.add(new ValidadorLargoMinimo());
  }

  public void validar(String contrasenia){
    this.validadores.forEach(validador -> validador.validar(contrasenia));
  }
}
