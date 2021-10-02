package repositorios;

import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioCaracteristicas implements WithGlobalEntityManager {

  public void agregarCaracteristica(Caracteristica caracteristica) {
    entityManager().persist(caracteristica);
  }

  public void eliminarCaracteristica(Caracteristica caracteristica) {
    entityManager().remove(caracteristica);
  }

  public List<Caracteristica> getCaracteristicas() {
    return entityManager()
        .createQuery("from Caracteristica", Caracteristica.class)
        .getResultList();
  }

  public void agregarCaracteristicasConValoresPosibles(CaracteristicaConValoresPosibles caracteristica) {
    caracteristica.listarCaracteristicas().forEach(c -> entityManager().persist(c));
  }

  public void eliminarCaracteristicasConValoresPosibles(CaracteristicaConValoresPosibles caracteristica) {
    String nombre = caracteristica.getNombreCaracteristica();
    Query query = entityManager().createQuery("DELETE FROM Caracteristica c WHERE c.nombreCaracteristica = :nombre");
    query.setParameter("nombre",nombre).executeUpdate();
  }

  public List<CaracteristicaConValoresPosibles> getCaracteristicasConValoresPosibles() {
    List<CaracteristicaConValoresPosibles> lista = new ArrayList<>();

    entityManager()
        .createQuery("from Caracteristica", Caracteristica.class)
        .getResultList()
        .stream()
        .collect(Collectors.groupingBy(Caracteristica::getNombreCaracteristica))
        .forEach((n, c) -> lista.add(
            new CaracteristicaConValoresPosibles(
                n, c.stream().map(Caracteristica::getValorCaracteristica).collect(Collectors.toList())
            )
        ));

    return lista;
  }
}