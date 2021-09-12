package modelo.asociacion;

import modelo.informe.Ubicacion;
import modelo.pregunta.ParDePreguntas;

import java.util.ArrayList;
import java.util.List;

public class Asociacion {
  private Ubicacion ubicacion;
  private List<ParDePreguntas> preguntas = new ArrayList<>();

  public Asociacion(Ubicacion ubicacion) {
    this.ubicacion = ubicacion;
  }

  public void agregarPregunta(ParDePreguntas pregunta) {
    preguntas.add(pregunta);
  }

  public void eliminarPregunta(ParDePreguntas pregunta) {
    preguntas.remove(pregunta);
  }


  public Ubicacion getUbicacion() {
    return ubicacion;
  }

  public List<ParDePreguntas> getPreguntas() {
    return preguntas;
  }

}
