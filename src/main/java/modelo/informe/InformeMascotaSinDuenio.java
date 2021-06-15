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
import servicio.notificacion.NotificacionCorreo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class InformeMascotaSinDuenio extends InformeMascotaEncontrada {

    private ObtenerHogaresClient hogaresClient = new ObtenerHogaresClient();
    private RepositorioPublicaciones repositorioPublicaciones;
    private NotificacionCorreo notificacionCorreo;

    public InformeMascotaSinDuenio(Persona rescatista, Ubicacion direccion, MascotaEncontrada mascota,
                                   RepositorioInformes repositorioInformes, ObtenerHogaresClient hogaresClient,
                                   RepositorioPublicaciones repositorioPublicaciones,
                                   NotificacionCorreo notificacionCorreo) {
        super(rescatista, direccion, mascota, repositorioInformes);
        this.hogaresClient = hogaresClient;
        this.repositorioPublicaciones = repositorioPublicaciones;
        this.notificacionCorreo = notificacionCorreo;
    }

    @Override
    public void procesarInforme() {
        super.procesarInforme();
        repositorioPublicaciones.agregarPublicacion(new Publicacion(this.getRescatista().getDatosDeContacto(), mascota.getFotos(), notificacionCorreo));
    }

    public List<Hogar> getHogaresTransitorios(Integer radioCercania) throws JsonProcessingException{
        List<Hogar> hogares = this.hogaresClient.obtenerTodosLosHogares();                                      //Cambie estadoActual por Caractesitsicas
        return hogares.stream().filter( hogar -> hogar.esPosibleHogarDeTransito(radioCercania,mascota.getAnimal(),mascota.getCaracteristicas(),this.getDireccion())).collect(Collectors.toList());
    }


}
