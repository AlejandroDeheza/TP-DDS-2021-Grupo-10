package utils;

import excepciones.AutenticacionConsecutivaException;
import excepciones.AutenticacionInvalidaException;
import modelo.usuario.Usuario;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class ValidadorAutenticacionNuevo {

  private final LocalTime ultimoIntentoDeSesionFallido;
  private final Integer contadorIntentosDeSesionFallidos;

  public ValidadorAutenticacionNuevo(LocalTime ultimoIntentoDeSesionFallido, Integer contadorIntentosDeSesionFallidos) {
    this.ultimoIntentoDeSesionFallido = ultimoIntentoDeSesionFallido;
    this.contadorIntentosDeSesionFallidos = contadorIntentosDeSesionFallidos;
  }

  public void autenticarUsuario(Usuario usuario, String contraseniaIngresada) {
    validarCantidadDeIntentosFallidos();
    validarContraseniaIngresada(usuario, contraseniaIngresada);
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
