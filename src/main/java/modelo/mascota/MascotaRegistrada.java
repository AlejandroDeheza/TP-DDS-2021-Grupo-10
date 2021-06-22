package modelo.mascota;

import modelo.mascota.caracteristica.Caracteristica;

import java.time.LocalDate;
import java.util.List;

public class MascotaRegistrada extends Mascota{

  private String nombre;
  private String apodo;
  private LocalDate fechaNacimiento;

  public MascotaRegistrada(Animal animal, Sexo sexo, String descripcionFisica, List<Caracteristica> caracteristicas, List<Foto> fotos, String nombre, String apodo, LocalDate fechaNacimiento) {
    super(animal, sexo, descripcionFisica, caracteristicas, fotos);
    this.nombre = nombre;
    this.apodo = apodo;
    this.fechaNacimiento = fechaNacimiento;
  }
}
