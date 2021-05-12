package modelo.usuario;

import modelo.persona.Persona;
import servicios.validacionUsuario.impl.ValidadorAutenticacion;
import servicios.validacionUsuario.impl.ValidadorContraseniasImpl;

public abstract class Usuario {

  private String usuario;
  private String contrasenia;
  private Persona persona;
  private ValidadorAutenticacion validadorAutenticacion;

  public Usuario(String usuario, String contrasenia, Persona persona) {
    new ValidadorContraseniasImpl().correrValidaciones(contrasenia);
    this.usuario = usuario;
    this.contrasenia = contrasenia;
    this.persona = persona;
    this.validadorAutenticacion = new ValidadorAutenticacion(this.contrasenia);
  }

  public void autenticarUsuario(String contraseniaIngresada){
    validadorAutenticacion.autenticarUsuario(contraseniaIngresada);
  }

  public String getUsuario() {
    return usuario;
  }

  public Persona getPersona() {
    return persona;
  }

}
