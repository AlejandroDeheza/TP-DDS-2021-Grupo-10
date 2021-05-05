package modelo;

import excepciones.AutenticacionConsecutivaException;
import excepciones.AutenticacionInvalidaException;

import static java.time.temporal.ChronoUnit.MINUTES;
import java.time.LocalTime;

public abstract class Usuario {

  private String usuario;
  private String contrasenia;
  private Persona persona;
  private LocalTime ultimoIntentoDeSesionFallido = LocalTime.now();
  private Long contadorIntentosDeSesion = Long.valueOf(0);

  public Usuario(String usuario, String contrasenia, Persona persona) {
    this.validarContrasenia(contrasenia);
    this.usuario = usuario;
    this.contrasenia = contrasenia;
    this.persona = persona;
  }

  private void validarContrasenia(String contrasenia) {
    Validador validadorContraseniasComunes = new ValidadorContraseniasComunes();
    validadorContraseniasComunes.validar(contrasenia);
    Validador validadorLargoMinimo = new ValidadorLargoMinimo();
    validadorLargoMinimo.validar(contrasenia);
  }

  public void autenticarUsuario(String contrasenia) {

    // capaz esto se debería implementar en el frontend
    // No sabemos si tenemos la suficiente infomracion para poder implementar esto
    if (MINUTES.between(LocalTime.now(), ultimoIntentoDeSesionFallido) < contadorIntentosDeSesion) {

      // Deberiamos tratar esta excepcion para que de alguna manera bloquee un intento de sesión en
      // la UI
      // Y ademas muestre el mensaje de la excepcion
      Long tiempoEsperaMaximo = Long.valueOf(60);
      if (MINUTES.between(LocalTime.now(), ultimoIntentoDeSesionFallido) > tiempoEsperaMaximo) {
        throw new AutenticacionConsecutivaException(
            "Debe esperar " + tiempoEsperaMaximo + " minutos para intentar iniciar sesion");
      } else {
        throw new AutenticacionConsecutivaException(
            "Debe esperar " + contadorIntentosDeSesion + " minutos para intentar iniciar sesion");
      }
    }

    if (!this.contrasenia.equals(contrasenia)) {
      ultimoIntentoDeSesionFallido = LocalTime.now();
      this.contadorIntentosDeSesion = Long.valueOf(contadorIntentosDeSesion + 1);
      throw new AutenticacionInvalidaException("La contraseña ingresada es inválida");
    }

    // se ejecuta si todo esta ok
    this.contadorIntentosDeSesion = Long.valueOf(0);
    concederPermisos();
  }

  private void concederPermisos() {
    // TODO
  }

}
