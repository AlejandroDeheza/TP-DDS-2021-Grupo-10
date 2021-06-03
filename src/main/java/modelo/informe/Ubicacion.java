package modelo.informe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ubicacion {

  @JsonProperty("lat")
  private Double latitud;

  @JsonProperty("long")
  private Double longitud;

  @JsonProperty("direccion")
  private String direccion;

  public Ubicacion(Double latitud, Double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public Ubicacion() {

  }


  public Double getLatitud() {
    return latitud;
  }

  public Double getLongitud() {
    return longitud;
  }

  public void setLatitud(Double latitud) {
    this.latitud = latitud;
  }

  public void setLongitud(Double longitud) {
    this.longitud = longitud;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }
}
