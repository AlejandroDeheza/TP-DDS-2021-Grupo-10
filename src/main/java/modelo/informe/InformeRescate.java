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


@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "informe")
public abstract class InformeRescate extends EntidadPersistente {

  @OneToOne
  private Persona rescatista;

  @Embedded
  private Ubicacion ubicacionRescatista;

  @OneToOne
  private MascotaEncontrada mascotaEncontrada;

  @Transient
  private RepositorioInformes repositorioInformes;

  @Transient
  private ReceptorHogares receptorHogares;

  public InformeRescate(Persona rescatista, Ubicacion ubicacionRescatista, MascotaEncontrada mascotaEncontrada,
                        RepositorioInformes repositorioInformes, ReceptorHogares receptorHogares) {
    this.rescatista = rescatista;
    this.ubicacionRescatista = ubicacionRescatista;
    this.mascotaEncontrada = mascotaEncontrada;
    this.repositorioInformes = repositorioInformes;
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
    repositorioInformes.marcarInformeComoProcesado(this);
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

}
