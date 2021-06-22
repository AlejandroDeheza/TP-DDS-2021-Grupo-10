package modelo.mascota;

import modelo.informe.Ubicacion;

import java.time.LocalDate;

public class Contexto {

  private LocalDate fechaEncuentro;
  private Ubicacion ubicacion;

  public Contexto(LocalDate fechaEncuentro, Ubicacion ubicacion) {
    this.fechaEncuentro = fechaEncuentro;
    this.ubicacion = ubicacion;
  }

  public LocalDate getFechaEncuentro() {
    return fechaEncuentro;
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }
}
