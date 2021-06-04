package modelo.persona;

import java.time.LocalDate;

public class Persona {

  private String nombre;
  private String apellido;
  private TipoDocumento tipoDocumento;
  private String numeroDeDocumento;
  private DatosDeContacto datosDeContacto;
  private LocalDate fechaNacimiento;

  public Persona(String nombre, String apellido, TipoDocumento tipoDocumento,
      String numeroDeDocumento, DatosDeContacto datosDeContacto, LocalDate fechaNacimiento) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.tipoDocumento = tipoDocumento;
    this.numeroDeDocumento = numeroDeDocumento;
    this.datosDeContacto = datosDeContacto;
    this.fechaNacimiento = fechaNacimiento;
  }

  public String getNombre() {
    return nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public TipoDocumento getTipoDocumento() {
    return tipoDocumento;
  }

  public String getNumeroDeDocumento() {
    return numeroDeDocumento;
  }

  public DatosDeContacto getDatosDeContacto() {
    return datosDeContacto;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }
}
