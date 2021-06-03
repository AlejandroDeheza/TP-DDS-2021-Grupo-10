package modelo.informe;

public class Ubicacion {
  private Double latitud;
  private Double longitud;
  private String direccion;

  public Ubicacion(Double latitud, Double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
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
