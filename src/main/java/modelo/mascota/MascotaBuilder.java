package modelo.mascota;

import java.time.LocalDate;
import java.util.List;
import excepciones.MascotaInvalidaException;
import modelo.mascota.caracteristica.Caracteristica;

public class MascotaBuilder {

  private Animal animal;
  private String nombre;
  private String apodo;
  private LocalDate fechaNacimiento;
  private Sexo sexo;
  private String descripcionFisica;
  private List<Caracteristica> caracteristicas;
  private List<Foto> fotos;

  public static MascotaBuilder crearBuilder() {
    return new MascotaBuilder();
  }

  public Mascota build() {
    this.validarCampos();
    return new Mascota(this.animal, this.nombre, this.apodo, this.fechaNacimiento, this.sexo,
        this.descripcionFisica, this.caracteristicas, this.fotos);
  }

  public MascotaBuilder conAnimal(Animal animal) {
    this.animal = animal;
    return this;
  }

  public MascotaBuilder conNombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  public MascotaBuilder conApodo(String apodo) {
    this.apodo = apodo;
    return this;
  }

  public MascotaBuilder conFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
    return this;
  }

  public MascotaBuilder conSexo(Sexo sexo) {
    this.sexo = sexo;
    return this;
  }

  public MascotaBuilder conDescripcionFisica(String descripcionFisica) {
    this.descripcionFisica = descripcionFisica;
    return this;
  }

  public MascotaBuilder conCaracteristicas(List<Caracteristica> caracteristicas) {
    this.caracteristicas = caracteristicas;
    return this;
  }

  public MascotaBuilder conFotos(List<Foto> fotos) {
    this.fotos = fotos;
    return this;
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

  public List<Caracteristica> getCaracteristicas() {
    return caracteristicas;
  }

  public List<Foto> getFotos() {
    return fotos;
  }

  private void validarCampos() { // TODO: Null check smells
    if (this.animal == null || this.nombre == null || this.apodo == null
        || this.fechaNacimiento == null || this.sexo == null || this.descripcionFisica == null
        || this.caracteristicas == null || this.fotos == null) {
      throw new MascotaInvalidaException("Los campos de MascotaBuilder son inválidos");
    }
  }
}
