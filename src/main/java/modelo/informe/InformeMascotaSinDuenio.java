package modelo.informe;

import client.ObtenerHogaresClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import client.hogares.Hogar;
import modelo.mascota.Animal;
import modelo.mascota.Foto;
import modelo.mascota.MascotaEncontrada;
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
    private RepositorioPublicaciones repositorioPublicaciones;

    public InformeMascotaSinDuenio(Persona rescatista, Ubicacion direccion, MascotaEncontrada mascota, ObtenerHogaresClient hogaresClient) {
        super(rescatista, direccion, mascota);
        this.hogaresClient = hogaresClient;
    }


    @Override
    public void procesarInforme() {
        super.procesarInforme();
        repositorioPublicaciones.agregarPublicacion(new Publicacion(this.getRescatista().getDatosDeContacto(),mascota.getFotos());
    }

    public List<Hogar> getHogaresTransitorios(Integer radioCercania) throws JsonProcessingException{
        List<Hogar> hogares = this.hogaresClient.obtenerTodosLosHogares();                                      //Podria ser un String directo, para no generar un Map al pedo
        return hogares.stream().filter( hogar -> hogar.esPosibleHogarDeTransito(radioCercania,mascota.getAnimal(),mascota.getEstadoActual(),this.getDireccion())).collect(Collectors.toList());
    }


}
