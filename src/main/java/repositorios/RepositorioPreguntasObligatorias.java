package repositorios;

import modelo.pregunta.ParDePreguntas;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;

public class RepositorioPreguntasObligatorias implements WithGlobalEntityManager  {

  public List<ParDePreguntas> getPreguntas() {
    return entityManager()
        .createQuery("from ParDePreguntas", ParDePreguntas.class)
        .getResultList();
  }

}
