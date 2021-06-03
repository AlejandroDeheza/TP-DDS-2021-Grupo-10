package client.response;

import modelo.hogares.Hogar;

import java.util.ArrayList;
import java.util.List;

public class HogaresResponse {
    private List<Hogar> hogares = new ArrayList<>();

    public List<Hogar> getHogares() {
        return hogares;
    }

    public void setHogares(List<Hogar> hogares) {
        this.hogares = hogares;
    }
}