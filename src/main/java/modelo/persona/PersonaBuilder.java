package modelo.persona;

import java.time.LocalDate;
import java.util.Objects;
import excepciones.DatosDeContactoIncompletosException;

public class PersonaBuilder {
  private String nombre;
  private String apellido;
  private TipoDocumento tipoDocumento;
  private String numeroDeDocumento;
  private DatosDeContacto datosDeContacto;
  private LocalDate fechaNacimiento;

  public static PersonaBuilder crearBuilder() {
    return new PersonaBuilder();
  }

  public Persona build() {
    this.validarQueTengaDatosContacto(this.nombre, this.apellido, Objects
        .requireNonNull(this.datosDeContacto, "Falta referencia a instancia de DatosDeContacto"));
    return new Persona(this.nombre, this.apellido, this.tipoDocumento, this.numeroDeDocumento,
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

  public PersonaBuilder conTipoDocumento(TipoDocumento tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
    return this;
  }

  public PersonaBuilder conNumeroDeDocumento(String numeroDeDocumento) {
    this.numeroDeDocumento = numeroDeDocumento;
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
