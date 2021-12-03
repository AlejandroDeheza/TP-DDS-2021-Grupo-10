package repositorios;

import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioCaracteristicas extends Repositorio<Caracteristica> {

  public RepositorioCaracteristicas() {
    super(Caracteristica.class);
  }

  public Caracteristica getCaracteristicaSegun(String nombre, String valor) {
    return entityManager()
        .createQuery("from Caracteristica c WHERE c.nombreCaracteristica = :nombre " +
            "AND c.valorCaracteristica = :valor", Caracteristica.class)
        .setParameter("nombre", nombre)
        .setParameter("valor", valor)
        .getSingleResult();
  }

  public void agregarCaracteristicasConValoresPosibles(CaracteristicaConValoresPosibles caracteristica) {
    caracteristica.listarCaracteristicas().forEach(this::agregar);
  }

  public void eliminarCaracteristicasConValoresPosibles(CaracteristicaConValoresPosibles caracteristica) {
    entityManager()
        .createQuery("DELETE FROM Caracteristica c WHERE c.nombreCaracteristica = :nombre")
        .setParameter("nombre", caracteristica.getNombreCaracteristica())
        .executeUpdate();
  }

  public List<CaracteristicaConValoresPosibles> getCaracteristicasConValoresPosibles() {
    List<CaracteristicaConValoresPosibles> lista = new ArrayList<>();

    listarTodos().stream()
        .collect(Collectors.groupingBy(Caracteristica::getNombreCaracteristica))
        .forEach((n, c) -> lista.add(
            new CaracteristicaConValoresPosibles(
                n, c.stream().map(Caracteristica::getValorCaracteristica).collect(Collectors.toList())
            )
        ));

    return lista;
  }
}