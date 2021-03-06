package modelo.persona;

import javax.persistence.Embeddable;

@Embeddable
public class DatosDeContacto {

  private String telefono;
  private String email;

  // para hibernate
  private DatosDeContacto() {

  }

  public DatosDeContacto(String telefono, String email) {
    this.telefono = telefono;
    this.email = email;
  }

  public Boolean noHayDatosDeContacto() {
    return telefono == null && email == null;
  }

  public Boolean noExisteCorreoAsociado() {
    return email == null;
  }


  public String getTelefono() {
    return telefono;
  }

  public String getEmail() {
    return email;
  }

}