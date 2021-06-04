package client;

import client.response.HogaresResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


public class ObtenerHogaresClientTest {
    ObtenerHogaresClient obtenerHogaresClientMock = mock(ObtenerHogaresClient.class);
    ObtenerHogaresClient hogaresClient = new ObtenerHogaresClient();
    ObjectMapper objectMapper = new ObjectMapper();
    String testJson = "{\"id\":\"eyJpdiI6ImFrV0NpamlMNXhSYW9VNTQrYzVWb3c9PSIsInZhbHVlIjoiU1JsaXkwcnNYaFpUNTVPNWJDREsvUT09IiwibWFjIjoiMTM5MTljZjI2YjFjZGE0N2YxM2E0YzBkNzM5MTIxNmVlNzQwM2MyMTYwYjc4NmYxYjY0NDU5OTliNTk0Y2RhNyJ9\",\"nombre\":\"Pensionado de mascotas Como en casa\"\",\"ubicacion\":{\"direccion\":\"Av. Ing Eduardo Madero 2300, B1669BZQ Del Viso, Provincia de Buenos Aires\",\"lat\":-34.46013439745161,\"long\":-58.80857841888721},\"telefono\":\"+541164657462\",\"admisiones\":{\"perros\":false,\"gatos\":true},\"capacidad\":50,\"lugares_disponibles\":45,\"patio\":true,\"caracteristicas\":[\"Tranquilo\",\"Pac\u00edfico\"]}";

    @Test
    public void consultarTodosLosHogaresDevuelveUnaListaDeHogares() throws JsonProcessingException {
        //TODO updatear estos tests, crear mas tests para verificar response de la API, mappeo a nuestro DTO, etc.
        when(obtenerHogaresClientMock.getClientResponse("1")).thenReturn(testJson);
//        HogaresResponse hogaresResponseExpected = objectMapper.readValue(testJson,HogaresResponse.class);
        HogaresResponse hogarResponse = hogaresClient.obtenerHogares("1");
    }

}
