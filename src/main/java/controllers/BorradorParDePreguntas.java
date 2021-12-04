package controllers;

import java.util.ArrayList;
import java.util.List;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.ParDeRespuestas;

public class BorradorParDePreguntas {
  private String preguntaDelDador;
  private String preguntaDelAdoptante;
  private Boolean esObligatoria;
  private Long asociacionId;
  private List<ParDeRespuestas> paresDeRespuestas = new ArrayList<>();
  private List<String> respuestasPosiblesDelDador = new ArrayList<>();
  private List<String> respuestasPosiblesDelAdoptante = new ArrayList<>();

  public BorradorParDePreguntas setPreguntas(String preguntaDelDador, String preguntaDelAdoptante, Boolean esObligatoria) {
    this.preguntaDelDador = preguntaDelDador;
    this.preguntaDelAdoptante = preguntaDelAdoptante;
    this.esObligatoria = esObligatoria;
    return this;
  }
  
  public BorradorParDePreguntas setAsociacionId(Long asociacionId) {
    this.asociacionId = asociacionId;
    return this;
  }

  public void agregarParDeRespuestas(ParDeRespuestas parDeRespuestas) {
    this.paresDeRespuestas.add(parDeRespuestas);
  }

  public void agregarRespuestaPosibleDador(String respuestaPosibleDador) {
    this.respuestasPosiblesDelDador.add(respuestaPosibleDador);
  }

  public void agregarRespuestaPosibleAdoptante(String respuestaPosibleAdoptante) {
    this.respuestasPosiblesDelAdoptante.add(respuestaPosibleAdoptante);
  }

  public ParDePreguntas crearParDePreguntas() {
    ParDePreguntas parDePreguntas = new ParDePreguntas(
        this.preguntaDelDador, this.preguntaDelAdoptante, this.esObligatoria
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

  public Boolean getEsObligatoria() {
    return this.esObligatoria;
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
