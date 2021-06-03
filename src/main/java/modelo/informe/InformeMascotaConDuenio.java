package modelo.informe;

import modelo.mascota.Foto;
<<<<<<< HEAD
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.DatosDeContacto;
=======
import modelo.mascota.Mascota;
>>>>>>> d7ad8a7 (Fix del cliente)
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import repositorios.RepositorioInformes;
import servicio.notificacion.Notificacion;
import servicio.notificacion.NotificacionCorreo;
import java.time.LocalDate;
import java.util.List;

public class InformeMascotaConDuenio extends InformeMascotaEncontrada {
    private Usuario duenioMascota;
    private NotificacionCorreo notificacionCorreo = new NotificacionCorreo();

    public InformeMascotaConDuenio(Usuario duenioMascota, Persona rescatista, LocalDate fechaEncuentro, Ubicacion direccion, List<Foto> fotosMascota, Ubicacion lugarDeEncuentro, Caracteristica estadoActualMascota, RepositorioInformes repositorioInformes) {
        super(rescatista, fechaEncuentro, direccion, fotosMascota, lugarDeEncuentro, estadoActualMascota, repositorioInformes);
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
        DatosDeContacto destinatario = this.getDuenioMascota().getPersona().getDatosDeContacto();
        String nombreSaludo = this.getDuenioMascota().getPersona().getNombre();
        String cuerpoMensaje = "Encontramos a tu mascota. Ingresa a nuestra pagina para contactarte con nosotros.";
        String saludo = "Hogar de Patitas";
        String asunto = "Encontramos a tu mascota!";
        Notificacion mensaje = new Notificacion(destinatario, nombreSaludo, cuerpoMensaje, saludo, asunto);
        return mensaje;
    }
}
