package modelo.informe;

import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.Animal;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import modelo.publicacion.Rescate;
import repositorios.RepositorioAsociaciones;
import repositorios.RepositorioRescates;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "informe_sin_QR")
public class InformeSinQR extends InformeRescate {

  @Enumerated(EnumType.STRING)
  private Animal tipoAnimal;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Caracteristica> caracteristicas;

  // para hibernate
  private InformeSinQR() {

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

  public List<Hogar> getHogaresCercanos(Integer radioCercania) {
    return super.getHogaresCercanos(
        radioCercania,
        tipoAnimal,
        this.getMascotaEncontrada().getTamanio(), caracteristicas
    );
  }

  @Override
  public void procesarInforme() {
    generarPublicacion();
    super.procesarInforme();
  }

  private void generarPublicacion() {
    new RepositorioRescates().agregar(
        new Rescate(
            this.getRescatista(),
            this.getMascotaEncontrada(),
            new RepositorioAsociaciones().getAsociacionMasCercana(getMascotaEncontrada().getUbicacion())
        )
    );
  }
}
