package repositorios;

import modelo.asociacion.Asociacion;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;

public class RepositorioAsociaciones implements WithGlobalEntityManager {
  public static RepositorioAsociaciones instancia = new RepositorioAsociaciones();

  public List<Asociacion> getAsociaciones() {
    return entityManager()
        .createQuery("from Asociacion", Asociacion.class)
        .getResultList();
  }
}
