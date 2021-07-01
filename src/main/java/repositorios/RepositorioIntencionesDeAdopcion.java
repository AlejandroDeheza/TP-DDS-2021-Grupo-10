package repositorios;

import java.util.ArrayList;
import java.util.List;
import modelo.publicacion.IntencionDeAdopcion;
import modelo.publicacion.Publicacion;

public class RepositorioIntencionesDeAdopcion {
  private static RepositorioIntencionesDeAdopcion RepositorioIntencionesDeAdopcion = new RepositorioIntencionesDeAdopcion();
  private List<IntencionDeAdopcion> intencionesDeAdopcion = new ArrayList<>();
  private List<Publicacion> publicacionesProcesadas = new ArrayList<>();

  public void agregar(IntencionDeAdopcion publicacion) {
    this.intencionesDeAdopcion.add(publicacion);
  }

  public void marcarComoProcesada(IntencionDeAdopcion publicacion) {
    this.intencionesDeAdopcion.remove(publicacion);
    this.publicacionesProcesadas.add(publicacion);
  }

  // el repositorio, en codigo de produccion, lo inyectamos por constructor
  // usamos el constructor solo para tests
  public RepositorioIntencionesDeAdopcion() {}

  // usamos el getInstance en Main
  public static RepositorioIntencionesDeAdopcion getInstance() {
    return RepositorioIntencionesDeAdopcion;
  }

  public List<IntencionDeAdopcion> getIntencionesDeAdopcion() {
    return this.intencionesDeAdopcion;
  }

  public List<Publicacion> getPublicacionesProcesadas() {
    return this.publicacionesProcesadas;
  }
}
