package repositorios;

import java.util.ArrayList;
import java.util.List;
import modelo.suscripcion.SuscripcionAdopciones;

public class RepositorioSuscripcionesAdopcion {
  private static RepositorioSuscripcionesAdopcion repositorioSuscripcionesAdopcion = new RepositorioSuscripcionesAdopcion();
  private List<SuscripcionAdopciones> suscripciones = new ArrayList<>();
  private List<SuscripcionAdopciones> precesadas = new ArrayList<>();

  public void agregar(SuscripcionAdopciones publicacion) {
    this.suscripciones.add(publicacion);
  }

  public void marcarComoProcesada(SuscripcionAdopciones publicacion) {
    this.suscripciones.remove(publicacion);
    this.precesadas.add(publicacion);
  }

  // el repositorio, en codigo de produccion, lo inyectamos por constructor
  // usamos el constructor solo para tests
  public RepositorioSuscripcionesAdopcion() {}

  // usamos el getInstance en Main
  public static RepositorioSuscripcionesAdopcion getInstance() {
    return repositorioSuscripcionesAdopcion;
  }

  public List<SuscripcionAdopciones> getSuscripciones() {
    return this.suscripciones;
  }

  public List<SuscripcionAdopciones> getPrecesadas() {
    return this.precesadas;
  }
}
