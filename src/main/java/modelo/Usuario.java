package modelo;

public abstract class Usuario {

  private String usuario;
  private String contrasenia;
  private Persona persona;

  public Usuario(String usuario, String contrasenia, Persona persona){

    //this.validarContrasenia(contrasenia);
    this.usuario = usuario;
    this.contrasenia = contrasenia;
    this.persona = persona;
  }

  /*
  private void validarContrasenia(String contrasenia){
    ValidadorContraseniasComunes validador = new ValidadorContraseniasComunes();
    validador.validar(contrasenia);
  }
  */

}
