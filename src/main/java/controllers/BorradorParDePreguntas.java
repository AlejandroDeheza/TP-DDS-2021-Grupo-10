package controllers;

import java.util.ArrayList;
import java.util.List;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.ParDeRespuestas;

public class BorradorParDePreguntas {
  private String preguntaDelDador;
  private String preguntaDelAdoptante;
  private Boolean esObligatoria = true;
  private Long asociacionId;
  private List<ParDeRespuestas> paresDeRespuestas;
  private List<String> respuestasPosiblesDelDador;
  private List<String> respuestasPosiblesDelAdoptante;

  public BorradorParDePreguntas setPreguntas(String preguntaDelDador, String preguntaDelAdoptante, Boolean esObligatoria) {
    this.preguntaDelDador = preguntaDelDador;
    this.preguntaDelAdoptante = preguntaDelAdoptante;
    this.esObligatoria = esObligatoria;
    this.paresDeRespuestas = new ArrayList<>();
    this.respuestasPosiblesDelDador = new ArrayList<>();
    this.respuestasPosiblesDelAdoptante = new ArrayList<>();
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

  public BorradorParDePreguntas setAsociacionId(Long asociacionId) {
    this.asociacionId = asociacionId;
    return this;
  }

  public ParDePreguntas crearParDePreguntas() {
    ParDePreguntas parDePreguntas = new ParDePreguntas(this.preguntaDelDador, this.preguntaDelAdoptante, this.esObligatoria);
    this.respuestasPosiblesDelDador.stream().forEach(respuestaPosibleDador -> {
      parDePreguntas.agregarRespuestaDador(respuestaPosibleDador);
    });
    this.respuestasPosiblesDelAdoptante.stream().forEach(respuestaPosibleAdoptante -> {
      parDePreguntas.agregarRespuestaAdoptante(respuestaPosibleAdoptante);
    });
    this.paresDeRespuestas.stream().forEach(parDeRespuestas -> {
      parDePreguntas.agregarRespuesta(parDeRespuestas);
    });
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
