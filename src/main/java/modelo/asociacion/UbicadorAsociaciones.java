package modelo.asociacion;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import modelo.informe.Ubicacion;

import java.util.*;
import java.util.stream.Collectors;

public class UbicadorAsociaciones {
  private RepositorioAsociaciones repositorioAsociaciones = RepositorioAsociaciones.getInstance();

  public UbicadorAsociaciones() {
  }

  public UbicadorAsociaciones(RepositorioAsociaciones repositorioAsociaciones) {
    this.repositorioAsociaciones = repositorioAsociaciones;
  }


  public Asociacion getAsociacionMasCercana(Ubicacion ubicacion){
    List<Asociacion> asociacionesOrdenadasPorUbicacion = repositorioAsociaciones.getAsociaciones().stream().sorted().collect(Collectors.toList());

    return asociacionesOrdenadasPorUbicacion.get(0);
  }
}
