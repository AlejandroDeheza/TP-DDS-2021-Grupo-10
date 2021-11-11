package repositorios;

import modelo.pregunta.ParDePreguntas;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioPreguntasObligatorias extends Repositorio implements WithGlobalEntityManager {

  public List<ParDePreguntas> getPreguntasObligatorias() {
    return entityManager()
        .createQuery("from ParDePreguntas", ParDePreguntas.class)
        .getResultList().stream()
        .filter(parDePreguntas -> parDePreguntas.getEsObligatoria()).collect(Collectors.toList());
  }

}
