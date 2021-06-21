package modelo.usuario;

import excepciones.AutenticacionInvalidaException;
import excepciones.MascotaYaRegistradaException;
import modelo.mascota.Mascota;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import repositorios.RepositorioCaracteristicas;
import utils.DummyData;

public class UsuarioTest {

  @Test
  @DisplayName("Si se crea un usuario valido, no se genera ningun problema")
  public void usuarioValidoTest() {
    assertDoesNotThrow(DummyData::getDummyUsuario);
  }

  @Test
  @DisplayName("Si un Usuario intenta registrar una mascota que no existe no da error")
  public void usuarioAgregaMascota() {
    Usuario usuario =  DummyData.getDummyUsuario();
    Mascota mascota = DummyData.getDummyMascota();
    assertDoesNotThrow(()-> usuario.agregarMascota(mascota));
  }

  @Test
  @DisplayName("Si un Usuario elimina un mascota no da error")
  public void usuarioEliminarMascotaAgregadaNoDaError() {
    Usuario usuario =  DummyData.getDummyUsuario();
    Mascota mascota = DummyData.getDummyMascota();
    usuario.agregarMascota(mascota);
    assertDoesNotThrow(()-> usuario.eliminarMascota(mascota));
  }
  //TODO: creo que faltaria un getter a listaMAscotas

  @Test
  @DisplayName("Si un Usuario intenta registrar a la misma mascota 2 veces, se genera MascotaYaRegistradaException")
  public void duenioMascotaMascotaRegistradaExceptionTest() {
    RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
    repositorioCaracteristicas.agregarCaracteristica(DummyData.getDummyCaracteristicaParaAdmin());

    Usuario usuario = DummyData.getDummyUsuario();
    Mascota mascota = DummyData.getDummyMascota();
    usuario.agregarMascota(mascota);
    assertThrows(MascotaYaRegistradaException.class, () -> usuario.agregarMascota(mascota));
  }

  @Test
  @DisplayName("Obtener login de usuario")
  public void obtenerUsuario() {
    String login = "DuenioMascota";
    Usuario usuario =  new Usuario(login, "P3p3.2019", TipoUsuario.NORMAL, DummyData.getDummyPersona());
    assertEquals(login,usuario.getUsuario());
  }

  @Test
  @DisplayName("Obtener Tipo de usuario")
  public void obtenerTipoUsuario() {
    TipoUsuario tipoUsuario = TipoUsuario.NORMAL;
    Usuario usuario =  new Usuario("DuenioMascota", "P3p3.2019", tipoUsuario, DummyData.getDummyPersona());
    assertEquals(tipoUsuario,usuario.getTipo());
  }

  @Test
  @DisplayName("Cuando un usuario intenta autenticarse con la contraseña valida no da error")
  public void autenticarUsuarioConContraseniaCorrectaNoDaError() {
    String contrasenia = "M1m45C0t4";
    Usuario usuario =  new Usuario("DuenioMascota", contrasenia, TipoUsuario.NORMAL, DummyData.getDummyPersona());
    assertDoesNotThrow(()->usuario.autenticarUsuario(contrasenia));
  }

  @Test
  @DisplayName("Cuando un usuario intenta autenticarse con la contraseña incorrecta da error")
  public void autenticarUsuarioConContraseniaIncorrectaDaError() {
    String contrasenia = "M1m45C0t4";
    Usuario usuario =  DummyData.getDummyUsuario();
    assertThrows(AutenticacionInvalidaException.class,()->usuario.autenticarUsuario(contrasenia));
  }
  //TODO: Tendriamos que validar cantidad de intentos? Creo que ese test va mas del lado de ValidadorAutenticacionTest


}
