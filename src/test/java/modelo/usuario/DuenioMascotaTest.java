package modelo.usuario;

import excepciones.DuenioMascotaMascotaRegistradaException;
import modelo.mascota.Mascota;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

public class DuenioMascotaTest {

  @Test
  @DisplayName("si un DuenioMascota intenta registrar a la misma mascota 2 veces, se genera " +
      "DuenioMascotaMascotaRegistradaException")
  public void DuenioMascotaMascotaRegistradaExceptionTest() {
    Administrador admin = DummyData.getDummyAdministrador();
    CaracteristicaConValoresPosibles caracteristica = DummyData.getDummyCaracteristicaParaAdmin();
    admin.agregarCaracteristica(caracteristica);
    DuenioMascota duenioMascota = DummyData.getDummyDuenioMascota();
    Mascota mascota1 = DummyData.getDummyMascota();
    duenioMascota.agregarMascota(mascota1);
    Assertions.assertThrows(DuenioMascotaMascotaRegistradaException.class,
        () -> duenioMascota.agregarMascota(mascota1));
  }
}
