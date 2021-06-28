package repositorios;

import modelo.publicacion.DarEnAdopcion;
import modelo.publicacion.Publicacion;
import modelo.publicacion.Rescate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPublicaciones {
  private static RepositorioPublicaciones repositorioPublicaciones = new RepositorioPublicaciones();
  private List<DarEnAdopcion> darEnAdopcion = new ArrayList<>();
  // private List<IntencionDeAdopcion> intencionesDeAdopcion = new ArrayList<>();
  private List<Rescate> rescates = new ArrayList<>();
  private List<Publicacion> publicacionesProcesadas = new ArrayList<>();

  public void agregar(Rescate publicacion) {
    this.rescates.add(publicacion);
  }

  // public void agregar(IntencionDeAdopcion publicacion) {
  // publicacionesPendientes.add(publicacion);
  // }

  public void agregar(DarEnAdopcion publicacion) {
    this.darEnAdopcion.add(publicacion);
  }

  public void marcarComoProcesada(Rescate publicacion) {
    this.rescates.remove(publicacion);
    this.publicacionesProcesadas.add(publicacion);
  }

  // public void marcarComoProcesada(IntencionDeAdopcion publicacion) {
  // publicacionesPendientes.remove(publicacion);
  // publicacionesProcesadas.add(publicacion);
  // }

  public void marcarComoProcesada(DarEnAdopcion publicacion) {
    this.darEnAdopcion.remove(publicacion);
    this.publicacionesProcesadas.add(publicacion);
  }

  // el repositorio, en codigo de produccion, lo inyectamos por constructor
  // usamos el constructor solo para tests
  public RepositorioPublicaciones() {}

  // usamos el getInstance en Main
  public static RepositorioPublicaciones getInstance() {
    return repositorioPublicaciones;
  }

  // GETTERS
  public static RepositorioPublicaciones getRepositorioPublicaciones() {
    return repositorioPublicaciones;
  }

  public List<DarEnAdopcion> getDarEnAdopcion() {
    return this.darEnAdopcion;
  }

  public List<Rescate> getRescates() {
    return this.rescates;
  }

  public List<Publicacion> getPublicacionesProcesadas() {
    return this.publicacionesProcesadas;
  }
}
