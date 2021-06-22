package modelo.mascota;

import modelo.mascota.caracteristica.Caracteristica;

import java.time.LocalDate;
import java.util.List;

public class MascotaRegistrada {

  private String nombre;
  private String apodo;
  private LocalDate fechaNacimiento;
  private String descripcionFisica;
  private Sexo sexo;
  private Animal animal;
  private List<Caracteristica> caracteristicas;
  private List<Foto> fotos;
  private TamanioMascota tamanio;

  public MascotaRegistrada(String nombre, String apodo, LocalDate fechaNacimiento, String descripcionFisica, Sexo sexo,
  Animal animal, List<Caracteristica> caracteristicas, List<Foto> fotos, TamanioMascota tamanio) {
    this.nombre = nombre;
    this.apodo = apodo;
    this.fechaNacimiento = fechaNacimiento;
    this.descripcionFisica = descripcionFisica;
    this.sexo = sexo;
    this.animal = animal;
    this.caracteristicas = caracteristicas;
    this.fotos = fotos;
    this.tamanio = tamanio;
  }

  public String getNombre() {
    return nombre;
  }

  public String getApodo() {
    return apodo;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }

  public String getDescripcionFisica() {
    return descripcionFisica;
  }

  public Sexo getSexo() {
    return sexo;
  }

  public Animal getAnimal() {
    return animal;
  }

  public List<Caracteristica> getCaracteristicas() {
    return caracteristicas;
  }

  public List<Foto> getFotos() {
    return fotos;
  }

  public TamanioMascota getTamanio() {
    return tamanio;
  }

}
