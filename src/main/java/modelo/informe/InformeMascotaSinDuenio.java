package modelo.informe;

import client.ObtenerHogaresClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import client.hogares.Hogar;
import modelo.mascota.MascotaEncontrada;
import modelo.persona.Persona;
import modelo.publicacion.Publicacion;
import repositorios.RepositorioInformes;
import repositorios.RepositorioPublicaciones;
import servicio.notificacion.NotificadorCorreo;

import java.util.List;
import java.util.stream.Collectors;

public class InformeMascotaSinDuenio extends InformeMascotaEncontrada {

    private ObtenerHogaresClient hogaresClient = new ObtenerHogaresClient();
    private RepositorioPublicaciones repositorioPublicaciones;
    private NotificadorCorreo notificadorCorreo;

    public InformeMascotaSinDuenio(Persona rescatista, Ubicacion direccion, MascotaEncontrada mascota,
                                   RepositorioInformes repositorioInformes, ObtenerHogaresClient hogaresClient,
                                   RepositorioPublicaciones repositorioPublicaciones,
                                   NotificadorCorreo notificadorCorreo) {
        super(rescatista, direccion, mascota, repositorioInformes);
        this.hogaresClient = hogaresClient;
        this.repositorioPublicaciones = repositorioPublicaciones;
        this.notificadorCorreo = notificadorCorreo;
    }

    @Override
    public void procesarInforme() {
        super.procesarInforme();
        repositorioPublicaciones.agregarPublicacion(new Publicacion(this.getRescatista().getDatosDeContacto(), mascota.getFotos(), notificadorCorreo));
    }

    public List<Hogar> getHogaresTransitorios(Integer radioCercania) throws JsonProcessingException{
        List<Hogar> hogares = this.hogaresClient.obtenerTodosLosHogares();                                      //Cambie estadoActual por Caractesitsicas
        return hogares.stream().filter( hogar -> hogar.esPosibleHogarDeTransito(radioCercania,mascota.getAnimal(),mascota.getCaracteristicas(),this.getDireccion())).collect(Collectors.toList());
    }


}
