package modelo.usuario;

import excepciones.MascotaYaRegistradaException;
import modelo.mascota.Mascota;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import repositorios.RepositorioCaracteristicas;
import utils.DummyData;

public class UsuarioTest {

  @Test
  @DisplayName("si se crea un usuario valido, no se genera ningun problema")
  public void usuarioValidoTest() {
    assertDoesNotThrow(DummyData::getDummyUsuario);
  }

  @Test
  @DisplayName("si un Usuario intenta registrar a la misma mascota 2 veces, se genera MascotaYaRegistradaException")
  public void DuenioMascotaMascotaRegistradaExceptionTest() {
    RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
    repositorioCaracteristicas.agregarCaracteristica(DummyData.getDummyCaracteristicaParaAdmin());

    Usuario usuario = DummyData.getDummyUsuario();
    Mascota mascota = DummyData.getDummyMascotaRegistrada(new RepositorioCaracteristicas());
    usuario.agregarMascota(mascota);
    assertThrows(MascotaYaRegistradaException.class, () -> usuario.agregarMascota(mascota));
  }

}
