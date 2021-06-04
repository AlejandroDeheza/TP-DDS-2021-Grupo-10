package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import modelo.hogares.Hogar;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ObtenerHogaresClientTest {

    ObtenerHogaresClient hogaresClient = new ObtenerHogaresClient();
    public static final int HTTP_OK = 200;
    public static final int HTTP_BAD_REQUEST = 400;

    @Test
    public void consultarTodosLosHogaresDevuelveUnaListaDeHogares() throws JsonProcessingException {
        List<Hogar> hogaresPosibles = hogaresClient.obtenerTodosLosHogares();
    }

}
