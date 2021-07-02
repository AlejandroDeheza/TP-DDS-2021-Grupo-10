package modelo.pregunta;

import java.util.ArrayList;
import java.util.List;

public class ParDePreguntas {
  private String preguntaDelDador;
  private String preguntaDelAdoptante;
  List<ParDeRespuestas> paresDeRespuestas = new ArrayList<>();

  public ParDePreguntas(String preguntaDelDador, String preguntaDelAdoptante) {
    this.preguntaDelDador = preguntaDelDador;
    this.preguntaDelAdoptante = preguntaDelAdoptante;
  }

  public Boolean esIgualA(ParDePreguntas parDePreguntas) {
    return parDePreguntas.getPreguntaDelAdoptante().equals(preguntaDelAdoptante)
        && parDePreguntas.getPreguntaDelDador().equals(preguntaDelDador);
  }

  public void agregarRespuesta(ParDeRespuestas parDeRespuestas) {
    paresDeRespuestas.add(parDeRespuestas);
  }

  public String getPreguntaDelDador() {
    return preguntaDelDador;
  }

  public String getPreguntaDelAdoptante() {
    return preguntaDelAdoptante;
  }

  public List<ParDeRespuestas> getParesDeRespuestas() {
    return paresDeRespuestas;
  }
}
