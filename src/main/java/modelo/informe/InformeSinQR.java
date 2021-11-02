package modelo.informe;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import repositorios.RepositorioAsociaciones;
import modelo.asociacion.UbicadorAsociaciones;
import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.Animal;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import modelo.publicacion.Rescate;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "informe_sin_QR")
public class InformeSinQR extends InformeRescate implements WithGlobalEntityManager {

  @Enumerated
  private Animal tipoAnimal;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //Correcto
  private List<Caracteristica> caracteristicas;

  @Transient
  private RepositorioAsociaciones repositorioAsociaciones;

  // para hibernate
  private InformeSinQR() {

  }

  public InformeSinQR(Persona rescatista, Ubicacion ubicacionRescatista, MascotaEncontrada mascotaEncontrada,
                      ReceptorHogares receptorHogares, Animal tipoAnimal, List<Caracteristica> caracteristicas,
                      RepositorioAsociaciones repositorioAsociaciones) {
    super(rescatista, ubicacionRescatista, mascotaEncontrada, receptorHogares);
    this.tipoAnimal = tipoAnimal;
    this.caracteristicas = caracteristicas;
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
    entityManager().persist(
        new Rescate(
            this.getRescatista(),
            this.getMascotaEncontrada(),
            ubicador.getAsociacionMasCercana(getMascotaEncontrada().getUbicacion())
        )
    );
  }
}
