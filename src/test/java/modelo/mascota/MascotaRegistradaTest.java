package modelo.mascota;

import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import utils.DummyData;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MascotaRegistradaTest {
  MascotaRegistrada mascotaRegistrada;

  @BeforeEach
  public void contextLoad() {
    mascotaRegistrada = DummyData.getMascotaRegistrada(new RepositorioCaracteristicas());
  }

  @Test
  @DisplayName("Una Mascota cumple con caracteristicas de la misma Caracterisca")
  public void cumpleConLasCaracteristicasDeLaMismaMascota() {
    assertTrue(mascotaRegistrada.cumpleConCaracteristicas(mascotaRegistrada.getCaracteristicas()));
  }

  @Test
  @DisplayName("Una Mascota no cumple con caracteristicas")
  public void noCumpleConLasCaracteristicas() {
    RepositorioCaracteristicas repo = new RepositorioCaracteristicas();
    repo.agregarCaracteristica(DummyData.getCaracteristicaParaAdmin());
    List<Caracteristica> listaCaracteristica = new ArrayList<>();
    listaCaracteristica.add(new Caracteristica("Comportamiento", "Inquieto", repo));
    assertFalse(mascotaRegistrada.cumpleConCaracteristicas(listaCaracteristica));
  }

  @Test
  @DisplayName("Una Mascota no cumple con las caracteristicas si no matchean todas las caracteristicas")
  public void nocumpleCaracteristicasSiNoMatcheanTodaslasCaracteristicas() {
    RepositorioCaracteristicas repo = new RepositorioCaracteristicas();
    CaracteristicaConValoresPosibles caracteristicaConValoresPosibles1 = new CaracteristicaConValoresPosibles("Comportamiento", Arrays.asList("Inquieto", "Tranquilo"));
    CaracteristicaConValoresPosibles caracteristicaConValoresPosibles2 = new CaracteristicaConValoresPosibles("Comilon", Arrays.asList("Si", "No"));
    repo.agregarCaracteristica(caracteristicaConValoresPosibles1);
    repo.agregarCaracteristica(caracteristicaConValoresPosibles2);
    List<Caracteristica> listaCaracteristica = new ArrayList<>();
    listaCaracteristica.add(new Caracteristica("Comilon", "Si", repo));
    assertFalse(mascotaRegistrada.cumpleConCaracteristicas(listaCaracteristica));
  }
}
