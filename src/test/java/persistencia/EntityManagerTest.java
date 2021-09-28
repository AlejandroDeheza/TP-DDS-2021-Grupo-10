package persistencia;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class EntityManagerTest extends NuestraAbstractPersistenceTest {

  @Test
  public void contextUp() {
    assertNotNull(entityManager());
  }

  @Test
  public void contextUpWithTransaction() {
    assertDoesNotThrow(() -> withTransaction(() -> {}));
  }
}
