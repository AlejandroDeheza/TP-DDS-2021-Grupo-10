package modelo.suscripcion;

import java.util.List;
import modelo.mascota.Animal;
import modelo.mascota.caracteristica.Caracteristica;

public class Preferencia {

  private List<Caracteristica> caracteristicas;
  private Animal tipoAnimal;

  public Preferencia(List<Caracteristica> caracteristicas, Animal tipoAnimal) {
    this.caracteristicas = caracteristicas;
    this.tipoAnimal = tipoAnimal;
  }

  public List<Caracteristica> getCaracteristicas() {
    return this.caracteristicas;
  }

  public Animal getTipoAnimal() {
    return this.tipoAnimal;
  }
}
