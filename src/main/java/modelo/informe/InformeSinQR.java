package modelo.informe;

import repositorios.RepositorioAsociaciones;
import modelo.asociacion.UbicadorAsociaciones;
import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.Animal;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import modelo.publicacion.Rescate;
import repositorios.RepositorioInformes;
import repositorios.RepositorioRescates;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "informe_sin_QR")
public class InformeSinQR extends InformeRescate {

  @Enumerated
  private Animal tipoAnimal;

  @ManyToMany
  private List<Caracteristica> caracteristicas;

  @Transient
  private RepositorioRescates repositorioRescates;

  @Transient
  private RepositorioAsociaciones repositorioAsociaciones;

  public InformeSinQR(Persona rescatista, Ubicacion ubicacionRescatista, MascotaEncontrada mascotaEncontrada,
                      RepositorioInformes repositorioInformes, ReceptorHogares receptorHogares, Animal tipoAnimal,
                      List<Caracteristica> caracteristicas, RepositorioRescates repositorioRescates,
                      RepositorioAsociaciones repositorioAsociaciones) {
    super(rescatista, ubicacionRescatista, mascotaEncontrada, repositorioInformes, receptorHogares);
    this.tipoAnimal = tipoAnimal;
    this.caracteristicas = caracteristicas;
    this.repositorioRescates = repositorioRescates;
    this.repositorioAsociaciones = repositorioAsociaciones;
  }

  public List<Hogar> getHogaresCercanos(Integer radioCercania) {
    return super.getHogaresCercanos(
        radioCercania,
        tipoAnimal,
        this.getMascotaEncontrada().getTamanio(),
        caracteristicas
    );
  }

  @Override
  public void procesarInforme() {
    generarPublicacion();
    super.procesarInforme();
  }

  private void generarPublicacion() {
    UbicadorAsociaciones ubicador = new UbicadorAsociaciones(repositorioAsociaciones);
    repositorioRescates.agregar(
        new Rescate(
            this.getRescatista(),
            repositorioRescates,
            this.getMascotaEncontrada(),
            ubicador.getAsociacionMasCercana(getMascotaEncontrada().getUbicacion())
        )
    );
  }
}
