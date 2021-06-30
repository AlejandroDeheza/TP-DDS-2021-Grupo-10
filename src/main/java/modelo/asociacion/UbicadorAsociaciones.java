package modelo.asociacion;

import modelo.informe.Ubicacion;

import java.util.*;
import java.util.stream.Collectors;

public class UbicadorAsociaciones {
  private RepositorioAsociaciones repositorioAsociaciones;

  public UbicadorAsociaciones(RepositorioAsociaciones repositorioAsociaciones) {
    this.repositorioAsociaciones = repositorioAsociaciones;
  }

  public Asociacion getAsociacionMasCercana(Ubicacion ubicacion) {
    Double minimaDistancia = repositorioAsociaciones
                          .getAsociaciones().stream()
                          .map(asociacion -> asociacion.getUbicacion().getDistancia(ubicacion))
                          .mapToDouble(Double::doubleValue).min().getAsDouble();
    List<Asociacion> asociacions =repositorioAsociaciones.getAsociaciones().stream().filter(asociacion -> asociacion.getUbicacion().getDistancia(ubicacion).equals(minimaDistancia)).collect(Collectors.toList());
    Asociacion asociacionMasCercana = asociacions.stream().findFirst().orElse(null);
    return asociacionMasCercana;
  }
}
