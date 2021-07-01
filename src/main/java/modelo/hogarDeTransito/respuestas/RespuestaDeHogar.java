package modelo.hogarDeTransito.respuestas;

import com.fasterxml.jackson.annotation.JsonProperty;
import modelo.informe.Ubicacion;
import modelo.mascota.Animal;
import modelo.mascota.TamanioMascota;
import modelo.mascota.caracteristica.Caracteristica;

import java.util.List;
import java.util.stream.Collectors;

public class RespuestaDeHogar {

  private String id;
  private String nombre;
  private Ubicacion ubicacion;
  private String telefono;
  private RespuestaDeAdmision admisiones;
  private Integer capacidad;
  private Integer lugaresDisponibles;
  private Boolean tienePatio;
  private List<String> caracteristicas;

  public RespuestaDeHogar(@JsonProperty("id") String id, @JsonProperty("nombre") String nombre,
                          @JsonProperty("ubicacion") Ubicacion ubicacion, @JsonProperty("telefono") String telefono,
                          @JsonProperty("admisiones") RespuestaDeAdmision admisiones,
                          @JsonProperty("capacidad") Integer capacidad,
                          @JsonProperty("lugares_disponibles") Integer lugaresDisponibles,
                          @JsonProperty("patio") Boolean tienePatio,
                          @JsonProperty("caracteristicas") List<String> caracteristicas) {
    this.id = id;
    this.nombre = nombre;
    this.ubicacion = ubicacion;
    this.telefono = telefono;
    this.admisiones = admisiones;
    this.capacidad = capacidad;
    this.lugaresDisponibles = lugaresDisponibles;
    this.tienePatio = tienePatio;
    this.caracteristicas = caracteristicas;
  }

  public boolean estaDisponible(Ubicacion ubicacionRescatista, Integer radioCercania, Animal animal,
                                TamanioMascota tamanioMascota, List<Caracteristica> caracteristicas) {
    List<String> valoresCaracteristicas = caracteristicas
        .stream()
        .map(Caracteristica::getValorCaracteristica)
        .collect(Collectors.toList());
    return estaDentroDeRadio(radioCercania, ubicacionRescatista) && aceptaAnimal(animal) &&
        aceptaTamanioMascota(tamanioMascota) && tieneLugaresDisponibles() &&
        cumpleRequisitoDeCaracterista(valoresCaracteristicas);
  }

  private Boolean aceptaAnimal(Animal animal) {
    return admisiones.aceptaAnimal(animal);
  }

  private Boolean aceptaTamanioMascota(TamanioMascota tamanioMascota) {
    return tienePatio || tamanioMascota == TamanioMascota.CHICO;
  }

  private Boolean tieneLugaresDisponibles() {
    return lugaresDisponibles > 0;
  }

  private Boolean cumpleRequisitoDeCaracterista(List<String> valoresCaracteristicas) {
    return valoresCaracteristicas.containsAll(this.caracteristicas);
  }

  private Boolean estaDentroDeRadio(Integer radioCercaniaEnKilometros, Ubicacion ubicacionOrigen) {
    return ubicacion.getDistancia(ubicacionOrigen) < radioCercaniaEnKilometros; // calculate the result
  }

  public String getNombre() {
    return nombre;
  }

  public String getTelefono() {
    return telefono;
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }

  public String getId() {
    return id;
  }

  public Integer getCapacidad() {
    return capacidad;
  }

}