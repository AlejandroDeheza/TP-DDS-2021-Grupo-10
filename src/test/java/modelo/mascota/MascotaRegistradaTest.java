package modelo.mascota;

import modelo.mascota.caracteristica.Caracteristica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MascotaRegistradaTest {
  MascotaRegistrada mascotaRegistrada;
  List<Caracteristica> listaCaracteristica;

  @BeforeEach
  public void contextLoad() {
    mascotaRegistrada = DummyData.getMascotaRegistrada(null);
    listaCaracteristica = new ArrayList<>();
  }

  @Test
  @DisplayName("Una Mascota cumple con caracteristicas de la misma mascota")
  public void cumpleConLasCaracteristicasDeLaMismaMascota() {
    assertTrue(mascotaRegistrada.cumpleConCaracteristicas(DummyData.getCaracteristicasParaMascota()));
  }

  @Test
  @DisplayName("Una Mascota no cumple con caracteristicas")
  public void noCumpleConLasCaracteristicas() {
    listaCaracteristica.add(new Caracteristica("Comportamiento", "Inquieto"));
    assertFalse(mascotaRegistrada.cumpleConCaracteristicas(listaCaracteristica));
  }

  @Test
  @DisplayName("Una Mascota no cumple con las caracteristicas si no matchean todas las caracteristicas")
  public void noCumpleCaracteristicasSiNoMatcheanTodaslasCaracteristicas() {
    listaCaracteristica.add(new Caracteristica("Comportamiento", "Tranquilo"));
    listaCaracteristica.add(new Caracteristica("Comilon", "Si"));
    assertFalse(mascotaRegistrada.cumpleConCaracteristicas(listaCaracteristica));
  }
}
