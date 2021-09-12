package modelo.informe;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ubicacion {

  private Double latitud;
  private Double longitud;
  private String direccion;

  public Ubicacion(@JsonProperty("lat") Double latitud, @JsonProperty("long") Double longitud,
                   @JsonProperty("direccion") String direccion) {
    this.latitud = latitud;
    this.longitud = longitud;
    this.direccion = direccion;
  }

  public Double getLatitud() {
    return latitud;
  }

  public Double getLongitud() {
    return longitud;
  }

  public String getDireccion() {
    return direccion;
  }

  public Double getDistancia(Ubicacion ubicacion) {
    // https://www.geeksforgeeks.org/program-distance-two-points-earth/
    double lonDireccionRescatista = Math.toRadians(ubicacion.getLongitud());
    double lonHogar = Math.toRadians(this.getLongitud());
    double latDireccionRescatista = Math.toRadians(ubicacion.getLatitud());
    double latHogar = Math.toRadians(this.getLatitud());

    // Haversine formula
    double dlon = lonHogar - lonDireccionRescatista;
    double dlat = latHogar - latDireccionRescatista;
    double a = Math.pow(Math.sin(dlat / 2), 2)
        + Math.cos(latDireccionRescatista) * Math.cos(latHogar) * Math.pow(Math.sin(dlon / 2), 2);

    double c = 2 * Math.asin(Math.sqrt(a));
    double r = 6371; // Radius of earth in kilometers. Use 3956 for miles
    return (c * r);
  }

}
