package client;

import client.hogares.Hogar;
import client.response.HogaresResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ObtenerHogaresClientTest {

    ObtenerHogaresClient obtenerHogaresClientMock = mock(ObtenerHogaresClient.class);
    ObjectMapper objectMapper = new ObjectMapper();
    //Un response con un solo hogar en la lista de hogares.
    String testJson = "{\"total\":40,\"offset\":\"1\",\"hogares\":[{\"id\":\"eyJpdiI6IjV6OHZLa1pxK09KZHRkdEZpclBLUlE9PSIsInZhbHVlIjoiY3JwNjZKQW1XcjRjaVBOQ3gxNVRjZz09IiwibWFjIjoiODgwODJhN2Y4YjA5MmNmNGE1MWU4NDY5ZWQ4MGZjMDRkYjA0Yzg5MjJmMjQ4ODkzNGUxYzNmMjc1ZDBhMWI0MCJ9\",\"nombre\":\"Pensionado de mascotas \\\"Como en casa\\\"\",\"ubicacion\":{\"direccion\":\"Av. Ing Eduardo Madero 2300, B1669BZQ Del Viso, Provincia de Buenos Aires\",\"lat\":-34.46013439745161,\"long\":-58.80857841888721},\"telefono\":\"+541164657462\",\"admisiones\":{\"perros\":false,\"gatos\":true},\"capacidad\":50,\"lugares_disponibles\":45,\"patio\":true,\"caracteristicas\":[\"Tranquilo\",\"Pacífico\"]}]}";
    HogaresResponse fakeResponse = null;

    @BeforeEach
    public void context() throws JsonProcessingException {
        fakeResponse = objectMapper.readValue(testJson,HogaresResponse.class);
    }

    @Test
    public void obtenerHogaresRetornaUnaListaDeHogaresConUnSoloElemento() throws JsonProcessingException {
        when(obtenerHogaresClientMock.obtenerHogares("1")).thenReturn(fakeResponse);
        HogaresResponse hogarResponse = obtenerHogaresClientMock.obtenerHogares("1");
        assertEquals(1,hogarResponse.getHogares().size());
    }

    @Test
    public void obtenerTodosLosHogaresDevuelveUnaListaCon5Elementos() throws JsonProcessingException {
        ObtenerHogaresClient hogarClient = new ObtenerHogaresClient();
        ObtenerHogaresClient spyClient = Mockito.spy(hogarClient);
        //Se hace 4 veces porque por ahora tenemos harcodeado en el client que se haga un for 4 veces. HAbria que cambiarlo.
        Mockito.doReturn(fakeResponse).when(spyClient).obtenerHogares(Mockito.any());
        Mockito.doReturn(fakeResponse).when(spyClient).obtenerHogares(Mockito.any());
        Mockito.doReturn(fakeResponse).when(spyClient).obtenerHogares(Mockito.any());
        Mockito.doReturn(fakeResponse).when(spyClient).obtenerHogares(Mockito.any());
        List<Hogar> resultado = spyClient.obtenerTodosLosHogares();
        assertEquals(4,resultado.size());
    }

}
