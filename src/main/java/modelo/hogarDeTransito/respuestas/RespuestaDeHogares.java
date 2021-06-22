package modelo.hogarDeTransito.respuestas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaDeHogares {

    @JsonProperty("hogares")
    private List<RespuestaDeHogar> hogares = new ArrayList<>();

    public List<RespuestaDeHogar> getHogares() {
        return hogares;
    }

}