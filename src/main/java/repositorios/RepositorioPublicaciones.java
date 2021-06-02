package repositorios;

import modelo.publicacion.Publicacion;
import excepciones.PublicacionExistenteException;
import excepciones.PublicacionInexistenteException;

import java.util.ArrayList;
import java.util.List;
import modelo.usuario.Usuario;
import java.util.stream.Collectors;
public class RepositorioPublicaciones {

    private static RepositorioPublicaciones repositorioPublicaciones;
    private List<Publicacion> publicaciones = new ArrayList<>();

    //usamos el constructor solo para tests
    public RepositorioPublicaciones() {
    }

    //usamos el getInstance en el codigo de produccion
    public static RepositorioPublicaciones getInstance() {
        if (repositorioPublicaciones == null) {
            repositorioPublicaciones = new RepositorioPublicaciones();
        }
        return repositorioPublicaciones;
    }

    public void agregarPublicacion(Publicacion publicacion){
        if (publicaciones.contains(publicacion))
            throw new PublicacionExistenteException();
        publicaciones.add(publicacion);
    }

    public void encontreMiMascota(Publicacion publicacion, Usuario usuario) {
        if (!publicaciones.contains(publicacion))
            throw new PublicacionInexistenteException();
      Publicacion publicacionConMascota= publicaciones.stream().filter(publicacion1 ->publicacion1.equals(publicacion)) .findFirst()
              .get();
      publicacionConMascota.agregarDuenio(usuario);
    // TODO: notificar DatosDeContacto (rescatista) que se encontro la mascota del usuario

    }

    public List<Publicacion> listarPublicacionesSinDuenio(){
        return publicaciones.stream().filter(publicacion -> !publicacion.tieneDuenio()).collect(Collectors.toList());
    }

    public List<Publicacion> listarPublicacionesConDuenio(){
        return publicaciones.stream().filter(publicacion -> publicacion.tieneDuenio()).collect(Collectors.toList());
    }
}
