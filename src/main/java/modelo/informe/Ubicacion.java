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

}
