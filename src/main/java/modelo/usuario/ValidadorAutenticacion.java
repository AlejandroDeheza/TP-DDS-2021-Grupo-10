package modelo.usuario;

import excepciones.AutenticacionConsecutivaException;
import excepciones.AutenticacionInvalidaException;

import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class ValidadorAutenticacion {

  private LocalTime ultimoIntentoDeSesionFallido = LocalTime.now();
  private Integer contadorIntentosDeSesionFallidos = 0;

  public void autenticarUsuario(Usuario usuario, String contraseniaIngresada) {
    validarCantidadDeIntentosFallidos();
    validarContraseniaIngresada(usuario, contraseniaIngresada);
    this.contadorIntentosDeSesionFallidos = 0;
  }

  private void validarCantidadDeIntentosFallidos() {
    if (falloLaAutenticacionHacePoco()) {
      int tiempoEsperaMaximo = 60;
      if (tiempoQueEstaEsperandoUsuario() > tiempoEsperaMaximo) {
        throw new AutenticacionConsecutivaException(
            "Debe esperar " + tiempoEsperaMaximo + " minutos para intentar iniciar sesion");
      } else {
        throw new AutenticacionConsecutivaException(
            "Debe esperar " + contadorIntentosDeSesionFallidos + " minutos para intentar iniciar sesion");
      }
    }
  }

  private void validarContraseniaIngresada(Usuario usuario, String contraseniaIngresada) {
    if (laContraseniaEsIncorrecta(usuario, contraseniaIngresada)) {
      ultimoIntentoDeSesionFallido = LocalTime.now();
      this.contadorIntentosDeSesionFallidos = contadorIntentosDeSesionFallidos + 1;
      throw new AutenticacionInvalidaException("La contraseña ingresada es incorrecta");
    }
  }

  // el tiempo de espera aumenta si la cantidad de sesiones fallidas aumenta
  private boolean falloLaAutenticacionHacePoco() {
    return ((int) MINUTES.between(LocalTime.now(), this.ultimoIntentoDeSesionFallido)) < contadorIntentosDeSesionFallidos;
  }

  private Long tiempoQueEstaEsperandoUsuario() {
    return MINUTES.between(LocalTime.now(), ultimoIntentoDeSesionFallido);
  }

  private boolean laContraseniaEsIncorrecta(Usuario usuario, String contraseniaIngresada) {
    return !usuario.getContrasenia().equals(contraseniaIngresada);
  }

}
