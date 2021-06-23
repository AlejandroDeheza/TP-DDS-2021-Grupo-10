package modelo.informe;

import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.MascotaRegistrada;
import modelo.persona.Persona;
import utils.ReceptorProperties;
import repositorios.RepositorioInformes;
import modelo.notificacion.Notificacion;
import modelo.notificacion.Notificador;

import java.util.List;
import java.util.Properties;

public class InformeConQR extends InformeRescate {
    private MascotaRegistrada mascotaRegistrada;
    private Notificador notificador;
    private ReceptorProperties receptorProperties;

    public InformeConQR(Persona rescatista, Ubicacion ubicacionRescatista, String direccionRescatista,
                        MascotaEncontrada mascotaEncontrada, RepositorioInformes repositorioInformes,
                        ReceptorHogares receptorHogares, MascotaRegistrada mascotaRegistrada,
                        Notificador notificador, ReceptorProperties receptorProperties) {
        super(rescatista, ubicacionRescatista, direccionRescatista, mascotaEncontrada, repositorioInformes,
            receptorHogares);
        this.mascotaRegistrada = mascotaRegistrada;
        this.notificador = notificador;
        this.receptorProperties = receptorProperties;
    }

    public List<Hogar> getHogaresCercanos(Integer radioCercania) {
        return super.getHogaresCercanos(
            radioCercania,
            mascotaRegistrada.getAnimal(),
            mascotaRegistrada.getTamanio(),
            mascotaRegistrada.getCaracteristicas()
        );
    }

    @Override
    public void procesarInforme() {
        notificarAlDuenio();
        super.procesarInforme();
    }

    private void notificarAlDuenio() {
        Properties properties = receptorProperties.getProperties();
        notificador.notificar(
            new Notificacion(
                mascotaRegistrada.getDuenio().getPersona().getDatosDeContacto(),
                properties.getProperty("asunto_InformeMasctoaConDuenio"),
                mascotaRegistrada.getDuenio().getPersona().getNombre(),
                properties.getProperty("cuerpoMensaje_InformeMasctoaConDuenio"),
                properties.getProperty("saludo")
            )
        );
    }

}
