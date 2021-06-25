package modelo.hogarDeTransito;

import modelo.informe.Ubicacion;

public class Hogar {

  private String nombre;
  private String telefono;
  private Ubicacion ubicacion;

  public Hogar(String nombre, String telefono, Ubicacion ubicacion) {
    this.nombre = nombre;
    this.telefono = telefono;
    this.ubicacion = ubicacion;
  }

  public String getNombre() {
    return nombre;
  }

  public String getTelefono() {
    return telefono;
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }

}
