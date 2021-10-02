package modelo.hogarDeTransito;

import modelo.hogarDeTransito.respuestas.RespuestaDeHogares;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
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

  private Client client = Client.create();
  private ObjectMapper objectMapper = new ObjectMapper();
  private int limiteDePaginasAtraer = 4;
  // TODO Se tiene que poder treaer hogares hasta la cantidad que indica la property total.
  private String jsonParaTestear;

  // para testear
  public ReceptorHogares(String jsonParaTestear) {
    this.limiteDePaginasAtraer = 1;
    this.jsonParaTestear = jsonParaTestear;
  }
  // para Main
  public ReceptorHogares() {
  }
  // el ReceptorHogares, en codigo de produccion, lo inyectamos por constructor

  public List<Hogar> getHogaresDisponibles(Ubicacion ubicacionRescatista, Integer radioCercania, Animal tipoAnimal,
                                           TamanioMascota tamanioMascota, List<Caracteristica> caracteristicas) {
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
    for (int offset = 1; offset <= limiteDePaginasAtraer; offset++) {
      resultado.addAll(getJsonMapeado(String.valueOf(offset)).getHogares());
    }
    return resultado;
  }

  public RespuestaDeHogares getJsonMapeado(String offset) {
    RespuestaDeHogares respuesta;
    try {
      if (jsonParaTestear != null) {
        respuesta = objectMapper.readValue(jsonParaTestear, RespuestaDeHogares.class);
      } else {
        respuesta = objectMapper.readValue(getJson(offset), RespuestaDeHogares.class);
      }
    } catch (JsonProcessingException e) {
      throw new JsonException(e);
    }
    return respuesta;
  }

  public String getJson(String offset) {
    Properties properties = new ReceptorProperties().getProperties();
    return client
        .resource(properties.getProperty("HOGARES_API"))
        .path(properties.getProperty("HOGARES"))
        .queryParam("offset", offset)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + properties.getProperty("TOKEN"))
        .get(ClientResponse.class)
        .getEntity(String.class);
  }
}
