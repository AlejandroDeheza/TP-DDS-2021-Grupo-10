package modelo.hogarDeTransito.respuestas;

import com.fasterxml.jackson.annotation.JsonProperty;
import modelo.informe.Ubicacion;
import modelo.mascota.Animal;
import modelo.mascota.TamanioMascota;
import modelo.mascota.caracteristica.Caracteristica;

import java.util.List;
import java.util.stream.Collectors;

public class RespuestaDeHogar {
    @JsonProperty("id")
    private String id;

  @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("ubicacion")
    private Ubicacion ubicacion;

    @JsonProperty("telefono")
    private String telefono;

    @JsonProperty("admisiones")
    private RespuestaDeAdmision admisiones;

    @JsonProperty("capacidad")
    private Integer capacidad;

    @JsonProperty("lugares_disponibles")
    private Integer lugaresDisponibles;

    @JsonProperty("patio")
    private Boolean tienePatio;

    @JsonProperty("caracteristicas")
    private List<String> caracteristicas;

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

    private Boolean aceptaAnimal(Animal animal){
        return admisiones.aceptaAnimal(animal);
    }

    private Boolean aceptaTamanioMascota(TamanioMascota tamanioMascota) {
        return
            (!tienePatio && tamanioMascota == TamanioMascota.CHICO) ||
            (tienePatio && tamanioMascota != TamanioMascota.CHICO);
    }

    private Boolean tieneLugaresDisponibles() {
        return lugaresDisponibles > 0;
    }

    private Boolean cumpleRequisitoDeCaracterista(List<String> valoresCaracteristicas){
        return valoresCaracteristicas.containsAll(this.caracteristicas);
    }

    private Boolean estaDentroDeRadio(Integer radioCercania, Ubicacion ubicacionDireccion) {
        // https://www.geeksforgeeks.org/program-distance-two-points-earth/
            double latDireccionRescatista;
            double latHogar;
            double lonDireccionRescatista;
            double lonHogar;
            lonDireccionRescatista = Math.toRadians(ubicacionDireccion.getLongitud());
            lonHogar = Math.toRadians(this.ubicacion.getLongitud());
            latDireccionRescatista = Math.toRadians(ubicacionDireccion.getLatitud());
            latHogar = Math.toRadians(this.ubicacion.getLatitud());

            // Haversine formula
            double dlon = lonHogar - lonDireccionRescatista;
            double dlat = latHogar - latDireccionRescatista;
            double a = Math.pow(Math.sin(dlat / 2), 2)
                    + Math.cos(latDireccionRescatista) * Math.cos(latHogar)
                    * Math.pow(Math.sin(dlon / 2),2);

            double c = 2 * Math.asin(Math.sqrt(a));

            // Radius of earth in kilometers. Use 3956
            // for miles
            double r = 6371;

            // calculate the result
            return (c * r)<radioCercania;
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

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setAdmisiones(RespuestaDeAdmision admisiones) {
        this.admisiones = admisiones;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public void setLugaresDisponibles(Integer lugaresDisponibles) {
        this.lugaresDisponibles = lugaresDisponibles;
    }

    public void setTienePatio(Boolean tienePatio) {
        this.tienePatio = tienePatio;
    }

    public void setCaracteristicas(List<String> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }
}