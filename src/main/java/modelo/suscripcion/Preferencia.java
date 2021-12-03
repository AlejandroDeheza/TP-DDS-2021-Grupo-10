package modelo.suscripcion;

import java.util.List;
import modelo.mascota.Animal;
import modelo.mascota.caracteristica.Caracteristica;

import javax.persistence.*;

@Embeddable
public class Preferencia {

  @ManyToMany(cascade = CascadeType.ALL)
  private List<Caracteristica> caracteristicas;

  @Enumerated(EnumType.STRING)
  private Animal tipoAnimal;

  // para hibernate
  private Preferencia() {

  }

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
