package repositorios;

import excepciones.CaracteristicasInvalidaException;
import excepciones.ValorCaracteristicaIncompatibleException;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.usuario.Administrador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RepositorioCaracteristicasTest {

  CaracteristicaConValoresPosibles caracteristica;
  RepositorioCaracteristicas repositorioCaracteristicas;

  @BeforeEach
  public void contextLoad() {
    repositorioCaracteristicas = new RepositorioCaracteristicas();
    Administrador admin = DummyData.getDummyAdministrador(repositorioCaracteristicas);
    caracteristica = DummyData.getDummyCaracteristicaParaAdmin();
    admin.agregarCaracteristica(caracteristica);
  }

  @Test
  @DisplayName("si un administrador ingresa una caracterstica nueva, esta se guarda en RepositorioCaracteristicas")
  public void administradorCaracteristicaTest(){
    assertTrue(repositorioCaracteristicas.getCaracteristicas().contains(caracteristica));
  }

  @Test
  @DisplayName("si un usuario ingresa una caracterstica valida, no se genera ningun problema")
  public void caracteristicaValidaTest(){
    assertDoesNotThrow(() -> new Caracteristica(
        "Comportamiento", "Bueno", repositorioCaracteristicas));
  }

  @Test
  @DisplayName("si un usuario ingresa una caracterstica invalida, se genera CaracteristicasInvalidaException")
  public void CaracteristicasInvalidaExceptionTest(){
    assertThrows(CaracteristicasInvalidaException.class,
        () -> new Caracteristica("Dormilon", "SI", repositorioCaracteristicas));
  }

  @Test
  @DisplayName("si un usuario asigna un valor INVALIDO a una caracterstica VALIDA, se genera" +
      "ValorCaracteristicaIncompatibleException")
  public void ValorCaracteristicaIncompatibleExceptionTest(){
    assertThrows(ValorCaracteristicaIncompatibleException.class,
        () -> new Caracteristica("Comportamiento", "Maso", repositorioCaracteristicas));
  }
}
