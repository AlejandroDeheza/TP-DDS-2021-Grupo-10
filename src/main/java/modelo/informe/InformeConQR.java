package modelo.informe;

import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.MascotaRegistrada;
import modelo.persona.Persona;
import repositorios.RepositorioInformes;

import java.util.List;

public class InformeConQR extends InformeRescate {

  private MascotaRegistrada mascotaRegistrada;

  public InformeConQR(Persona rescatista, Ubicacion ubicacionRescatista, MascotaEncontrada mascotaEncontrada,
                      RepositorioInformes repositorioInformes, ReceptorHogares receptorHogares,
                      MascotaRegistrada mascotaRegistrada) {
    super(rescatista, ubicacionRescatista, mascotaEncontrada, repositorioInformes,
        receptorHogares);
    this.mascotaRegistrada = mascotaRegistrada;
  }

  public List<Hogar> getHogaresCercanos(Integer radioCercania) {
    return super.getHogaresCercanos(
        radioCercania,
        mascotaRegistrada.getAnimal(),
        mascotaRegistrada.getTamanio(),
        mascotaRegistrada.getCaracteristicas()
    );
  }

  @Override
  public void procesarInforme() {
    notificarAlDuenio();
    super.procesarInforme();
  }

  private void notificarAlDuenio() {
    mascotaRegistrada.getDuenio().getNotificadorPreferido().notificarEncontramosTuMascota(mascotaRegistrada);
  }

}
