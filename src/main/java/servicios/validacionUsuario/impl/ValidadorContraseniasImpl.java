package servicios.validacionUsuario.impl;

import excepciones.ArchivoException;
import excepciones.ContraseniaInvalidaException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ValidadorContraseniasImpl implements ValidadorContrasenias {

  private BufferedReader archivoContrasenias;
  private List<String> contraseniasComunes = new ArrayList<>();

  @Override
  public void correrValidaciones(String contrasenia){
    validarLargoMinimo(contrasenia);
    validarContraseniasComunes(contrasenia);
  }

  private void validarLargoMinimo(String password) {
    if (password.length() < 8) {
      throw new ContraseniaInvalidaException("El largo de la contraseña debe tener como mínimo 8 caracteres");
    }
  }

  private void validarContraseniasComunes(String contrasenia){
    this.obtenerContraseniasComunes();
    if (contraseniasComunes.contains(contrasenia))
      throw new ContraseniaInvalidaException("Es una de las 10.000 contraseñas mas usadas");
  }

  private void obtenerContraseniasComunes() {
    try {
      abrirArchivo();
      leerArchivo();

    } catch (FileNotFoundException e) {
      throw new ArchivoException("Algo salio mal en abrirArchivo() en clase ValidadorContraseniasComunes", e);

    } catch (IOException e) {
      throw new ArchivoException("Algo salio mal en leerArchivo() en clase ValidadorContraseniasComunes", e);

    } finally {
      cerrarArchivo();
    }
  }

  private void abrirArchivo() throws FileNotFoundException {
    this.archivoContrasenias = new BufferedReader(new InputStreamReader(
        new FileInputStream("src/main/resources/10k-most-common.txt"), StandardCharsets.UTF_8));
  }

  private void leerArchivo() throws IOException {
    String unaLinea = this.archivoContrasenias.readLine();
    while (unaLinea != null) {
      contraseniasComunes.add(unaLinea);
      unaLinea = this.archivoContrasenias.readLine();
    }
  }

  private void cerrarArchivo() {
    try {
      if (this.archivoContrasenias != null)
        this.archivoContrasenias.close();

    } catch (Exception e) {
      throw new ArchivoException("Algo salio mal en cerrarArchivo() en clase ValidadorContraseniasComunes", e);
    }
  }

}
