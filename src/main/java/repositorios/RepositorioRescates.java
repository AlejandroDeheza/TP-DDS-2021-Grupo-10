package repositorios;

import modelo.publicacion.Rescate;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioRescates implements WithGlobalEntityManager  {

  public List<Rescate> getRescates() {
    return entityManager()
        .createQuery("from Rescate", Rescate.class)
        .getResultList().stream()
        .filter(Rescate::estaActiva).collect(Collectors.toList());
  }

  public List<Rescate> getProcesados() {
    return entityManager()
        .createQuery("from Rescate", Rescate.class)
        .getResultList().stream()
        .filter(p -> !p.estaActiva()).collect(Collectors.toList());
  }
}
