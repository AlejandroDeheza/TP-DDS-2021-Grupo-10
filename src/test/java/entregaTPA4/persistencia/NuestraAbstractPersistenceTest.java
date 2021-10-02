package entregaTPA4.persistencia;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public abstract class NuestraAbstractPersistenceTest extends AbstractPersistenceTest
    implements WithGlobalEntityManager {

  @BeforeEach
  public void setup() {
    this.beginTransaction();
  }

  @AfterEach
  public void tearDown() {
    this.rollbackTransaction();
  }
}
