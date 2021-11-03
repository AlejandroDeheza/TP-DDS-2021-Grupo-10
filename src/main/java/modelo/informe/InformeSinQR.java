package modelo.informe;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.Animal;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import modelo.publicacion.Rescate;
import repositorios.RepositorioAsociaciones;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "informe_sin_QR")
public class InformeSinQR extends InformeRescate implements WithGlobalEntityManager {

  @Enumerated
  private Animal tipoAnimal;

  @ManyToMany(cascade = CascadeType.ALL)
  private List<Caracteristica> caracteristicas;

  // para hibernate
  public InformeSinQR() {

  }

  public InformeSinQR(Persona rescatista, Ubicacion ubicacionRescatista, MascotaEncontrada mascotaEncontrada,
                      ReceptorHogares receptorHogares, Animal tipoAnimal, List<Caracteristica> caracteristicas) {
    super(rescatista, ubicacionRescatista, mascotaEncontrada, receptorHogares);
    this.tipoAnimal = tipoAnimal;
    this.caracteristicas = caracteristicas;

  }

  public Animal getTipoAnimal() {
    return tipoAnimal;
  }

  public List<Caracteristica> getCaracteristicas() {
    return caracteristicas;
  }

  @Override
  public void procesarInforme() {
    generarPublicacion();
    super.procesarInforme();
  }

  private void generarPublicacion() {
    RepositorioAsociaciones repositorioAsociaciones = RepositorioAsociaciones.instancia;
    entityManager().persist(
        new Rescate(
            this.getRescatista(),
            this.getMascotaEncontrada(),
            repositorioAsociaciones.getAsociacionMasCercana(getMascotaEncontrada().getUbicacion())
        )
    );
  }
}
