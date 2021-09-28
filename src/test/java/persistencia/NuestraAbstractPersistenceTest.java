package persistencia;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public abstract class NuestraAbstractPersistenceTest implements TransactionalOps, EntityManagerOps, WithGlobalEntityManager {

  @BeforeEach
  public void setup() {
    this.beginTransaction();
  }

  @AfterEach
  public void tearDown() {
    this.rollbackTransaction();
  }
}
