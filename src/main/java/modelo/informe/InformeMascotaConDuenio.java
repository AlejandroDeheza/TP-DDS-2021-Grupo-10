package modelo.informe;

import modelo.mascota.Foto;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.DatosDeContacto;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import repositorios.RepositorioProperties;
import repositorios.RepositorioInformes;
import servicio.notificacion.Notificacion;
import servicio.notificacion.NotificacionSender;

import java.time.LocalDate;
import java.util.List;

import java.util.Properties;


public class InformeMascotaConDuenio extends InformeMascotaEncontrada {
    private Usuario duenioMascota;
    private NotificacionSender notificacionCorreo;

    public InformeMascotaConDuenio(Persona rescatista, Ubicacion direccion, MascotaEncontrada mascota, Usuario duenioMascota, NotificacionSender notificacionCorreo) {
        super(rescatista, direccion, mascota);
        this.duenioMascota = duenioMascota;
        this.notificacionCorreo = notificacionCorreo;
    }

    public Usuario getDuenioMascota() {
        return duenioMascota;
    }

    @Override
    public void procesarInforme() {

        Notificacion notificacion = buildNotificacion();
        super.procesarInforme();
        notificacionCorreo.enviarNotificacion(notificacion);
    }

    private Notificacion buildNotificacion() {

        Properties properties = RepositorioProperties.getInstance().getProperties();

        DatosDeContacto destinatario = this.getDuenioMascota().getPersona().getDatosDeContacto();
        String nombreSaludo = this.getDuenioMascota().getPersona().getNombre();
        String cuerpoMensaje = properties.getProperty("cuerpoMensaje_InformeMasctoaConDuenio");
        String saludo = properties.getProperty("saludo");
        String asunto = properties.getProperty("asunto_InformeMasctoaConDuenio");
        Notificacion mensaje = new Notificacion(destinatario, nombreSaludo, cuerpoMensaje, saludo, asunto);
        return mensaje;
    }
}
