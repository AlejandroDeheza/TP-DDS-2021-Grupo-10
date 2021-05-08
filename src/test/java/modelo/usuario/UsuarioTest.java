package modelo.usuario;

import excepciones.AutenticacionInvalidaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

public class UsuarioTest {

  DuenioMascota duenioMascota = DummyData.getDummyDuenioMascota();

  @Test
  @DisplayName("se puede crear un usuario valido")
  public void usuarioValidoTest(){
    Assertions.assertDoesNotThrow(() -> DummyData.getDummyDuenioMascota());
  }

  @Test
  @DisplayName("un usuario dummy se puede autenticar correctamente")
  public void autenticacionCorrectaTest(){
    Assertions.assertDoesNotThrow(() -> duenioMascota.autenticarUsuario("Password1234"));
  }

  @Test
  @DisplayName("si un usuario dummy se autentica mal genera AutenticacionInvalidaException")
  public void autenticacionInvalidaExceptionTest(){
    Assertions.assertThrows(AutenticacionInvalidaException.class,
        () -> duenioMascota.autenticarUsuario("Password12"));
  }

}
