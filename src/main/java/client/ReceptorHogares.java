package client;

import client.response.HogaresResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import client.hogares.Hogar;
import excepciones.JsonException;
import excepciones.RepositorioPropertiesException;
import modelo.informe.Ubicacion;
import modelo.mascota.Animal;
import modelo.mascota.caracteristica.Caracteristica;
import repositorios.RepositorioProperties;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ReceptorHogares {

    private final Client client;
    ObjectMapper objectMapper = new ObjectMapper();

    public ReceptorHogares() {
        client = Client.create();
    }

    public List<Hogar> getHogaresDisponibles(Ubicacion ubicacionRescatista, Integer radioCercania, Animal tipoAnimal,
                                             List<Caracteristica> caracteristicas) {
        List<Hogar> hogares = obtenerTodosLosHogares();
        return hogares.stream().filter( hogar -> hogar.esPosibleHogarDeTransito(
            ubicacionRescatista,
            radioCercania,
            tipoAnimal,
            caracteristicas
        )).collect(Collectors.toList());
    }

    public HogaresResponse obtenerHogares(String offset) {
        Properties properties = RepositorioProperties.getInstance().getProperties();
        WebResource recurso = this.client.resource(properties.getProperty("HOGARES_API")).path(properties.getProperty("HOGARES"));
        WebResource recursoConParametros = recurso.queryParam("offset", offset);
        WebResource.Builder builder = recursoConParametros.accept(MediaType.APPLICATION_JSON).header("Authorization","Bearer " + properties.getProperty("TOKEN"));
        ClientResponse response = builder.get(ClientResponse.class);
        String responseJson = response.getEntity(String.class);
        HogaresResponse respuesta;
        try {
            respuesta = objectMapper.readValue(responseJson,HogaresResponse.class);
        }
        catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
        return respuesta;
    }

    public List<Hogar> obtenerTodosLosHogares() {
        List<Hogar> resultado = new ArrayList<>();
        //TODO Se tiene que poder treaer hogares hasta la cantidad que indica la property total.
        for (int offset = 1; offset < 5; offset++) {
            HogaresResponse response = this.obtenerHogares(String.valueOf(offset));
            resultado.addAll(response.getHogares());
        }
        return resultado;
    }
}
