package modelo.usuario;

import modelo.EntitidadPersistente;
import modelo.notificacion.Notificador;
import modelo.persona.Persona;

import javax.persistence.*;

@Entity
public class Usuario extends EntitidadPersistente {

  private String usuario;

  private String contrasenia;

  @Enumerated
  private TipoUsuario tipo;

  @OneToOne
  private Persona persona;

  @Transient
  private ValidadorAutenticacion validadorAutenticacion;

  public Usuario(String usuario, String contrasenia, TipoUsuario tipo, Persona persona) {
    new ValidadorContrasenias().correrValidaciones(contrasenia);
    this.usuario = usuario;
    this.contrasenia = contrasenia;
    this.tipo = tipo;
    this.persona = persona;
    this.validadorAutenticacion = new ValidadorAutenticacion();
  }

  public void autenticarUsuario(String contraseniaIngresada) {
    validadorAutenticacion.autenticarUsuario(this, contraseniaIngresada);
  }

  public Notificador getNotificadorPreferido() {
    return persona.getNotificadorPreferido();
  }

  public String getUsuario() {
    return usuario;
  }

  public String getContrasenia() {
    return contrasenia;
  }

  public TipoUsuario getTipo() {
    return tipo;
  }

  public Persona getPersona() {
    return persona;
  }
}
