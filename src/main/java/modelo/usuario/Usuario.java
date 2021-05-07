package modelo.usuario;

import modelo.persona.Persona;
import servicios.validacionUsuario.impl.ValidadorAutenticacion;
import servicios.validacionUsuario.impl.ValidadorContrasenias;

public abstract class Usuario {

  private String usuario;
  private String contrasenia;
  private Persona persona;
  private ValidadorAutenticacion validadorAutenticacion;

  public Usuario(String usuario, String contrasenia, Persona persona) {
    new ValidadorContrasenias().validarContrasenia(contrasenia);
    this.usuario = usuario;
    this.contrasenia = contrasenia;
    this.persona = persona;
    validadorAutenticacion = new ValidadorAutenticacion(contrasenia);
  }

  public void autenticarUsuario(String contraseniaIngresada){
    validadorAutenticacion.autenticarUsuario(contraseniaIngresada);
  }

}
