package repositorios;

import modelo.pregunta.ParDePreguntas;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioParDePreguntas extends Repositorio<ParDePreguntas> {

  public RepositorioParDePreguntas() {
    super(ParDePreguntas.class);
  }

  public List<ParDePreguntas> getPreguntasObligatorias() {
    return listarTodos().stream()
        .filter(ParDePreguntas::getEsObligatoria)
        .collect(Collectors.toList());
  }

}
