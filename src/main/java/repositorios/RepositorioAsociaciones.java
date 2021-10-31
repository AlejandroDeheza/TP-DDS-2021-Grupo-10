package repositorios;

import modelo.asociacion.Asociacion;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;

public class RepositorioAsociaciones implements WithGlobalEntityManager {

  public List<Asociacion> getAsociaciones() {
    return entityManager()
        .createQuery("from Asociacion", Asociacion.class)
        .getResultList();
  }
}
