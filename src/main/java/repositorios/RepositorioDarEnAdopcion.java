package repositorios;

import modelo.publicacion.DarEnAdopcion;

import java.util.ArrayList;
import java.util.List;

public class RepositorioDarEnAdopcion {
  private static RepositorioDarEnAdopcion repo = new RepositorioDarEnAdopcion();
  private List<DarEnAdopcion> publicaciones = new ArrayList<>();
  private List<DarEnAdopcion> procesadas = new ArrayList<>();

  public void agregar(DarEnAdopcion publicacion) {
    this.publicaciones.add(publicacion);
  }

  public void marcarComoProcesada(DarEnAdopcion publicacion) {
    this.publicaciones.remove(publicacion);
    this.procesadas.add(publicacion);
  }

  // el repositorio, en codigo de produccion, lo inyectamos por constructor
  // usamos el constructor solo para tests
  public RepositorioDarEnAdopcion() {}
  // usamos el getInstance en Main
  public static RepositorioDarEnAdopcion getInstance() {
    return repo;
  }

  // GETTERS
  public List<DarEnAdopcion> getPublicaciones() {
    return this.publicaciones;
  }

  public List<DarEnAdopcion> getProcesadas() {
    return this.procesadas;
  }
}
