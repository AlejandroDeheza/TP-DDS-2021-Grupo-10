package repositorios;

import modelo.suscripcion.SuscripcionParaAdopcion;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioSuscripcionesParaAdopciones implements WithGlobalEntityManager  {

  public void agregar(SuscripcionParaAdopcion publicacion) {
    entityManager().persist(publicacion);
  }

  public void darDeBaja(SuscripcionParaAdopcion publicacion) {
    entityManager().remove(publicacion);
    entityManager().persist(publicacion);
  }

  public List<SuscripcionParaAdopcion> getSuscripciones() {
    return entityManager()
        .createQuery("from SuscripcionParaAdopcion", SuscripcionParaAdopcion.class)
        .getResultList().stream()
        .filter(SuscripcionParaAdopcion::estaActiva).collect(Collectors.toList());
  }

  public List<SuscripcionParaAdopcion> getSuscripcionesDeBaja() {
    return entityManager()
        .createQuery("from SuscripcionParaAdopcion", SuscripcionParaAdopcion.class)
        .getResultList().stream()
        .filter(s -> !s.estaActiva()).collect(Collectors.toList());
  }
}
