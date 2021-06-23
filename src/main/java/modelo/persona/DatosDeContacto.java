package modelo.persona;

public class DatosDeContacto {
  private String telefono;
  private String email;

  public DatosDeContacto(String telefono, String email) {
    this.telefono = telefono;
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public Boolean noHayDatosDeContacto() {
    return telefono == null && email == null;
  }

  public boolean noExisteCorreoAsociado() {
    return this.email == null;
  }

}