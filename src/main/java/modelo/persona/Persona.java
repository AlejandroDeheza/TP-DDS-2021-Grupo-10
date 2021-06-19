package modelo.persona;

import java.time.LocalDate;

public class Persona {

  private String nombre;
  private String apellido;
  private DocumentoIdentidad documentoIdentidad;
  private DatosDeContacto datosDeContacto;
  private LocalDate fechaNacimiento;

  public Persona(String nombre, String apellido, DocumentoIdentidad documentoIdentidad,
       DatosDeContacto datosDeContacto, LocalDate fechaNacimiento) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.documentoIdentidad = documentoIdentidad;
    this.datosDeContacto = datosDeContacto;
    this.fechaNacimiento = fechaNacimiento;
  }

  public String getNombre() {
    return nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public DocumentoIdentidad getDocumentoIdentidad() {
    return documentoIdentidad;
  }

  public DatosDeContacto getDatosDeContacto() {
    return datosDeContacto;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }
}
