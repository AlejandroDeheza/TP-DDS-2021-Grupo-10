package servicios.validacionUsuario.impl;

import excepciones.ContraseniaInvalidaException;

import java.util.List;

public class ValidadorContrasenias {

  public void correrValidaciones(String contrasenia) {
    validarLargoMinimo(contrasenia);
    validarContraseniasComunes(contrasenia);
  }

  private void validarLargoMinimo(String contrasenia) {
    if (contrasenia.length() < 8)
      throw new ContraseniaInvalidaException("El largo de la contraseña debe tener como mínimo 8 caracteres");
  }

  private void validarContraseniasComunes(String contrasenia) {
    GestorArchivos gestorArchivos = new GestorArchivos();
    List<String> contraseniasComunes = gestorArchivos.getArchivoEnLista(
        "src/main/resources/10k-most-common.txt");
    if (contraseniasComunes.contains(contrasenia))
      throw new ContraseniaInvalidaException("Es una de las 10.000 contraseñas mas usadas");
  }

}
