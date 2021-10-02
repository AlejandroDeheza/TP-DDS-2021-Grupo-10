package repositorios;

import modelo.publicacion.DarEnAdopcion;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioDarEnAdopcion implements WithGlobalEntityManager  {

  public void agregar(DarEnAdopcion publicacion) {
    entityManager().persist(publicacion);
  }

  public void actualizar(DarEnAdopcion publicacion) {
    Query query = entityManager().createQuery("UPDATE FROM DarEnAdopcion SET s.estaActiva=false WHERE id=:id"); //TODO Investigar xq rompe con merge.
    query.setParameter("id",publicacion.getId()).executeUpdate();
  }

  public List<DarEnAdopcion> getPublicaciones() {
    return entityManager()
        .createQuery("from DarEnAdopcion", DarEnAdopcion.class)
        .getResultList().stream()
        .filter(DarEnAdopcion::estaActiva).collect(Collectors.toList());
  }

  public List<DarEnAdopcion> getProcesadas() {
    return entityManager()
        .createQuery("from DarEnAdopcion", DarEnAdopcion.class)
        .getResultList().stream()
        .filter(p -> !p.estaActiva()).collect(Collectors.toList());
  }
}
