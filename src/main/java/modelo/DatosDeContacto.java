package modelo;

public class DatosDeContacto {
  private Integer telefono;
  private String email;

  public DatosDeContacto(Integer telefono, String email) {
    this.telefono = telefono;
    this.email = email;
  }

  public Boolean hayAlgunDatoDeContacto(){
    return (this.telefono !=null || this.email!=null);
  }

}