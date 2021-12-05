package controllers;

import java.util.ArrayList;
import java.util.List;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.ParDeRespuestas;

public class BorradorParDePreguntas {
  private String preguntaDelDador;
  private String preguntaDelAdoptante;
  private Boolean esPreguntaObligatoria;
  private Long asociacionId;
  private List<String> respuestasPosiblesDelDador;
  private List<String> respuestasPosiblesDelAdoptante;

  private List<ParDeRespuestas> paresDeRespuestas = new ArrayList<>();

  public BorradorParDePreguntas(Long asociacionId, String preguntaDelDador, String preguntaDelAdoptante,
                                Boolean esPreguntaObligatoria, List<String> respuestasPosiblesDelDador,
                                List<String> respuestasPosiblesDelAdoptante) {
    this.asociacionId = asociacionId;
    this.preguntaDelDador = preguntaDelDador;
    this.preguntaDelAdoptante = preguntaDelAdoptante;
    this.esPreguntaObligatoria = esPreguntaObligatoria;
    this.respuestasPosiblesDelDador = respuestasPosiblesDelDador;
    this.respuestasPosiblesDelAdoptante = respuestasPosiblesDelAdoptante;
  }

  public void agregarParesDeRespuestas(List<ParDeRespuestas> paresDeRespuestas) {
    this.paresDeRespuestas = paresDeRespuestas;
  }

  public ParDePreguntas crearParDePreguntas() {
    ParDePreguntas parDePreguntas = new ParDePreguntas(
        this.preguntaDelDador, this.preguntaDelAdoptante, this.esPreguntaObligatoria
    );
    this.respuestasPosiblesDelDador.forEach(parDePreguntas::agregarRespuestaPosibleDelDador);
    this.respuestasPosiblesDelAdoptante.forEach(parDePreguntas::agregarRespuestaPosibleDelAdoptante);
    this.paresDeRespuestas.forEach(parDePreguntas::agregarRespuestasQueMachean);
    return parDePreguntas;
  }

  public String getPreguntaDelDador() {
    return this.preguntaDelDador;
  }

  public String getPreguntaDelAdoptante() {
    return this.preguntaDelAdoptante;
  }

  public Boolean getEsPreguntaObligatoria() {
    return this.esPreguntaObligatoria;
  }

  public Long getAsociacionId() {
    return this.asociacionId;
  }

  public List<String> getRespuestasPosiblesDelDador() {
    return this.respuestasPosiblesDelDador;
  }

  public List<String> getRespuestasPosiblesDelAdoptante() {
    return this.respuestasPosiblesDelAdoptante;
  }
  
  public List<ParDeRespuestas> getParesDeRespuestasPosibles() {
    return this.paresDeRespuestas;
  }
}
