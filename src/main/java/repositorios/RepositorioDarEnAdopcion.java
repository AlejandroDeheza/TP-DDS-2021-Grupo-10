package repositorios;

import modelo.publicacion.DarEnAdopcion;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioDarEnAdopcion extends Repositorio<DarEnAdopcion>  {

  public RepositorioDarEnAdopcion() {
    super(DarEnAdopcion.class);
  }

  public List<DarEnAdopcion> getPublicacionesActivas() {
    return listarTodos().stream()
        .filter(DarEnAdopcion::estaActiva).collect(Collectors.toList());
  }

  public List<DarEnAdopcion> getPublicacionesProcesadas() {
    return listarTodos().stream()
        .filter(p -> !p.estaActiva()).collect(Collectors.toList());
  }
}
