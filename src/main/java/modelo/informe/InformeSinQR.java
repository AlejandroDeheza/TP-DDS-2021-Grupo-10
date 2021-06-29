package modelo.informe;

import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.Animal;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import modelo.publicacion.Rescate;
import repositorios.RepositorioInformes;
import repositorios.RepositorioRescates;
import modelo.notificacion.NotificadorCorreo;

import java.util.List;

public class InformeSinQR extends InformeRescate {

  private Animal tipoAnimal;
  private List<Caracteristica> caracteristicas;
  private RepositorioRescates repositorioRescates;
  private NotificadorCorreo notificadorCorreo;

  public InformeSinQR(Persona rescatista, Ubicacion ubicacionRescatista, String direccionRescatista,
      MascotaEncontrada mascotaEncontrada, RepositorioInformes repositorioInformes, ReceptorHogares receptorHogares,
      Animal tipoAnimal, List<Caracteristica> caracteristicas, RepositorioRescates repositorioRescates,
      NotificadorCorreo notificadorCorreo) {
    super(rescatista, ubicacionRescatista, direccionRescatista, mascotaEncontrada, repositorioInformes,
        receptorHogares);
    this.tipoAnimal = tipoAnimal;
    this.caracteristicas = caracteristicas;
    this.repositorioRescates = repositorioRescates;
    this.notificadorCorreo = notificadorCorreo;
  }

  public List<Hogar> getHogaresCercanos(Integer radioCercania) {
    return super.getHogaresCercanos(radioCercania, tipoAnimal, this.getMascotaEncontrada().getTamanio(),
        caracteristicas);
  }

  @Override
  public void procesarInforme() {
    generarPublicacion();
    super.procesarInforme();
  }

  private void generarPublicacion() {
    repositorioRescates.agregar(new Rescate(super.getRescatista().getDatosDeContacto(), this.notificadorCorreo,
        this.repositorioRescates, super.getMascotaEncontrada()));
  }
}
