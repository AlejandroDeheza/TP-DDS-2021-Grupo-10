package modelo;

import java.time.LocalDate;
import java.util.List;

public class Mascota {
   Animal animal;
   String nombre;
   String apodo;
   LocalDate fechaNacimiento;
   Sexo sexo;
   String descripcionFisica;
   List<Caracteristica> caracteristicas;
   List<Foto> fotos;

    public Mascota(Animal animal, String nombre, String apodo, LocalDate fechaNacimiento, Sexo sexo, String descripcionFisica, List<Caracteristica> catacteristicas, List<Foto> fotos) {
        this.animal = animal;
        this.nombre = nombre;
        this.apodo = apodo;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.descripcionFisica = descripcionFisica;
        this.caracteristicas = catacteristicas;
        this.fotos=fotos;
    }
    public List<Caracteristica> getCaracteristicas(){
      return caracteristicas;
    }

    public Animal getAnimal() {
      return animal;
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

    public Sexo getSexo() {
      return sexo;
    }

    public String getDescripcionFisica() {
      return descripcionFisica;
    }

    public List<Foto> getFotos() {
      return fotos;
    }

}
