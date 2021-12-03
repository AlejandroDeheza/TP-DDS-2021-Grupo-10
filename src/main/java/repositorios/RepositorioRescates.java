package repositorios;

import modelo.publicacion.Rescate;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioRescates extends Repositorio<Rescate> {

  public RepositorioRescates() {
    super(Rescate.class);
  }

  public List<Rescate> getRescatesActivos() {
    return listarTodos().stream()
        .filter(Rescate::estaActiva).collect(Collectors.toList());
  }

  public List<Rescate> getRescatesProcesados() {
    return listarTodos().stream()
        .filter(p -> !p.estaActiva()).collect(Collectors.toList());
  }
}
