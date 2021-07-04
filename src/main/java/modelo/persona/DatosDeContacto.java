package modelo.persona;

import modelo.notificacion.Notificador;

public class DatosDeContacto {
  private String telefono;
  private String email;
  private Notificador notificadorPreferido;

  public DatosDeContacto(String telefono, String email, Notificador notificadorPreferido) {
    this.telefono = telefono;
    this.email = email;
    this.notificadorPreferido = notificadorPreferido;
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

  public Notificador getNotificadorPreferido() {
    return notificadorPreferido;
  }

}