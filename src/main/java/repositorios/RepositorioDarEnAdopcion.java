package repositorios;

import modelo.publicacion.DarEnAdopcion;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioDarEnAdopcion implements WithGlobalEntityManager  {

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
