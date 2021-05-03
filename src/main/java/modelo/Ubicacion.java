package modelo;

public class Ubicacion {
    private String latitud;
    private String longitud;

    public Ubicacion(String latitud, String longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public String getLongitud() {
        return longitud;
    }
}
