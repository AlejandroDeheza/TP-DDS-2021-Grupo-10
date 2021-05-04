package modelo;

import java.time.LocalDate;

public class Persona {

  private LocalDate fechaNacimiento;
  private TipoDocumento tipoDocumento;
  private Integer numeroDocumento;
  private DatosDeContacto datosDeContacto;

  public Persona(LocalDate fechaNacimiento, TipoDocumento tipoDocumento, Integer numeroDocumento,
                 DatosDeContacto datosDeContacto){

    this.fechaNacimiento = fechaNacimiento;
    this.tipoDocumento = tipoDocumento;
    this.numeroDocumento = numeroDocumento;
    this.datosDeContacto = datosDeContacto;
  }
}
