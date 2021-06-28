package repositorios;

import modelo.publicacion.DarEnAdopcion;
import modelo.publicacion.Publicacion;
import modelo.publicacion.Rescate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPublicaciones {
  private static RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();
  private List<Publicacion> publicacionesPendientes = new ArrayList<>();
  private List<Publicacion> publicacionesProcesadas = new ArrayList<>();

  public void agregar(Rescate publicacion) {
    publicacionesPendientes.add(publicacion);
  }

  // public void agregar(IntencionDeAdopcion publicacion) {
  // publicacionesPendientes.add(publicacion);
  // }

  public void agregar(DarEnAdopcion publicacion) {
    publicacionesPendientes.add(publicacion);
  }

  public void marcarComoProcesada(Rescate publicacion) {
    publicacionesPendientes.remove(publicacion);
    publicacionesProcesadas.add(publicacion);
  }

  // public void marcarComoProcesada(IntencionDeAdopcion publicacion) {
  // publicacionesPendientes.remove(publicacion);
  // publicacionesProcesadas.add(publicacion);
  // }

  public void marcarComoProcesada(DarEnAdopcion publicacion) {
    publicacionesPendientes.remove(publicacion);
    publicacionesProcesadas.add(publicacion);
  }

  // el repositorio, en codigo de produccion, lo inyectamos por constructor
  // usamos el constructor solo para tests
  public RepositorioPublicaciones() {}

  // usamos el getInstance en Main
  public static RepositorioPublicaciones getInstance() {
    return repositorioPublicaciones;
  }

  // GETTERS
  public List<Publicacion> getPublicacionesPendientes() {
    return publicacionesPendientes;
  }

  public List<Publicacion> getPublicacionesProcesadas() {
    return publicacionesProcesadas;
  }

}
