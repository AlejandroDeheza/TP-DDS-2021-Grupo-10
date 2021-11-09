package utils;

import java.util.List;
import javax.persistence.TypedQuery;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;

public class CascadeTypeCheck extends NuestraAbstractPersistenceTest {
  Object parent;
  Object child;
  private Class<?> parentClass;
  private Class<?> childClass;
  String parentClause;
  String childClause;
  private TypedQuery<?> parentQuery;
  private TypedQuery<?> childQuery;

  public CascadeTypeCheck(Object parent) {
    this.parent = parent;
    this.parentClass = this.parent.getClass();
    this.parentClause = "from ".concat(parentClass.getSimpleName());
    this.parentQuery = entityManager().createQuery(parentClause, parentClass);
  }
  
  private void setChildProperties(Object child) {
    this.child = child;
    this.childClass = this.child.getClass();
    this.childClause = "from ".concat(childClass.getSimpleName());
    this.childQuery = entityManager().createQuery(childClause, childClass);
  }

  private boolean validarQueCumplaCascadeType(Object child, Integer afterPersistParentExpected,
      Integer afterPersistChildExpected, Integer afterRemoveParentExpected, Integer afterRemoveChildExpected) {
    setChildProperties(child);

    entityManager().persist(this.parent);
    boolean afterPersistParentValue = afterPersistParentExpected == this.parentQuery.getResultList().size();
    boolean afterPersistChildValue = afterPersistChildExpected == this.childQuery.getResultList().size();

    entityManager().remove(this.parent);
    boolean afterRemoveParentValue = afterRemoveParentExpected == this.parentQuery.getResultList().size();
    boolean afterRemoveChildValue = afterRemoveChildExpected == this.childQuery.getResultList().size();

    return afterPersistParentValue && afterPersistChildValue && afterRemoveParentValue && afterRemoveChildValue;
  }

  public boolean contemplaElCascadeType(Object child, Integer afterPersistParentExpected, Integer afterPersistChildExpected,
      Integer afterRemoveParentExpected, Integer afterRemoveChildExpected) {
    return validarQueCumplaCascadeType(
        child,
        afterPersistParentExpected,
        afterPersistChildExpected,
        afterRemoveParentExpected,
        afterRemoveChildExpected
    );
  }

  public boolean contemplaElCascadeType(List<?> child, Integer afterPersistParentExpected, Integer afterPersistChildExpected,
      Integer afterRemoveParentExpected, Integer afterRemoveChildExpected) {
    return validarQueCumplaCascadeType(
        child.get(0),
        afterPersistParentExpected,
        afterPersistChildExpected,
        afterRemoveParentExpected,
        afterRemoveChildExpected
    );
  }
}
