package modelo;

public abstract class Usuario {

  private String usuario;
  private String contrasenia;
  private Persona persona;
  // private LocalDateTime ultimoIntentoDeSesion = LocalDateTime.now();

  public Usuario(String usuario, String contrasenia, Persona persona) {
    this.validarContrasenia(contrasenia);
    this.usuario = usuario;
    this.contrasenia = contrasenia;
    this.persona = persona;
  }

  private void validarContrasenia(String contrasenia) {
    ValidadorContraseniasComunes validador = new ValidadorContraseniasComunes();
    validador.validar(contrasenia);
  }

  /*
   * public void autenticarUsuario(String contrasenia) { if
   * (LocalDateTime.now().minusMinutes(ultimoIntentoDeSesion.getMinute()).getMinute() <
   * Integer.valueOf(30)) {
   * 
   * } if (contrasenia != this.contrasenia) { ultimoIntentoDeSesion = LocalDateTime.now(); throw new
   * AutenticacionInvalidaException("La contraseña ingresada es inválida"); }
   * 
   * }
   */

}
