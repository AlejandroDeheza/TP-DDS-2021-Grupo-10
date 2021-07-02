package repositorios;

import modelo.publicacion.DarEnAdopcion;

import java.util.ArrayList;
import java.util.List;

public class RepositorioDarEnAdopcion {
  private static RepositorioDarEnAdopcion repositorioPublicaciones = new RepositorioDarEnAdopcion();
  private List<DarEnAdopcion> darEnAdopcion = new ArrayList<>();
  private List<DarEnAdopcion> publicacionesProcesadas = new ArrayList<>();

  public void agregar(DarEnAdopcion publicacion) {
    this.darEnAdopcion.add(publicacion);
  }

  public void marcarComoProcesada(DarEnAdopcion publicacion) {
    this.darEnAdopcion.remove(publicacion);
    this.publicacionesProcesadas.add(publicacion);
  }

  // el repositorio, en codigo de produccion, lo inyectamos por constructor
  // usamos el constructor solo para tests
  public RepositorioDarEnAdopcion() {}
  // usamos el getInstance en Main
  public static RepositorioDarEnAdopcion getInstance() {
    return repositorioPublicaciones;
  }

  // GETTERS
  public List<DarEnAdopcion> getDarEnAdopcion() {
    return this.darEnAdopcion;
  }

  public List<DarEnAdopcion> getPublicacionesProcesadas() {
    return this.publicacionesProcesadas;
  }
}
