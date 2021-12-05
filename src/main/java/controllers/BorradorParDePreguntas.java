package controllers;

import java.util.List;

public class BorradorParDePreguntas {
  private final String preguntaDelDador;
  private final String preguntaDelAdoptante;
  private final Boolean esPreguntaObligatoria;
  private final Long asociacionId;
  private final List<String> respuestasPosiblesDelDador;
  private final List<String> respuestasPosiblesDelAdoptante;

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

}
