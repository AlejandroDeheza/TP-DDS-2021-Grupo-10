package modelo.informe;

import client.ReceptorHogares;
import client.hogares.Hogar;
import modelo.mascota.Animal;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import repositorios.RepositorioInformes;

import java.time.LocalDate;
import java.util.List;

public abstract class Informe {

  private Persona rescatista;
  private Ubicacion ubicacionRescatista;
  private String direccionRescatista;
  private MascotaEncontrada mascotaEncontrada;
  private RepositorioInformes repositorioInformes;
  private ReceptorHogares receptorHogares;

  public Informe(Persona rescatista, Ubicacion ubicacionRescatista, String direccionRescatista,
                 MascotaEncontrada mascotaEncontrada, RepositorioInformes repositorioInformes,
                 ReceptorHogares receptorHogares) {
    this.rescatista = rescatista;
    this.ubicacionRescatista = ubicacionRescatista;
    this.direccionRescatista = direccionRescatista;
    this.mascotaEncontrada = mascotaEncontrada;
    this.repositorioInformes = repositorioInformes;
    this.receptorHogares = receptorHogares;
  }

  public void procesarInforme(){
    repositorioInformes.marcarInformeComoProcesado(this);
  }

  public List<Hogar> getHogaresCercanos(Integer radioCercania, Animal tipoAnimal,
                                        List<Caracteristica> caracteristicas) {
    return receptorHogares.getHogaresDisponibles(
        ubicacionRescatista,
        radioCercania,
        tipoAnimal,
        caracteristicas
    );
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
