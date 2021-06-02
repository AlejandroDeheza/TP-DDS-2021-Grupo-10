package client;

import com.sun.jersey.api.client.ClientResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObtenerHogaresClientTest {

    ObtenerHogaresClient hogaresClient = new ObtenerHogaresClient();
    public static final int HTTP_OK = 200;
    public static final int HTTP_BAD_REQUEST = 400;

    @Test
    public void cuandoConsultoHogaresConOffsetValidoDevuelve200() {
        ClientResponse response = hogaresClient.obtenerHogares("1");
        Assertions.assertEquals(response.getStatus(), HTTP_OK);
    }

    @Test
    public void cuandoConsultoHogaresConOffsetMayorA4Devuelve400() {
        ClientResponse response = hogaresClient.obtenerHogares("5");
        Assertions.assertEquals(response.getStatus(), HTTP_BAD_REQUEST);
    }

}
