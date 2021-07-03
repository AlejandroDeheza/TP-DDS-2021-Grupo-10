package repositorios;

import java.util.ArrayList;
import java.util.List;

import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;

public class RepositorioCaracteristicas {

  private static RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
  private List<CaracteristicaConValoresPosibles> caracteristicas = new ArrayList<>();

  public void agregarCaracteristica(CaracteristicaConValoresPosibles caracteristica) {
    caracteristicas.add(caracteristica);
  }

  public void eliminarCaracteristica(CaracteristicaConValoresPosibles caracteristica) {
    caracteristicas.remove(caracteristica);
  }

  public List<CaracteristicaConValoresPosibles> getCaracteristicas() {
    return caracteristicas;
  }

  //el repositorio, en codigo de produccion, lo inyectamos por constructor
  //usamos el constructor solo para tests
  public RepositorioCaracteristicas() {
  }
  //usamos el getInstance en Main
  public static RepositorioCaracteristicas getInstance() {
    return repositorioCaracteristicas;
  }
}