package modelo.informe;

import modelo.mascota.Foto;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.DatosDeContacto;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import properties.MisProperties;
import repositorios.RepositorioInformes;

import servicio.notificacion.Notificacion;
import servicio.notificacion.NotificacionCorreo;

import java.time.LocalDate;
import java.util.List;

import java.util.Properties;


public class InformeMascotaConDuenio extends InformeMascotaEncontrada {
    private Usuario duenioMascota;
    private NotificacionCorreo notificacionCorreo = new NotificacionCorreo();

    public InformeMascotaConDuenio(Usuario duenioMascota, Persona rescatista, LocalDate fechaEncuentro, Ubicacion direccion, List<Foto> fotosMascota, Ubicacion lugarDeEncuentro, List<Caracteristica>  estadoActualMascota) {
        super(rescatista, fechaEncuentro, direccion, fotosMascota, lugarDeEncuentro, estadoActualMascota);
        this.duenioMascota = duenioMascota;
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

        Properties properties = new Properties();
        MisProperties.cargarInfoPropertiesTests(properties);

        DatosDeContacto destinatario = this.getDuenioMascota().getPersona().getDatosDeContacto();
        String nombreSaludo = this.getDuenioMascota().getPersona().getNombre();
        String cuerpoMensaje = properties.getProperty("cuerpoMensaje_InformeMasctoaConDuenio");
        //String cuerpoMensaje = "Encontramos a tu mascota. Ingresa a nuestra pagina para contactarte con nosotros.";
        String saludo = properties.getProperty("saludo");
        String asunto = properties.getProperty("asunto_InformeMasctoaConDuenio");
        Notificacion mensaje = new Notificacion(destinatario, nombreSaludo, cuerpoMensaje, saludo, asunto);
        return mensaje;
    }
}
