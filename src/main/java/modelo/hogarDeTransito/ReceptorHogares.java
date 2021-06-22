package modelo.hogarDeTransito;

import modelo.hogarDeTransito.respuestas.RespuestaDeHogares;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import modelo.hogarDeTransito.respuestas.RespuestaDeHogar;
import excepciones.JsonException;
import modelo.informe.Ubicacion;
import modelo.mascota.Animal;
import modelo.mascota.TamanioMascota;
import modelo.mascota.caracteristica.Caracteristica;
import utils.ReceptorProperties;

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

    public List<Hogar> getHogaresDisponibles(Ubicacion ubicacionRescatista, Integer radioCercania,
                                             Animal tipoAnimal, TamanioMascota tamanioMascota,
                                             List<Caracteristica> caracteristicas) {
        List<RespuestaDeHogar> hogares = obtenerTodosLosHogares();
        return hogares
            .stream()
            .filter(hogar -> hogar.estaDisponible(
                ubicacionRescatista,
                radioCercania,
                tipoAnimal,
                tamanioMascota,
                caracteristicas
            ))
            .map(this::mapearHogar)
            .collect(Collectors.toList());
    }

    private Hogar mapearHogar(RespuestaDeHogar hogar) {
        return new Hogar(hogar.getNombre(), hogar.getTelefono(), hogar.getUbicacion());
    }

    public List<RespuestaDeHogar> obtenerTodosLosHogares() {
        List<RespuestaDeHogar> resultado = new ArrayList<>();
        //TODO Se tiene que poder treaer hogares hasta la cantidad que indica la property total.
        for (int offset = 1; offset < 5; offset++) {
            RespuestaDeHogares response = this.getJsonMapeado(String.valueOf(offset));
            resultado.addAll(response.getHogares());
        }
        return resultado;
    }

    public RespuestaDeHogares getJsonMapeado(String offset) {
        Properties properties = new ReceptorProperties().getProperties();
        WebResource recurso = client.resource(properties.getProperty("HOGARES_API")).path(properties
            .getProperty("HOGARES"));
        WebResource recursoConParametros = recurso.queryParam("offset", offset);
        WebResource.Builder builder = recursoConParametros.accept(MediaType.APPLICATION_JSON)
            .header("Authorization","Bearer " + properties.getProperty("TOKEN"));
        ClientResponse response = builder.get(ClientResponse.class);
        String responseJson = response.getEntity(String.class);
        RespuestaDeHogares respuesta;
        try {
            respuesta = objectMapper.readValue(responseJson, RespuestaDeHogares.class);
        }
        catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
        return respuesta;
    }
}
