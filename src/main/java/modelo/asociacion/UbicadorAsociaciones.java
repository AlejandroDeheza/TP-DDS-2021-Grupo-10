package modelo.asociacion;

import modelo.informe.Ubicacion;
import repositorios.RepositorioAsociaciones;

public class UbicadorAsociaciones {
  private RepositorioAsociaciones repositorioAsociaciones = RepositorioAsociaciones.instancia;

  public UbicadorAsociaciones() {
  }

  public Asociacion getAsociacionMasCercana(Ubicacion ubicacion) {
    Double minimaDistancia = repositorioAsociaciones.getAsociaciones().stream()
        .map(asociacion -> asociacion.getUbicacion().getDistancia(ubicacion))
        .mapToDouble(Double::doubleValue).min().getAsDouble();
    return repositorioAsociaciones.getAsociaciones().stream()
        .filter(asociacion -> asociacion.getUbicacion().getDistancia(ubicacion).equals(minimaDistancia))
        .findFirst().orElse(null);
  }
}
