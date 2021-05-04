package modelo;

import excepciones.ContraseniaInvalidaException;

public class ValidadorLargoMinimo implements Validador {

  @Override
  public void validar(String password) {
    if (password.length() < 8) {
      throw new ContraseniaInvalidaException(
          "El largo de la contraseña debe estar formada por un mínimo de 8 caracteres");
    }
  }
}
