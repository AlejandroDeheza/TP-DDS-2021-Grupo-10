package modelo.usuario;

import modelo.persona.Persona;
import servicios.validacionUsuario.impl.ValidadorAutenticacion;
import servicios.validacionUsuario.impl.ValidadorContrasenias;

public class Usuario {

  private String usuario;
  private String contrasenia;
  private TipoUsuario tipo;
  private Persona persona;
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
