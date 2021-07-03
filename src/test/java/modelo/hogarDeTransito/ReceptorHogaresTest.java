package modelo.hogarDeTransito;

import modelo.informe.Ubicacion;
import modelo.mascota.Animal;
import modelo.mascota.TamanioMascota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.DummyData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReceptorHogaresTest {

  String testJson = DummyData.getJsonHogaresApi();

  @BeforeEach
  public void context() {
  }

  @Test
  @DisplayName("Los Jsons se mapean correctamente con constructor para tests")
  public void losJsonSeMapeanCorrectamente() {
    List<Hogar> hogaresDisponibles = obtenerHogaresDisponibles(new ReceptorHogares(testJson));

    assertEquals(1, hogaresDisponibles.size());
    assertEquals("Pensionado de mascotas \"Como en casa\"", hogaresDisponibles.get(0).getNombre());
  }

  @Test
  @DisplayName("Los Jsons se mapean correctamente con spy")
  public void getHogaresDisponiblesDevuelveUnaListaCon4Elementos() {
    ReceptorHogares spyClient = Mockito.spy(ReceptorHogares.class);
    doReturn(testJson).when(spyClient).getJson(any());
    List<Hogar> hogaresDisponibles = obtenerHogaresDisponibles(spyClient);

    verify(spyClient, times(4)).getJson(any());
    verify(spyClient, times(4)).getJsonMapeado(any());
    verify(spyClient, times(1)).obtenerTodosLosHogares();

    // TODO tenemos harcodeado que se haga un for 4 veces. Habria que cambiarlo.
    assertEquals(4, hogaresDisponibles.size());
    assertEquals("Pensionado de mascotas \"Como en casa\"", hogaresDisponibles.get(0).getNombre());
    assertEquals("Pensionado de mascotas \"Como en casa\"", hogaresDisponibles.get(3).getNombre());
  }

  public List<Hogar> obtenerHogaresDisponibles(ReceptorHogares receptorHogares) {
    return receptorHogares.getHogaresDisponibles(
        new Ubicacion(-34.46, -58.80, null), 1000, Animal.GATO, TamanioMascota.CHICO,
        DummyData.getCaracteristicasParaMascota()
    );
  }

}
