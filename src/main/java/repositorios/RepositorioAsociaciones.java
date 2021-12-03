package repositorios;

import modelo.asociacion.Asociacion;
import modelo.informe.Ubicacion;
import java.util.List;

public class RepositorioAsociaciones extends Repositorio<Asociacion> {

  public RepositorioAsociaciones() {
    super(Asociacion.class);
  }

  public Asociacion getAsociacionMasCercana(Ubicacion ubicacion) {
    List<Asociacion> asociaciones = super.listarTodos();

    Double minimaDistancia = asociaciones.stream()
        .map(asociacion -> asociacion.getUbicacion().getDistancia(ubicacion))
        .mapToDouble(Double::doubleValue).min().getAsDouble();

    return asociaciones.stream()
        .filter(asociacion -> asociacion.getUbicacion().getDistancia(ubicacion).equals(minimaDistancia))
        .findFirst().orElse(null);
  }
}
