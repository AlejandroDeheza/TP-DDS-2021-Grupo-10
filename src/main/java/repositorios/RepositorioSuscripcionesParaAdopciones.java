package repositorios;

import java.util.ArrayList;
import java.util.List;
import modelo.suscripcion.SuscripcionParaAdopcion;

public class RepositorioSuscripcionesParaAdopciones {
  private static RepositorioSuscripcionesParaAdopciones repo = new RepositorioSuscripcionesParaAdopciones();
  private List<SuscripcionParaAdopcion> suscripciones = new ArrayList<>();
  private List<SuscripcionParaAdopcion> suscripcionesDeBaja = new ArrayList<>();

  public void agregar(SuscripcionParaAdopcion publicacion) {
    suscripciones.add(publicacion);
  }

  public void darDeBaja(SuscripcionParaAdopcion publicacion) {
    suscripciones.remove(publicacion);
    suscripcionesDeBaja.add(publicacion);
  }

  // el repositorio, en codigo de produccion, lo inyectamos por constructor
  // usamos el constructor solo para tests
  public RepositorioSuscripcionesParaAdopciones() {}

  // usamos el getInstance en Main
  public static RepositorioSuscripcionesParaAdopciones getInstance() {
    return repo;
  }

  public List<SuscripcionParaAdopcion> getSuscripciones() {
    return suscripciones;
  }

  public List<SuscripcionParaAdopcion> getSuscripcionesDeBaja() {
    return suscripcionesDeBaja;
  }
}
