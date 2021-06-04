package client;

import client.response.HogaresResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import modelo.hogares.Hogar;
import properties.MisProperties;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import properties.MisProperties;

public class ObtenerHogaresClient {

    private final Client client;
    ObjectMapper objectMapper = new ObjectMapper();

    public ObtenerHogaresClient() {
        client = Client.create();
    }

    private HogaresResponse obtenerHogares(String offset) throws JsonProcessingException {

        Properties properties = new Properties();
        MisProperties.cargarInfoPropertiesMain(properties);

        WebResource recurso = this.client.resource(properties.getProperty("HOGARES_API")).path(properties.getProperty("HOGARES"));
        WebResource recursoConParametros = recurso.queryParam("offset",offset);
        WebResource.Builder builder = recursoConParametros.accept(MediaType.APPLICATION_JSON).header("Authorization","Bearer " + properties.getProperty("TOKEN"));
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
