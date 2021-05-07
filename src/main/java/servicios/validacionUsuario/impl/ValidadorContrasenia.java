package servicios.validacionUsuario.impl;

import excepciones.ArchivoException;
import excepciones.ContraseniaInvalidaException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ValidadorContrasenia implements ValidadorContrasenias {

  private BufferedReader archivoContrasenias;

  @Override
  public void correrValidaciones(String contrasenia){
    validarLargoMinimo(contrasenia);
    validarContraseniasComunes(contrasenia);
  }

  private void validarLargoMinimo(String password) {
    if (password.length() < 8) {
      throw new ContraseniaInvalidaException(
          "El largo de la contraseña debe tener como mínimo 8 caracteres");
    }
  }


  private void validarContraseniasComunes(String contrasenia) {
    try {
      this.archivoContrasenias = new BufferedReader(new InputStreamReader(
          new FileInputStream("src/main/resources/10k-most-common.txt"), StandardCharsets.UTF_8));

      String unaLinea = this.archivoContrasenias.readLine();
      while (unaLinea != null) {
        if (unaLinea.contentEquals(contrasenia)) {
          throw new ContraseniaInvalidaException("Es una de las 10.000 contraseñas mas usadas");
        }
        unaLinea = this.archivoContrasenias.readLine();
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
