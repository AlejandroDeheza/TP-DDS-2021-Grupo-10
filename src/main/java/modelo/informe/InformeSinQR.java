package modelo.informe;

import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import com.fasterxml.jackson.core.JsonProcessingException;
import modelo.mascota.Animal;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import modelo.publicacion.Publicacion;
import repositorios.RepositorioInformes;
import repositorios.RepositorioPublicaciones;
import modelo.notificacion.NotificadorCorreo;

import java.util.List;

public class InformeSinQR extends InformeRescate {

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
        return super.getHogaresCercanos(
            radioCercania,
            tipoAnimal,
            this.getMascotaEncontrada().getTamanio(),
            caracteristicas);
    }

    @Override
    public void procesarInforme() {
        generarPublicacion();
        super.procesarInforme();
    }

    private void generarPublicacion() {
        repositorioPublicaciones.agregarPublicacion(
            new Publicacion(
                this.getMascotaEncontrada(),
                this.getRescatista().getDatosDeContacto(),
                notificadorCorreo)
        );
    }

}
