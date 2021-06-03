package client;

import client.response.HogaresResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import modelo.hogares.Hogar;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

public class ObtenerHogaresClient {

    private final Client client;
    private static final String HOGARES_API = "https://api.refugiosdds.com.ar/api";
    private static final String HOGARES = "hogares";
    private static final String TOKEN = "hUkcMVqXTgkftoDDG8DrVrZbd44m1YJq6s8qfTmu211BWepPiCB78RRbZlf1";
    ObjectMapper objectMapper = new ObjectMapper();

    public ObtenerHogaresClient() {
        client = Client.create();
    }

    private HogaresResponse obtenerHogares(String offset) throws JsonProcessingException {

        WebResource recurso = this.client.resource(HOGARES_API).path(HOGARES);
        WebResource recursoConParametros = recurso.queryParam("offset",offset);
        WebResource.Builder builder = recursoConParametros.accept(MediaType.APPLICATION_JSON).header("Authorization","Bearer " + TOKEN);
        ClientResponse response = builder.get(ClientResponse.class);
        HogaresResponse hogaresResponse = objectMapper.readValue(response.getEntity(String.class),HogaresResponse.class);
        return hogaresResponse;
    }

    public List<Hogar> obtenerTodosLosHogares() throws JsonProcessingException {
        List<Hogar> resultado = new ArrayList<>();
        for (int offset = 1; offset < 5; offset++) {
            HogaresResponse response = this.obtenerHogares(String.valueOf(offset));
            resultado.addAll(response.getHogares());
        }
        return resultado;
    }
}
