package repositorios;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public abstract class Repositorio implements WithGlobalEntityManager {

  public <T> void agregar(T instancia) {
    entityManager().persist(instancia);
  }
}
