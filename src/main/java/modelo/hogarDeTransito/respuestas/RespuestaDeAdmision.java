package modelo.hogarDeTransito.respuestas;

import com.fasterxml.jackson.annotation.JsonProperty;
import modelo.mascota.Animal;

public class RespuestaDeAdmision {

  private Boolean aceptaPerro;
  private Boolean aceptaGato;

  public RespuestaDeAdmision(@JsonProperty("perros") Boolean aceptaPerro, @JsonProperty("gatos") Boolean aceptaGato) {
    this.aceptaPerro = aceptaPerro;
    this.aceptaGato = aceptaGato;
  }

  public Boolean aceptaAnimal(Animal animal) {
    return (aceptaGato && animal == Animal.GATO) || (aceptaPerro && animal == Animal.PERRO);
  }
}
