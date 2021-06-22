package modelo.informe;

import client.ObtenerHogaresClient;
import client.hogares.Hogar;
import com.fasterxml.jackson.core.JsonProcessingException;
import modelo.mascota.Animal;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import repositorios.RepositorioInformes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Informe {

  private Persona rescatista;
  private Ubicacion ubicacionRescatista;
  private String direccionRescatista;
  private MascotaEncontrada mascotaEncontrada;
  private RepositorioInformes repositorioInformes;
  private ObtenerHogaresClient receptorHogares;

  public Informe(Persona rescatista, Ubicacion ubicacionRescatista, String direccionRescatista,
                 MascotaEncontrada mascotaEncontrada, RepositorioInformes repositorioInformes,
                 ObtenerHogaresClient receptorHogares) {
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

  public List<Hogar> getHogaresCercanos(Integer radioCercania, Animal tipoAnimal, List<Caracteristica> caracteristicas)
      throws JsonProcessingException {
    List<Hogar> hogares = receptorHogares.obtenerTodosLosHogares();
    return hogares.stream().filter( hogar -> hogar.esPosibleHogarDeTransito(
        radioCercania,
        tipoAnimal,
        caracteristicas,
        ubicacionRescatista
    )).collect(Collectors.toList());
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
