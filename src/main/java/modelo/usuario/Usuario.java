package modelo.usuario;

import modelo.notificacion.Notificador;
import modelo.persona.Persona;

import javax.persistence.*;

@Entity
public class Usuario {

  @Id
  @GeneratedValue
  private Long id;

  private String usuario;
  private String contrasenia;
  @Transient
  private TipoUsuario tipo;
  @Transient
  private Persona persona;
  @Transient
  private ValidadorAutenticacion validadorAutenticacion;

  public Usuario(String usuario, String contrasenia, TipoUsuario tipo, Persona persona) {
    new ValidadorContrasenias().correrValidaciones(contrasenia);
    this.usuario = usuario;
    this.contrasenia = contrasenia;
    this.tipo = tipo;
    this.persona = persona;
    this.validadorAutenticacion = new ValidadorAutenticacion(this.contrasenia);
  }

  public void autenticarUsuario(String contraseniaIngresada) {
    validadorAutenticacion.autenticarUsuario(contraseniaIngresada);
  }

  public Notificador getNotificadorPreferido() {
    return persona.getNotificadorPreferido();
  }

  public String getUsuario() {
    return usuario;
  }

  public TipoUsuario getTipo() {
    return tipo;
  }

  public Persona getPersona() {
    return persona;
  }
}
