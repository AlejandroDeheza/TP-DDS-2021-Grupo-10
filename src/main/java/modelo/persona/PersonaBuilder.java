package modelo.persona;

import java.time.LocalDate;
import java.util.Objects;
import excepciones.DatosDeContactoIncompletosException;

public class PersonaBuilder {
  private String nombre;
  private String apellido;
  private DocumentoIdentidad documentoIdentidad;
  private DatosDeContacto datosDeContacto;
  private LocalDate fechaNacimiento;

  public static PersonaBuilder crearBuilder() {
    return new PersonaBuilder();
  }

  public Persona build() {
    this.validarQueTengaDatosContacto(this.nombre, this.apellido, Objects
        .requireNonNull(this.datosDeContacto, "Falta referencia a instancia de DatosDeContacto"));
    return new Persona(this.nombre, this.apellido, this.documentoIdentidad,
        this.datosDeContacto, this.fechaNacimiento);
  }

  public PersonaBuilder conNombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  public PersonaBuilder conApellido(String apellido) {
    this.apellido = apellido;
    return this;
  }

  public PersonaBuilder conDocumentoIdentidad(DocumentoIdentidad documentoIdentidad) {
    this.documentoIdentidad = documentoIdentidad;
    return this;
  }

  public PersonaBuilder conDatosDeContacto(DatosDeContacto datosDeContacto) {
    this.datosDeContacto = datosDeContacto;
    return this;
  }

  public PersonaBuilder conFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
    return this;
  }

  private void validarQueTengaDatosContacto(String nombre, String apellido,
      DatosDeContacto datosDeContacto) {
    Boolean noTieneNingunDatoDeContacto = !datosDeContacto.hayAlgunDatoDeContacto();
    Boolean noTieneNombreYApellido = nombre == null && apellido == null;

    if (noTieneNombreYApellido && noTieneNingunDatoDeContacto) {
      throw new DatosDeContactoIncompletosException(
          "Se debe agregar al menos un dato de contacto o el nombre y apellido");
    } else if (!datosDeContacto.existeCorreoAsociado()) {
      throw new DatosDeContactoIncompletosException(
          "El dato de contacto debe tener un correo asociado");
    }
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
