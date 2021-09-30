package repositorios;

import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioCaracteristicasConValoresPosibles implements WithGlobalEntityManager {

  public void agregarCaracteristica(CaracteristicaConValoresPosibles caracteristica) {
    caracteristica.listarCaracteristicas().forEach(c -> entityManager().persist(c));
  }

  public void eliminarCaracteristica(CaracteristicaConValoresPosibles caracteristica) {
    caracteristica.listarCaracteristicas().forEach(c -> entityManager().remove(c));
  }

  public List<CaracteristicaConValoresPosibles> getCaracteristicas() {
    List<CaracteristicaConValoresPosibles> lista = new ArrayList<>();

    entityManager()
        .createQuery("from Caracteristica", Caracteristica.class)
        .getResultList().stream()
        .collect(Collectors.groupingBy(Caracteristica::getNombreCaracteristica))
        .forEach((n, c) -> lista.add(
            new CaracteristicaConValoresPosibles(
                n, c.stream().map(Caracteristica::getValorCaracteristica).collect(Collectors.toList())
            )
        ));

    return lista;
  }
}