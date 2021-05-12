package modelo.usuario;

import excepciones.DuenioMascotaMascotaRegistradaException;
import modelo.mascota.Mascota;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DuenioMascotaTest {

  @Test
  @DisplayName("si un DuenioMascota intenta registrar a la misma mascota 2 veces, se genera " +
      "DuenioMascotaMascotaRegistradaException")
  public void DuenioMascotaMascotaRegistradaExceptionTest() {
    RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
    agregarCaracteristicaDummyAlRepo(repositorioCaracteristicas);

    DuenioMascota duenioMascota = DummyData.getDummyDuenioMascota();
    Mascota mascota = DummyData.getDummyMascota(repositorioCaracteristicas);
    duenioMascota.agregarMascota(mascota);
    assertThrows(DuenioMascotaMascotaRegistradaException.class, () -> duenioMascota.agregarMascota(mascota));
  }

  private void agregarCaracteristicaDummyAlRepo(RepositorioCaracteristicas repo) {
    Administrador admin = DummyData.getDummyAdministrador(repo);
    admin.agregarCaracteristica(DummyData.getDummyCaracteristicaParaAdmin());
  }
}
