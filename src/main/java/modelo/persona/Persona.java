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


  public Persona(String nombre, String apellido, TipoDocumento tipoDocumento, String numeroDeDocumento, DatosDeContacto datosDeContacto, LocalDate fechaNacimiento) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.tipoDocumento = tipoDocumento;
    this.numeroDeDocumento = numeroDeDocumento;
    validarTengaDatoContacto(nombre,apellido,Objects.requireNonNull(datosDeContacto,"Falta referencia a objeto Datos De Contacto"));
    this.datosDeContacto = datosDeContacto;
    this.fechaNacimiento = fechaNacimiento;
  }

  public void validarTengaDatoContacto(String nombre,String apellido, DatosDeContacto datosDeContacto){
    Boolean tieneDatosDeContacto = datosDeContacto.hayAlgunDatoDeContacto();
    Boolean noTieneNombreYApellido = this.nombre==null && this.apellido ==null;

    if (noTieneNombreYApellido && !tieneDatosDeContacto)
      throw new DatosDeContactoIncompletosException("Se debe agregar al menos un dato de contacto o el nombre y apellido");
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