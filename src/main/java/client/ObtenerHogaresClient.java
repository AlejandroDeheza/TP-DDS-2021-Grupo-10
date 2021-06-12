package client;

import client.response.HogaresResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import client.hogares.Hogar;
import repositorios.RepositorioProperties;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ObtenerHogaresClient {

    private final Client client;
    ObjectMapper objectMapper = new ObjectMapper();

    public ObtenerHogaresClient() {
        client = Client.create();
    }

    public HogaresResponse obtenerHogares(String offset) throws JsonProcessingException {
        HogaresResponse hogaresResponse = objectMapper.readValue(getClientResponse(offset),HogaresResponse.class);
        return hogaresResponse;
    }

    public String getClientResponse(String offset) {
        Properties properties = RepositorioProperties.getInstance().getProperties();
        WebResource recurso = this.client.resource(properties.getProperty("HOGARES_API")).path(properties.getProperty("HOGARES"));
        WebResource recursoConParametros = recurso.queryParam("offset", offset);
        WebResource.Builder builder = recursoConParametros.accept(MediaType.APPLICATION_JSON).header("Authorization","Bearer " + properties.getProperty("TOKEN"));
        ClientResponse response = builder.get(ClientResponse.class);
        return response.getEntity(String.class);
    }

    public List<Hogar> obtenerTodosLosHogares() throws JsonProcessingException {
        List<Hogar> resultado = new ArrayList<>();
        //TODO Se tiene que poder treaer hogares hasta la cantidad que indica la property total.
        for (int offset = 1; offset < 5; offset++) {
            HogaresResponse response = this.obtenerHogares(String.valueOf(offset));
            resultado.addAll(response.getHogares());
        }
        return resultado;
    }
}
