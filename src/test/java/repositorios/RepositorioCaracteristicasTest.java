package repositorios;

import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.*;

public class RepositorioCaracteristicasTest {

  CaracteristicaConValoresPosibles caracteristica;
  RepositorioCaracteristicas repositorioCaracteristicas;

  @BeforeEach
  public void contextLoad() {
    caracteristica = DummyData.getCaracteristicaParaAdmin();
    repositorioCaracteristicas = new RepositorioCaracteristicas();
    repositorioCaracteristicas.agregarCaracteristica(caracteristica);
  }

  @Test
  @DisplayName("si un administrador ingresa una caracterstica nueva, esta se guarda en RepositorioCaracteristicas")
  public void administradorCaracteristicaTest() {
    assertTrue(repositorioCaracteristicas.getCaracteristicas().contains(caracteristica));
  }

  @Test
  @DisplayName("si un usuario ingresa una caracterstica valida, no se genera ningun problema")
  public void caracteristicaValidaTest() {
    assertDoesNotThrow(() -> new Caracteristica("Comportamiento", "Tranquilo"));
  }

}
