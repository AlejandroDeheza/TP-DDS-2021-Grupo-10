package repositorios;

import java.util.ArrayList;
import java.util.List;
import modelo.suscripcion.SuscripcionParaAdopcion;

public class RepositorioSuscripcionesParaAdopciones {
  private static RepositorioSuscripcionesParaAdopciones repositorioSuscripcionesParaAdopciones = new RepositorioSuscripcionesParaAdopciones();
  private List<SuscripcionParaAdopcion> suscripciones = new ArrayList<>();
  private List<SuscripcionParaAdopcion> precesadas = new ArrayList<>();

  public void agregar(SuscripcionParaAdopcion publicacion) {
    this.suscripciones.add(publicacion);
  }

  public void marcarComoProcesada(SuscripcionParaAdopcion publicacion) {
    this.suscripciones.remove(publicacion);
    this.precesadas.add(publicacion);
  }

  // el repositorio, en codigo de produccion, lo inyectamos por constructor
  // usamos el constructor solo para tests
  public RepositorioSuscripcionesParaAdopciones() {}

  // usamos el getInstance en Main
  public static RepositorioSuscripcionesParaAdopciones getInstance() {
    return repositorioSuscripcionesParaAdopciones;
  }

  public List<SuscripcionParaAdopcion> getSuscripciones() {
    return this.suscripciones;
  }

  public List<SuscripcionParaAdopcion> getPrecesadas() {
    return this.precesadas;
  }
}
