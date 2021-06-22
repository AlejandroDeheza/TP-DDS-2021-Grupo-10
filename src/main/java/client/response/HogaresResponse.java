package client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import client.hogares.Hogar;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HogaresResponse {

    @JsonProperty("hogares")
    private List<Hogar> hogares = new ArrayList<>();

    public List<Hogar> getHogares() {
        return hogares;
    }

    public void setHogares(List<Hogar> hogares) {
        this.hogares = hogares;
    }
}