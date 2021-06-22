package modelo.informe;

import client.ObtenerHogaresClient;
import client.hogares.Hogar;
import com.fasterxml.jackson.core.JsonProcessingException;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.MascotaRegistrada;
import modelo.persona.Persona;
import repositorios.RepositorioProperties;
import repositorios.RepositorioInformes;
import servicio.notificacion.Notificacion;
import servicio.notificacion.Notificador;

import java.util.List;
import java.util.Properties;

public class InformeConQR extends Informe {
    private MascotaRegistrada mascotaRegistrada;
    private Notificador notificador;
    private RepositorioProperties repositorioProperties;

    public InformeConQR(Persona rescatista, Ubicacion ubicacionRescatista, String direccionRescatista,
                        MascotaEncontrada mascotaEncontrada, RepositorioInformes repositorioInformes,
                        ObtenerHogaresClient receptorHogares, MascotaRegistrada mascotaRegistrada,
                        Notificador notificador, RepositorioProperties repositorioProperties) {
        super(rescatista, ubicacionRescatista, direccionRescatista, mascotaEncontrada, repositorioInformes,
            receptorHogares);
        this.mascotaRegistrada = mascotaRegistrada;
        this.notificador = notificador;
        this.repositorioProperties = repositorioProperties;
    }

    public List<Hogar> getHogaresCercanos(Integer radioCercania) throws JsonProcessingException {
        return super.getHogaresCercanos(
            radioCercania,
            mascotaRegistrada.getAnimal(),
            mascotaRegistrada.getCaracteristicas()
        );
    }

    @Override
    public void procesarInforme() {
        notificarAlDuenio();
        super.procesarInforme();
    }

    private void notificarAlDuenio() {
        Properties properties = repositorioProperties.getProperties();
        notificador.enviarNotificacion(
            new Notificacion(
                mascotaRegistrada.getDuenio().getPersona().getDatosDeContacto(),
                mascotaRegistrada.getDuenio().getPersona().getNombre(),
                properties.getProperty("cuerpoMensaje_InformeMasctoaConDuenio"),
                properties.getProperty("saludo"),
                properties.getProperty("asunto_InformeMasctoaConDuenio")
            )
        );
    }

}
