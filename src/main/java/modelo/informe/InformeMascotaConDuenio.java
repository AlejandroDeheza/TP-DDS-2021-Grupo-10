package modelo.informe;

import modelo.mascota.Foto;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.DatosDeContacto;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import servicio.notificacion.Notificacion;
import servicio.notificacion.NotificacionCorreo;
import java.time.LocalDate;
import java.util.List;

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
        DatosDeContacto destinatario = this.getDuenioMascota().getPersona().getDatosDeContacto();
        String nombreSaludo = this.getDuenioMascota().getPersona().getNombre();
        String cuerpoMensaje = "Encontramos a tu mascota. Ingresa a nuestra pagina para contactarte con nosotros.";
        String saludo = "Hogar de Patitas";
        String asunto = "Encontramos a tu mascota!";
        Notificacion notificacion = new Notificacion(destinatario, nombreSaludo, cuerpoMensaje, saludo, asunto);
        super.procesarInforme();
        notificacionCorreo.enviarNotificacion(notificacion);
    }
}
