package modelo;

import excepciones.DatosDeContactoIncompletos;
import java.util.Objects;
import java.time.LocalDate;

public class Persona {
  String nombre;
  String apellido;
  TipoDocumento tipoDocumento;
  String numeroDeDocumento;
  DatosDeContacto datosDeContacto;
  LocalDate fechaNacimiento;


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
      throw new DatosDeContactoIncompletos("Se debe agregar al menos un dato de contacto o el nombre y apellido");
  }
}