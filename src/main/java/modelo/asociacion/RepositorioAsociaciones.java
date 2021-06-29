package modelo.asociacion;

import modelo.pregunta.ParDePreguntas;

import java.util.ArrayList;
import java.util.List;

public class RepositorioAsociaciones {
  private List<Asociacion> asociaciones = new ArrayList<>();
  private static RepositorioAsociaciones repositorioAsociaciones = new RepositorioAsociaciones();

  //el repositorio, en codigo de produccion, lo inyectamos por constructor
  //usamos el constructor solo para tests
  public RepositorioAsociaciones() {
  }

  //usamos el getInstance en Main
  public static RepositorioAsociaciones getInstance() {
    return repositorioAsociaciones;
  }

  public List<Asociacion> getAsociaciones() {
    return asociaciones;
  }


  public void agregarAsociaciones(Asociacion asociacion) {
    asociaciones.add(asociacion);
  }

  public void eliminarAsociaciones(Asociacion asociacion) {
    asociaciones.remove(asociacion);
  }
}
