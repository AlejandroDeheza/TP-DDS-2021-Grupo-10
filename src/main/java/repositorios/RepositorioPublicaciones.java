package repositorios;

import modelo.publicacion.Rescate;

import java.util.ArrayList;
import java.util.List;

public class RepositorioPublicaciones {
  private static RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();
  private List<Rescate> publicaciones = new ArrayList<>();
  private List<Rescate> publicacionesEncontradas = new ArrayList<>();

  public void agregarPublicacion(Rescate publicacion) {
    publicaciones.add(publicacion);
  }

  public void marcarPublicacionComoEncontrada(Rescate publicacion) {
    publicaciones.remove(publicacion);
    publicacionesEncontradas.add(publicacion);
  }


  //el repositorio, en codigo de produccion, lo inyectamos por constructor
  //usamos el constructor solo para tests
  public RepositorioPublicaciones() {
  }
  //usamos el getInstance en Main
  public static RepositorioPublicaciones getInstance() {
    return repositorioPublicaciones;
  }

  // GETTERS
  public List<Rescate> getPublicaciones() {
    return publicaciones;
  }

  public List<Rescate> getPublicacionesEncontradas() {
    return publicacionesEncontradas;
  }

}