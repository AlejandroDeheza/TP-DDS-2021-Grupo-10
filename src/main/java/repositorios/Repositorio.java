package repositorios;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;

public abstract class Repositorio<T> implements WithGlobalEntityManager {

  private final Class<T> claseDeT;

  protected Repositorio(Class<T> claseDeT) {
    this.claseDeT = claseDeT;
  }


  public void agregar(T instancia) {
    entityManager().persist(instancia);
  }

  public void eliminar(T instancia) {
    entityManager().remove(instancia);
  }

  public T buscarPorId(Long id) {
    return entityManager().find(claseDeT, id);
  }

  public List<T> listarTodos() {
    return entityManager()
        .createQuery("from " + claseDeT.getSimpleName(), claseDeT)
        .getResultList();
  }
}
