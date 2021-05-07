package exceptions;

import excepciones.CaracteristicasVacioException;
import excepciones.DuenioMascotaMascotaRegistradaException;
import modelo.mascota.Mascota;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.usuario.Administrador;
import modelo.usuario.DuenioMascota;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import java.util.Arrays;
import java.util.List;

public class DuenioMascotaRegistradaExceptionTest {

  @Test
  @DisplayName("Un duenio de mascota con una mascota registrada no puede registrar a la misma")
  public void duenioNoPuedeRegistrarMascotaDuplicada() {

    Administrador admin = new Administrador("elAdmin","sarasas123123",DummyData.getDummyPersona());
    List<String> valoresPosible = Arrays.asList("Bueno", "Malo");
    Caracteristica caracteristica = new Caracteristica("Comportamiento",valoresPosible);
    admin.agregarCaracteristica(caracteristica);
    DuenioMascota duenioMascota = DummyData.getDummyDuenioMascota();
    Mascota mascota1=DummyData.getDummyMascota();
    Mascota mascota2 = mascota1;
    duenioMascota.agregarMascota(mascota1);
    Assertions.assertThrows(DuenioMascotaMascotaRegistradaException.class, () -> duenioMascota.agregarMascota(mascota2));

  }

  //TODO agregar test de todas las validaciones.

}
