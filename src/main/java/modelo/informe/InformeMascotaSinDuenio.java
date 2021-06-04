package modelo.informe;

import client.ObtenerHogaresClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import modelo.hogares.Hogar;
import modelo.mascota.Animal;
import modelo.mascota.Foto;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import modelo.publicacion.Publicacion;
import repositorios.RepositorioInformes;
import repositorios.RepositorioPublicaciones;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class InformeMascotaSinDuenio extends InformeMascotaEncontrada {

    ObtenerHogaresClient hogaresClient = new ObtenerHogaresClient();
    private Animal animal;
    private RepositorioPublicaciones repositorioPublicaciones;

    public InformeMascotaSinDuenio(Persona rescatista, LocalDate fechaEncuentro, Ubicacion direccion, List<Foto> fotosMascota, Ubicacion lugarDeEncuentro, List<Caracteristica>  estadoActualMascota
            , Animal animal, RepositorioInformes repositorioInformes, RepositorioPublicaciones repositorioPublicaciones) {
        super(rescatista, fechaEncuentro, direccion, fotosMascota, lugarDeEncuentro,estadoActualMascota,repositorioInformes);
        this.animal=animal;
    }

    public Animal getAnimal() {
        return animal;
    }

    @Override
    public void procesarInforme() {
        super.procesarInforme();
        repositorioPublicaciones.agregarPublicacion(new Publicacion(this.getRescatista().getDatosDeContacto(),getFotosMascota()));
    }

    public List<Hogar> getHogaresTransitorios(Integer radioCercania) throws JsonProcessingException{
        List<Hogar> hogares = this.hogaresClient.obtenerTodosLosHogares();
        return hogares.stream().filter( hogar -> hogar.esPosibleHogarDeTransito(radioCercania,getAnimal(),getEstadoActualMascota(),this.getDireccion())).collect(Collectors.toList());
    }

}
