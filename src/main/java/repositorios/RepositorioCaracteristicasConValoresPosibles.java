package repositorios;

import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioCaracteristicas implements WithGlobalEntityManager {

  public void agregarCaracteristica(CaracteristicaConValoresPosibles caracteristica) {
    entityManager().persist(caracteristica);
  }

  public void eliminarCaracteristica(CaracteristicaConValoresPosibles caracteristica) {
    entityManager().remove(caracteristica);
  }

  /*public List<CaracteristicaConValoresPosibles> getCaracteristicas() {
    return entityManager()
        .createQuery("from Caracteristica", CaracteristicaConValoresPosibles.class)
        .getResultList();
  }*/
  // TODO, revisar si este repo sirve para algo
  // si sirve para algo, arreglar el metodo de arriba
}