package modelo.publicacion;

import java.util.List;
import modelo.mascota.Animal;
import modelo.mascota.caracteristica.Caracteristica;

public class Preferencia {

  private List<Caracteristica> caracteristicas;
  private Animal tipoAnimal;

  public List<Caracteristica> getCaracteristicas() {
    return this.caracteristicas;
  }

  public Animal getTipoAnimal() {
    return this.tipoAnimal;
  }
}
