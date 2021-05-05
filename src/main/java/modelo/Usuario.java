package modelo;

public abstract class Usuario {

  private String usuario;
  private String contrasenia;
  private Persona persona;
  private ValidadorAutenticacion validadorAutenticacion;

  public Usuario(String usuario, String contrasenia, Persona persona) {
    new ValidadorContrasenias().validar(contrasenia);
    this.usuario = usuario;
    this.contrasenia = contrasenia;
    this.persona = persona;
    validadorAutenticacion = new ValidadorAutenticacion(contrasenia);
  }

  public void autenticarUsuario(String contraseniaIngresada){
    validadorAutenticacion.autenticarUsuario(contraseniaIngresada);
  }

}
