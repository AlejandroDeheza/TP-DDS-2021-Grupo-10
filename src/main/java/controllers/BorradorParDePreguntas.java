package controllers;

import modelo.pregunta.ParDePreguntas;

public class BorradorParDePreguntas {
  private String preguntaDelDador;
  private String preguntaDelAdoptante;
  private Boolean esObligatoria = true;
  private Long asociacionId;

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

  public ParDePreguntas crearParDePreguntas() {
    return new ParDePreguntas(this.preguntaDelDador, this.preguntaDelAdoptante, this.esObligatoria);
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
}
