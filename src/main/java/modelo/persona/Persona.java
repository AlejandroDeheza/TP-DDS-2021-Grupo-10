package modelo.persona;

import excepciones.DatosDeContactoIncompletosException;
import java.util.Objects;
import java.time.LocalDate;

public class Persona {

  private String nombre;
  private String apellido;
  private TipoDocumento tipoDocumento;
  private String numeroDeDocumento;
  private DatosDeContacto datosDeContacto;
  private LocalDate fechaNacimiento;

  public Persona(String nombre, String apellido, TipoDocumento tipoDocumento, String numeroDeDocumento,
                 DatosDeContacto datosDeContacto, LocalDate fechaNacimiento) {
    this.validarQueTengaDatosContacto(nombre, apellido, Objects.requireNonNull(datosDeContacto,
        "Falta referencia a instancia de DatosDeContacto"));
    this.nombre = nombre;
    this.apellido = apellido;
    this.tipoDocumento = tipoDocumento;
    this.numeroDeDocumento = numeroDeDocumento;
    this.datosDeContacto = datosDeContacto;
    this.fechaNacimiento = fechaNacimiento;
  }

  private void validarQueTengaDatosContacto(String nombre, String apellido, DatosDeContacto datosDeContacto){
    Boolean noTieneNingunDatoDeContacto = !datosDeContacto.hayAlgunDatoDeContacto();
    Boolean noTieneNombreYApellido = nombre == null && apellido == null;

    if (noTieneNombreYApellido && noTieneNingunDatoDeContacto)
      throw new DatosDeContactoIncompletosException(
          "Se debe agregar al menos un dato de contacto o el nombre y apellido");
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