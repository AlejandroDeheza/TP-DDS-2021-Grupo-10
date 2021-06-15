package modelo.mascota;

import modelo.mascota.caracteristica.Caracteristica;

import java.time.LocalDate;
import java.util.List;

public abstract class Mascota {

  private Animal animal;
  private Sexo sexo;
  private String descripcionFisica;
  private List<Caracteristica> caracteristicas;
  private List<Foto> fotos;

  public Mascota(Animal animal, Sexo sexo, String descripcionFisica, List<Caracteristica> caracteristicas, List<Foto> fotos) {
    this.animal = animal;
    this.sexo = sexo;
    this.descripcionFisica = descripcionFisica;
    this.caracteristicas = caracteristicas;
    this.fotos = fotos;
  }

  public Animal getAnimal() {
    return animal;
  }

  public Sexo getSexo() {
    return sexo;
  }

  public String getDescripcionFisica() {
    return descripcionFisica;
  }

  public List<Foto> getFotos() {
    return fotos;
  }

  public List<Caracteristica> getCaracteristicas() {
    return caracteristicas;
  }

}
