package repositorios;

import modelo.asociacion.Asociacion;
import modelo.informe.Ubicacion;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioAsociaciones extends Repositorio implements WithGlobalEntityManager {

  public List<Asociacion> getAsociaciones() {
    return entityManager()
        .createQuery("from Asociacion", Asociacion.class)
        .getResultList();
  }

  public Asociacion buscarPorId(Long id) {
    return entityManager().find(Asociacion.class, id);
  }

  public Asociacion getAsociacionMasCercana(Ubicacion ubicacion) {
    List<Asociacion> asociaciones = this.getAsociaciones();

    Double minimaDistancia = asociaciones.stream()
        .map(asociacion -> asociacion.getUbicacion().getDistancia(ubicacion))
        .mapToDouble(Double::doubleValue).min().getAsDouble();
    return asociaciones.stream()
        .filter(asociacion -> asociacion.getUbicacion().getDistancia(ubicacion).equals(minimaDistancia))
        .findFirst().orElse(null);
  }
}
