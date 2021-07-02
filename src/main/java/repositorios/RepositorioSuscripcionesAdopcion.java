package repositorios;

import java.util.ArrayList;
import java.util.List;
import modelo.publicacion.SuscripcionAdopciones;

public class RepositorioSuscripcionesAdopcion {
  private static RepositorioSuscripcionesAdopcion RepositorioSuscripcionesAdopcion = new RepositorioSuscripcionesAdopcion();
  private List<SuscripcionAdopciones> intencionesDeAdopcion = new ArrayList<>();
  private List<SuscripcionAdopciones> publicacionesProcesadas = new ArrayList<>();

  public void agregar(SuscripcionAdopciones publicacion) {
    this.intencionesDeAdopcion.add(publicacion);
  }

  public void marcarComoProcesada(SuscripcionAdopciones publicacion) {
    this.intencionesDeAdopcion.remove(publicacion);
    this.publicacionesProcesadas.add(publicacion);
  }

  // el repositorio, en codigo de produccion, lo inyectamos por constructor
  // usamos el constructor solo para tests
  public RepositorioSuscripcionesAdopcion() {}

  // usamos el getInstance en Main
  public static RepositorioSuscripcionesAdopcion getInstance() {
    return RepositorioSuscripcionesAdopcion;
  }

  public List<SuscripcionAdopciones> getIntencionesDeAdopcion() {
    return this.intencionesDeAdopcion;
  }

  public List<SuscripcionAdopciones> getPublicacionesProcesadas() {
    return this.publicacionesProcesadas;
  }
}
