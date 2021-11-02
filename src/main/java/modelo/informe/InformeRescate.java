package modelo.informe;

import modelo.EntidadPersistente;
import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.Animal;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.TamanioMascota;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import repositorios.RepositorioInformes;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "informe")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class InformeRescate extends EntidadPersistente {

  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //Correcto - OneToOne? o ManyToOne?
  private Persona rescatista;

  @Embedded
  private Ubicacion ubicacionRescatista;

  @OneToOne(cascade = CascadeType.ALL) //Chequear - Tiene sentido una mascotaEncontrada sin su Informe?
  private MascotaEncontrada mascotaEncontrada;

  @Transient
  private ReceptorHogares receptorHogares;

  private Boolean estaProcesado = false;

  // para hibernate
  protected InformeRescate() {

  }

  public InformeRescate(Persona rescatista, Ubicacion ubicacionRescatista, MascotaEncontrada mascotaEncontrada,
                        ReceptorHogares receptorHogares) {
    this.rescatista = rescatista;
    this.ubicacionRescatista = ubicacionRescatista;
    this.mascotaEncontrada = mascotaEncontrada;
    this.receptorHogares = receptorHogares;
  }

  public List<Hogar> getHogaresCercanos(Integer radioCercania, Animal tipoAnimal, TamanioMascota tamanioMascota,
                                        List<Caracteristica> caracteristicas) {
    return receptorHogares.getHogaresDisponibles(
        ubicacionRescatista,
        radioCercania,
        tipoAnimal,
        tamanioMascota,
        caracteristicas
    );
  }

  public void procesarInforme() {
    this.estaProcesado = true;
  }

  public MascotaEncontrada getMascotaEncontrada() {
    return mascotaEncontrada;
  }

  public Persona getRescatista() {
    return rescatista;
  }

  public String getDireccionRescatista() {
    return ubicacionRescatista.getDireccion();
  }

  public Ubicacion getUbicacionRescatista() {
    return ubicacionRescatista;
  }

  public LocalDate getFechaEncuentro() {
    return mascotaEncontrada.getFechaEncuentro();
  }

  public Boolean getEstaProcesado(){
    return this.estaProcesado;
  }

}
