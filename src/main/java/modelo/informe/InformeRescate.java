package modelo.informe;

import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.Animal;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.TamanioMascota;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import repositorios.RepositorioInformes;

import java.time.LocalDate;
import java.util.List;

public abstract class InformeRescate {

  private Persona rescatista;
  private Ubicacion ubicacionRescatista;
  private String direccionRescatista;
  private MascotaEncontrada mascotaEncontrada;
  private RepositorioInformes repositorioInformes;
  private ReceptorHogares receptorHogares;

  public InformeRescate(Persona rescatista, Ubicacion ubicacionRescatista, String direccionRescatista,
                        MascotaEncontrada mascotaEncontrada, RepositorioInformes repositorioInformes,
                        ReceptorHogares receptorHogares) {
    this.rescatista = rescatista;
    this.ubicacionRescatista = ubicacionRescatista;
    this.direccionRescatista = direccionRescatista;
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

  public void procesarInforme(){
    repositorioInformes.marcarInformeComoProcesado(this);
  }

  public MascotaEncontrada getMascotaEncontrada() {
    return mascotaEncontrada;
  }

  public Persona getRescatista() {
    return rescatista;
  }

  public String getDireccionRescatista() {
    return direccionRescatista;
  }

  public Ubicacion getUbicacionRescatista() {
    return ubicacionRescatista;
  }

  public LocalDate getFechaEncuentro(){
    return mascotaEncontrada.getFechaEncuentro();
  }

}
