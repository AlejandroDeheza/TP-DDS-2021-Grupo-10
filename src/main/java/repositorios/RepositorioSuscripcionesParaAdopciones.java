package repositorios;

import modelo.suscripcion.SuscripcionParaAdopcion;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioSuscripcionesParaAdopciones extends Repositorio<SuscripcionParaAdopcion> {

  public RepositorioSuscripcionesParaAdopciones() {
    super(SuscripcionParaAdopcion.class);
  }

  public List<SuscripcionParaAdopcion> getSuscripcionesActivas() {
    return listarTodos().stream()
        .filter(SuscripcionParaAdopcion::estaActiva).collect(Collectors.toList());
  }

  public List<SuscripcionParaAdopcion> getSuscripcionesDeBaja() {
    return listarTodos().stream()
        .filter(s -> !s.estaActiva()).collect(Collectors.toList());
  }
}
