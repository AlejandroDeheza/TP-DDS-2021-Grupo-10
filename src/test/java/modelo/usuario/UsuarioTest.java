package modelo.usuario;

import excepciones.AutenticacionInvalidaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.DummyData;

public class UsuarioTest {

  DuenioMascota duenioMascota = DummyData.getDummyDuenioMascota();

  @Test
  @DisplayName("si se crea un usuario valido, no se genera ningun problema")
  public void usuarioValidoTest(){
    assertDoesNotThrow(DummyData::getDummyDuenioMascota);
  }

  @Test
  @DisplayName("si un usuario dummy se autentica correctamente, no se genera ningun problema")
  public void autenticacionCorrectaTest(){
    assertDoesNotThrow(() -> duenioMascota.autenticarUsuario("Password1234"));
  }

  @Test
  @DisplayName("si un usuario dummy se autentica mal, se genera AutenticacionInvalidaException")
  public void autenticacionInvalidaExceptionTest(){
    assertThrows(AutenticacionInvalidaException.class,
        () -> duenioMascota.autenticarUsuario("Password12"));
  }

}
