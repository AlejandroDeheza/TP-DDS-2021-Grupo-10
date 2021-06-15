package repositorios;

import modelo.publicacion.Publicacion;

import java.util.ArrayList;
import java.util.List;
import modelo.usuario.Usuario;
import servicio.notificacion.Notificacion;
import servicio.notificacion.NotificacionCorreo;
import servicio.notificacion.NotificacionSender;

import java.util.stream.Collectors;
public class RepositorioPublicaciones {
    private NotificacionSender notificacionCorreo;
    private static RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones(new NotificacionCorreo());
    private List<Publicacion> publicaciones = new ArrayList<>();

    //usamos el constructor solo para tests
    public RepositorioPublicaciones(NotificacionSender notificacionCorreo) {
        this.notificacionCorreo = notificacionCorreo;
    }
    //usamos el getInstance en el codigo de produccion
    public static RepositorioPublicaciones getInstance() {
        return repositorioPublicaciones;
    }

    public void agregarPublicacion(Publicacion publicacion) {
        publicaciones.add(publicacion);
    }

    public void encontreMiMascota(Publicacion publicacion, Usuario usuario) {
        Publicacion publicacionConMascota = publicaciones.stream().filter(publicacion1 -> publicacion1.equals(publicacion)).findFirst()
                .get();
        publicacionConMascota.agregarDuenio(usuario);
        String cuerpoMsg="El dueño encontro una mascota que vos rescataste. Por favor comunicarse al " + usuario.getPersona().getDatosDeContacto().getEmail();
        Notificacion notificacion = new Notificacion(publicacion.getDatosDeContactoRescatista(), null,cuerpoMsg,"Hogar de Patitas","Han Encontrado una mascota que rescataste!");

        notificacionCorreo.enviarNotificacion(notificacion);
    }

    public List<Publicacion> listarPublicacionesSinDuenio() {
        return publicaciones.stream().filter(publicacion -> !publicacion.tieneDuenio()).collect(Collectors.toList());
    }

    public List<Publicacion> listarPublicacionesConDuenio() {
        return publicaciones.stream().filter(publicacion -> publicacion.tieneDuenio()).collect(Collectors.toList());
    }

}