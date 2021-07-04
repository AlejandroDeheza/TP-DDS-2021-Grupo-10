package repositorios;

import java.util.ArrayList;
import java.util.List;

import modelo.publicacion.Rescate;

public class RepositorioRescates {
  private static RepositorioRescates repo = new RepositorioRescates();
  private List<Rescate> rescates = new ArrayList<>();
  private List<Rescate> procesados = new ArrayList<>();

  public void agregar(Rescate publicacion) {
    this.rescates.add(publicacion);
  }

  public void marcarComoProcesada(Rescate publicacion) {
    this.rescates.remove(publicacion);
    this.procesados.add(publicacion);
  }

  // el repositorio, en codigo de produccion, lo inyectamos por constructor
  // usamos el constructor solo para tests
  public RepositorioRescates() {}

  // usamos el getInstance en Main
  public static RepositorioRescates getInstance() {
    return repo;
  }

  public List<Rescate> getRescates() {
    return this.rescates;
  }

  public List<Rescate> getProcesados() {
    return this.procesados;
  }
}
