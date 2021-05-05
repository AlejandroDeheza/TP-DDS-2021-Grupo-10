package modelo;

import excepciones.AutenticacionConsecutivaException;
import excepciones.AutenticacionInvalidaException;

import static java.time.temporal.ChronoUnit.MINUTES;
import java.time.LocalTime;

public abstract class Usuario {

  private String usuario;
  private String contrasenia;
  private Persona persona;
  private LocalTime ultimoIntentoDeSesion = LocalTime.now();
  private Long contadorIntentosDeSesion = Long.valueOf(0);

  public Usuario(String usuario, String contrasenia, Persona persona) {
    this.validarContrasenia(contrasenia);
    this.usuario = usuario;
    this.contrasenia = contrasenia;
    this.persona = persona;
  }

  private void validarContrasenia(String contrasenia) {
    Validador validador = new ValidadorContraseniasComunes();
    validador.validar(contrasenia);
    Validador validador2 = new ValidadorLargoMinimo();
    validador2.validar(contrasenia);
  }

  public void autenticarUsuario(String contrasenia) {

    // capaz esto se debería implementar en el frontend
    // No sabemos si tenemos la suficiente infomracion para poder implementar esto
    if (MINUTES.between(LocalTime.now(), ultimoIntentoDeSesion) < contadorIntentosDeSesion) {

      // Deberiamos tratar esta excepcion para que de alguna manera bloquee un intento de secion en
      // la UI
      // Y ademas muestre el mensaje de la excepcion
      Long tiempoEsperaMaximo = Long.valueOf(60);
      if (MINUTES.between(LocalTime.now(), ultimoIntentoDeSesion) > tiempoEsperaMaximo) {
        throw new AutenticacionConsecutivaException(
            "Debe esperar " + tiempoEsperaMaximo + " minutos para intentar iniciar secion");
      } else {
        throw new AutenticacionConsecutivaException(
            "Debe esperar " + contadorIntentosDeSesion + " minutos para intentar iniciar secion");
      }
    }

    if (!this.contrasenia.equals(contrasenia)) {
      ultimoIntentoDeSesion = LocalTime.now();
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
