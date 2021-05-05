package modelo;

import excepciones.DatosDeContactoInvalidosException;

public class DatosDeContacto {

  private String nombreYApellido;
  private Integer telefono;
  private String email;

  public DatosDeContacto(String nombreYApellido, Integer telefono, String email){

    this.validarDatosDeContacto(nombreYApellido, telefono, email);
    this.nombreYApellido = nombreYApellido;
    this.telefono = telefono;
    this.email = email;
  }

  private void validarDatosDeContacto(String nombreYApellido, Integer telefono, String email){
    if(nombreYApellido == null && telefono == null && email == null){
      throw new DatosDeContactoInvalidosException(
          "Debe ingresar como minimo uno de los siguientes campos: <nombre y apellido>, <telefono> o <email>");
    }
  }

  public Boolean hayAlgunDatoDeContacto(){
    return (this.telefono !=null || this.email!=null);
  }

}
