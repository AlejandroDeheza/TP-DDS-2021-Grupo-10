package servicios.validacionUsuario.impl;

import excepciones.ArchivoException;
import excepciones.ContraseniaInvalidaException;

import java.io.*;

public class ValidadorContrasenias implements Validador {

  private BufferedReader archivoContrasenias;

  @Override
  public void validarContrasenia(String contrasenia){
    validarLargoMinimo(contrasenia);
    validarContrasiasComunes(contrasenia);

  }

  private void validarLargoMinimo(String password) {
    if (password.length() < 8) {
      throw new ContraseniaInvalidaException(
          "El largo de la contraseña debe tener como mínimo 8 caracteres");
    }
  }


  private void validarContrasiasComunes(String contrasenia) {
    try {
      this.archivoContrasenias = new BufferedReader(new InputStreamReader(
          new FileInputStream("src/main/resources/10k-most-common.txt"), "UTF8"));

      for (int i = 1; i <= 10000; i++) {
        if (this.archivoContrasenias.readLine().contentEquals(contrasenia)) {
          throw new ContraseniaInvalidaException("Es una de las 10.000 contraseñas mas usadas");
        }
      }
    } catch (FileNotFoundException e) {
      throw new ArchivoException(
          "Algo salio mal al usar validar() en clase ValidadorContraseniasComunes", e);
    } catch (IOException e) {
      throw new ArchivoException(
          "Algo salio mal al usar validar() en clase ValidadorContraseniasComunes", e);
    } finally {
      try {
        if (this.archivoContrasenias != null) {
          this.archivoContrasenias.close();
        }
      } catch (Exception e) {
        throw new ArchivoException(
            "Algo salio mal al cerrar archivo en validar() en clase ValidadorContraseniasComunes",
            e);
      }
    }
  }
}
