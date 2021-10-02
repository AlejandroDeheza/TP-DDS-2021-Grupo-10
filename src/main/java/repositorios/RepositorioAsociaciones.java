package repositorios;

import modelo.asociacion.Asociacion;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;

public class RepositorioAsociaciones implements WithGlobalEntityManager {

  public void agregarAsociacion(Asociacion asociacion) {
    entityManager().persist(asociacion);
  }

  public List<Asociacion> getAsociaciones() {
    return entityManager()
        .createQuery("from Asociacion", Asociacion.class)
        .getResultList();
  }

  public void eliminarAsociaciones(Asociacion asociacion) {
    entityManager().remove(asociacion);
  }
}
