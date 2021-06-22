package modelo.informe;

import client.ReceptorHogares;
import com.fasterxml.jackson.core.JsonProcessingException;
import client.hogares.Hogar;
import modelo.mascota.Animal;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import modelo.publicacion.Publicacion;
import repositorios.RepositorioInformes;
import repositorios.RepositorioPublicaciones;
import servicio.notificacion.NotificadorCorreo;

import java.util.List;

public class InformeSinQR extends Informe {

    private Animal tipoAnimal;
    private List<Caracteristica> caracteristicas;
    private RepositorioPublicaciones repositorioPublicaciones;
    private NotificadorCorreo notificadorCorreo;

    public InformeSinQR(Persona rescatista, Ubicacion ubicacionRescatista, String direccionRescatista,
                        MascotaEncontrada mascotaEncontrada, RepositorioInformes repositorioInformes,
                        ReceptorHogares receptorHogares, Animal tipoAnimal, List<Caracteristica> caracteristicas,
                        RepositorioPublicaciones repositorioPublicaciones, NotificadorCorreo notificadorCorreo) {
        super(rescatista, ubicacionRescatista, direccionRescatista, mascotaEncontrada, repositorioInformes,
            receptorHogares);
        this.tipoAnimal = tipoAnimal;
        this.caracteristicas = caracteristicas;
        this.repositorioPublicaciones = repositorioPublicaciones;
        this.notificadorCorreo = notificadorCorreo;
    }

    public List<Hogar> getHogaresCercanos(Integer radioCercania) throws JsonProcessingException{
        return super.getHogaresCercanos(radioCercania, tipoAnimal, caracteristicas);
    }

    @Override
    public void procesarInforme() {
        generarPublicacion();
        super.procesarInforme();
    }

    private void generarPublicacion() {
        repositorioPublicaciones.agregarPublicacion(
            new Publicacion(
                this.getRescatista().getDatosDeContacto(),
                this.getMascotaEncontrada().getFotos(),
                notificadorCorreo)
        );
    }

}
