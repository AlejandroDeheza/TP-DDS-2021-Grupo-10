package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import excepciones.ArchivoException;
import excepciones.ContraseniaInvalidaException;

public class ValidadorContraseniasComunes implements Validador {

  private BufferedReader archivoContrasenias;

  @Override
  public void validar(String contrasenia) {
    try {
      this.archivoContrasenias = new BufferedReader(new FileReader("archivos/10k-most-common.txt"));
      for (int i = 1; i <= 10000; i++) {
        if (this.archivoContrasenias.readLine().contentEquals(contrasenia)) {
          throw new ContraseniaInvalidaException("Es una de las 10.000 contraseñas mas usadas");
        }
      }
    } catch (Exception e) {
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
