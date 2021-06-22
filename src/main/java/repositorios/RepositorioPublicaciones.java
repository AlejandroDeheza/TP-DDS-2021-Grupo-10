package repositorios;

import modelo.publicacion.Publicacion;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPublicaciones {
    private static RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();
    private List<Publicacion> publicaciones = new ArrayList<>();
    private List<Publicacion> publicacionesEncontradas = new ArrayList<>();

    public void agregarPublicacion(Publicacion publicacion) {
        publicaciones.add(publicacion);
    }

    public void marcarPublicacionComoEncontrada(Publicacion publicacion) {
        publicaciones.remove(publicacion);
        publicacionesEncontradas.add(publicacion);
    }


    //el repositorio, en codigo de produccion, lo inyectamos por constructor
    //usamos el constructor solo para tests
    public RepositorioPublicaciones() {}
    //usamos el getInstance en Main
    public static RepositorioPublicaciones getInstance() {
        return repositorioPublicaciones;
    }

    // GETTERS
    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public List<Publicacion> getPublicacionesEncontradas() {
        return publicacionesEncontradas;
    }

}