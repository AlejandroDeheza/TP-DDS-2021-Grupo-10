package modelo.hogarDeTransito;

import modelo.informe.Ubicacion;
import modelo.mascota.Animal;
import modelo.mascota.TamanioMascota;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repositorios.RepositorioCaracteristicas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReceptorHogaresTest {

    //Un response con un solo hogar en la lista de hogares.
    String testJson = "{\"total\":40,\"offset\":\"1\",\"hogares\":[{\"id\":\"eyJpdiI6IjV6OHZLa1pxK09KZHRkdEZpclBLUl" +
        "E9PSIsInZhbHVlIjoiY3JwNjZKQW1XcjRjaVBOQ3gxNVRjZz09IiwibWFjIjoiODgwODJhN2Y4YjA5MmNmNGE1MWU4NDY5ZWQ4MGZjMDRk" +
        "YjA0Yzg5MjJmMjQ4ODkzNGUxYzNmMjc1ZDBhMWI0MCJ9\",\"nombre\":\"Pensionado de mascotas \\\"Como en casa\\\"\"," +
        "\"ubicacion\":{\"direccion\":\"Av. Ing Eduardo Madero 2300, B1669BZQ Del Viso, Provincia de Buenos Aires\"," +
        "\"lat\":-34.46013439745161,\"long\":-58.80857841888721},\"telefono\":\"+541164657462\",\"admisiones\":{\"" +
        "perros\":false,\"gatos\":true},\"capacidad\":50,\"lugares_disponibles\":45,\"patio\":true,\"caracteristicas" +
        "\":[\"Tranquilo\"]}]}";
    List<Caracteristica> caracteristicas;

    @BeforeEach
    public void context() {
        RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
        repositorioCaracteristicas.agregarCaracteristica(new CaracteristicaConValoresPosibles(
            "Comportamiento", Arrays.asList("Inquieto", "Tranquilo")));
        caracteristicas = new ArrayList<>();
        caracteristicas.add(new Caracteristica("Comportamiento", "Tranquilo",
            repositorioCaracteristicas));
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
        Mockito.doReturn(testJson).when(spyClient).getJson(any());
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
            new Ubicacion(-34.46, -58.80), 1000, Animal.GATO, TamanioMascota.CHICO,
            caracteristicas);
    }

}
