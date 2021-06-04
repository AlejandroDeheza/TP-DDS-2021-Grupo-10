package modelo.hogares;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import modelo.informe.Ubicacion;
import modelo.mascota.Animal;
import modelo.mascota.Mascota;
import modelo.mascota.caracteristica.Caracteristica;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Hogar {
    @JsonProperty("id")
    private String id;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("ubicacion")
    private Ubicacion ubicacion;

    @JsonProperty("telefono")
    private String telefono;

    @JsonProperty("admisiones")
    private Admision admisiones;

    @JsonProperty("capacidad")
    private Integer capacidad;

    @JsonProperty("lugares_disponibles")
    private Integer lugaresDisponibles;

    @JsonProperty("patio")
    private Boolean tienePatio;

    @JsonProperty("caracteristicas")
    private List<String> caracteristicas;

    public boolean esPosibleHogarDeTransito(Integer radioCercania, Mascota mascota, Ubicacion direccion) {
        List<String> valoresCaracteristicasMascota = mascota.getCaracteristicas().stream().map(caracteristica -> caracteristica.getValorCaracteristica()).collect(Collectors.toList());
        return aceptaAnimal(mascota.getAnimal()) && aceptaTamanioMascota(mascota.getCaracteristicas())
                && tieneLugaresDisponibles() && estaDentroDeRadio(radioCercania, direccion) &&
                cumpleRequisitoDeCaracterista(valoresCaracteristicasMascota);
    }

    private Boolean cumpleRequisitoDeCaracterista(List<String> caracteristicas){
        return getCaracteristicas().containsAll(caracteristicas);
    }
    private Boolean tieneLugaresDisponibles() {
        return getLugaresDisponibles() > 0;
    }

    private Boolean  aceptaAnimal (Animal animal){
        return this.getAdmisiones().aceptaAnimal(animal);
    }

    private Boolean aceptaTamanioMascota(List<Caracteristica> caracteristicas) {
        Boolean esMascotaPequenia = caracteristicas
                .stream()
                .anyMatch(caracteristica ->
                        caracteristica.getNombreCaracteristica().equals("tamaño") && caracteristica.getValorCaracteristica().equals("pequeño"));
        return (!this.getTienePatio() && esMascotaPequenia) || (this.getTienePatio() && !esMascotaPequenia) ;
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
}