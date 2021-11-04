package modelo.mascota;

import modelo.EntidadPersistente;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.usuario.Usuario;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class MascotaRegistrada extends EntidadPersistente {

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Usuario duenio;

  private String nombre;

  private String apodo;

  private LocalDate fechaNacimiento;

  private String descripcionFisica;

  @Enumerated
  private Sexo sexo;

  @Enumerated
  private Animal animal;

  @ManyToMany(cascade = CascadeType.ALL)
  private List<Caracteristica> caracteristicas;

  @ElementCollection
  private List<Foto> fotos;

  @Enumerated
  private TamanioMascota tamanio;

  // para hibernate
  private MascotaRegistrada() {

  }

  public MascotaRegistrada(Usuario duenio, String nombre, String apodo, LocalDate fechaNacimiento,
                           String descripcionFisica, Sexo sexo, Animal animal, List<Caracteristica> caracteristicas,
                           List<Foto> fotos, TamanioMascota tamanio) {
    this.duenio = duenio;
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

  public Boolean cumpleConCaracteristicas(List<Caracteristica> caracteristicas) {
    return this.caracteristicas.containsAll(caracteristicas);
  }

  public Usuario getDuenio() {
    return duenio;
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
