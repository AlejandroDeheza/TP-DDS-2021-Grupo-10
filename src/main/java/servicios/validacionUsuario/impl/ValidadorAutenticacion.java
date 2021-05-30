package servicios.validacionUsuario.impl;

import excepciones.AutenticacionConsecutivaException;
import excepciones.AutenticacionInvalidaException;

import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class ValidadorAutenticacion {

  private String contraseniaCorrecta;
  private LocalTime ultimoIntentoDeSesionFallido = LocalTime.now();
  private Long contadorIntentosDeSesionFallidos = 0L;

  public ValidadorAutenticacion(String contraseniaCorrecta) {
    this.contraseniaCorrecta = contraseniaCorrecta;
  }

  public void autenticarUsuario(String contraseniaIngresada) {
    validarCantidadDeIntentosFallidosDeAutenticacion();
    validarQueContraseniaIngresadaSeaCorrecta(contraseniaIngresada);
    this.contadorIntentosDeSesionFallidos = 0L;
    concederPermisos();
  }

  private void validarCantidadDeIntentosFallidosDeAutenticacion() {
    if (falloLaAutenticacionHacePoco()) {
      long tiempoEsperaMaximo = 60L;
      if (tiempoQueEstaEsperandoUsuario() > tiempoEsperaMaximo) {
        throw new AutenticacionConsecutivaException(
            "Debe esperar " + tiempoEsperaMaximo + " minutos para intentar iniciar sesion");
      } else {
        throw new AutenticacionConsecutivaException(
            "Debe esperar " + contadorIntentosDeSesionFallidos + " minutos para intentar iniciar sesion");
      }
    }
  }

  private void validarQueContraseniaIngresadaSeaCorrecta(String contraseniaIngresada) {
    if (laContraseniaEsIncorrecta(contraseniaIngresada)) {
      ultimoIntentoDeSesionFallido = LocalTime.now();
      this.contadorIntentosDeSesionFallidos = contadorIntentosDeSesionFallidos + 1;
      throw new AutenticacionInvalidaException("La contraseña ingresada es incorrecta");
    }
  }

  // el tiempo de espera aumenta si la cantidad de sesiones fallidas aumenta
  private boolean falloLaAutenticacionHacePoco() {
    return MINUTES.between(LocalTime.now(), this.ultimoIntentoDeSesionFallido) < contadorIntentosDeSesionFallidos;
  }

  private Long tiempoQueEstaEsperandoUsuario() {
    return MINUTES.between(LocalTime.now(), ultimoIntentoDeSesionFallido);
  }

  private boolean laContraseniaEsIncorrecta(String contraseniaIngresada) {
    return !this.contraseniaCorrecta.equals(contraseniaIngresada);
  }

  private void concederPermisos() {
    // TODO
  }
}
