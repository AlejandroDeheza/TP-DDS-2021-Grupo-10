package modelo.mascota;

import modelo.mascota.caracteristica.Caracteristica;

import java.time.LocalDate;
import java.util.List;

public class MascotaRegistrada extends Mascota{

  private String nombre;
  private String apodo;
  private LocalDate fechaNacimiento;

  public MascotaRegistrada(Animal animal, String nombre, String apodo, LocalDate fechaNacimiento, Sexo sexo, String descripcionFisica, List<Caracteristica> catacteristicas, List<Foto> fotos, String nombre1, String apodo1, LocalDate fechaNacimiento1) {
    super(animal, nombre, apodo, fechaNacimiento, sexo, descripcionFisica, catacteristicas, fotos);
    this.nombre = nombre1;
    this.apodo = apodo1;
    this.fechaNacimiento = fechaNacimiento1;
  }
}
