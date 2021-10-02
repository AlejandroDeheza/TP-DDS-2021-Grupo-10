package repositorios;

import modelo.suscripcion.SuscripcionParaAdopcion;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioSuscripcionesParaAdopciones implements WithGlobalEntityManager  {

  public void agregar(SuscripcionParaAdopcion publicacion) {
    entityManager().persist(publicacion);
  }

  public void darDeBaja(SuscripcionParaAdopcion publicacion) {
    publicacion.desactivar();
    Query query = entityManager().createQuery("UPDATE FROM SuscripcionParaAdopcion s SET s.estaActiva=false WHERE id=:id");
    query.setParameter("id",publicacion.getId()).executeUpdate();
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
