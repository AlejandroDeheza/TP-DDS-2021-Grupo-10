package modelo.usuario;

import excepciones.ContraseniaInvalidaException;
import utils.Constantes;
import utils.GestorArchivos;
import java.util.List;

public class ValidadorContrasenias {

  public void correrValidaciones(String contrasenia) {
    validarLargoMinimo(contrasenia);
    validarContraseniasComunes(contrasenia);
  }

  private void validarLargoMinimo(String contrasenia) {
    if (contrasenia.length() < 8)
      throw new ContraseniaInvalidaException("El largo de la contrasenia debe tener como mínimo 8 caracteres");
  }

  private void validarContraseniasComunes(String contrasenia) {
    List<String> contraseniasComunes = new GestorArchivos().getArchivoEnLista(
        new Constantes().getMostCommonPasswordsFileName()
    );
    if (contraseniasComunes.contains(contrasenia))
      throw new ContraseniaInvalidaException("Es una de las 10.000 contrasenias mas usadas");
  }

}
