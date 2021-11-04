package utils;

import javax.persistence.TypedQuery;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;

public class CascadeTypeCheck extends NuestraAbstractPersistenceTest {
  public boolean contemplaElCascadeType(Object parent, Object child, Integer afterPersistParentExpected,
      Integer afterPersistChildExpected, Integer afterRemoveParentExpected, Integer afterRemoveChildExpected) {
    Class<?> parentClass = parent.getClass();
    Class<?> childClass = child.getClass();
    String parentQuery = "from ".concat(parentClass.getSimpleName());
    String childQuery = "from ".concat(childClass.getSimpleName());
    TypedQuery<?> parentClause = entityManager().createQuery(parentQuery, parentClass);
    TypedQuery<?> childClause = entityManager().createQuery(childQuery, childClass);

    entityManager().persist(parent);
    boolean afterPersistParentValue = afterPersistParentExpected == parentClause.getResultList().size();
    boolean afterPersistChildValue = afterPersistChildExpected == childClause.getResultList().size();

    entityManager().remove(parent);
    boolean afterRemoveParentValue = afterRemoveParentExpected == parentClause.getResultList().size();
    boolean afterRemoveChildValue = afterRemoveChildExpected == childClause.getResultList().size();

    return afterPersistParentValue && afterPersistChildValue && afterRemoveParentValue && afterRemoveChildValue;
  }
}
