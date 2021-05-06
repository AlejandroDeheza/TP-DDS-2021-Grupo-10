package modelo;

import excepciones.AutenticacionConsecutivaException;
import excepciones.AutenticacionInvalidaException;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;

public class ValidadorAutenticacion {

  private String contraseniaCorrecta;
  private LocalTime ultimoIntentoDeSesionFallido = LocalTime.now();
  private Long contadorIntentosDeSesionFallidos = Long.valueOf(0);

  public ValidadorAutenticacion(String contraseniaCorrecta){
    this.contraseniaCorrecta = contraseniaCorrecta;
  }

  public void autenticarUsuario(String contraseniaIngresada) {

    if (falloLaAutenticacionHacePoco()) {

      Long tiempoEsperaMaximo = Long.valueOf(60);
      if (tiempoQueEstaEsperandoUsuario() > tiempoEsperaMaximo) {
        throw new AutenticacionConsecutivaException(
            "Debe esperar " + tiempoEsperaMaximo + " minutos para intentar iniciar sesion");
      } else {
        throw new AutenticacionConsecutivaException(
            "Debe esperar " + contadorIntentosDeSesionFallidos + " minutos para intentar iniciar sesion");
      }
    }

    if (laContraseniaEsIncorrecta(contraseniaIngresada)) {
      ultimoIntentoDeSesionFallido = LocalTime.now();
      this.contadorIntentosDeSesionFallidos = Long.valueOf(contadorIntentosDeSesionFallidos + 1);
      throw new AutenticacionInvalidaException("La contraseña ingresada es incorrecta");
    }

    // se ejecuta si todo esta ok
    this.contadorIntentosDeSesionFallidos = Long.valueOf(0);
    concederPermisos();
  }

  // el tiempo de espera aumenta si la cantidad de sesiones fallidas aumenta
  private boolean falloLaAutenticacionHacePoco(){
    return MINUTES.between(LocalTime.now(), this.ultimoIntentoDeSesionFallido) < contadorIntentosDeSesionFallidos;
  }

  private Long tiempoQueEstaEsperandoUsuario(){
    return MINUTES.between(LocalTime.now(), ultimoIntentoDeSesionFallido);
  }

  private boolean laContraseniaEsIncorrecta(String contraseniaIngresada){
    return !this.contraseniaCorrecta.equals(contraseniaIngresada);
  }

  private void concederPermisos() {
    // TODO
  }
}
