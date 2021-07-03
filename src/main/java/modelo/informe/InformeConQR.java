package modelo.informe;

import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.MascotaRegistrada;
import modelo.persona.Persona;
import utils.ReceptorProperties;
import repositorios.RepositorioInformes;

import java.util.List;

public class InformeConQR extends InformeRescate {

  private MascotaRegistrada mascotaRegistrada;
  private ReceptorProperties receptorProperties;

  public InformeConQR(Persona rescatista, Ubicacion ubicacionRescatista, String direccionRescatista,
                      MascotaEncontrada mascotaEncontrada, RepositorioInformes repositorioInformes,
                      ReceptorHogares receptorHogares, MascotaRegistrada mascotaRegistrada,
                      ReceptorProperties receptorProperties) {
    super(rescatista, ubicacionRescatista, direccionRescatista, mascotaEncontrada, repositorioInformes,
        receptorHogares);
    this.mascotaRegistrada = mascotaRegistrada;
    this.receptorProperties = receptorProperties;
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
