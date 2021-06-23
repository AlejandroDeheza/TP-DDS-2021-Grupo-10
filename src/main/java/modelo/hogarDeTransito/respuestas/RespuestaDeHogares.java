package modelo.hogarDeTransito.respuestas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaDeHogares {

  private List<RespuestaDeHogar> hogares;

  public RespuestaDeHogares(@JsonProperty("hogares") List<RespuestaDeHogar> hogares) {
    this.hogares = hogares;
  }

  public List<RespuestaDeHogar> getHogares() {
    return hogares;
  }

}