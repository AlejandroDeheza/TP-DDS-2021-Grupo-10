package modelo.pregunta;

import javax.persistence.Embeddable;

@Embeddable
public class ParDeRespuestas {

  private String respuestaDelDador;
  private String respuestaDelAdoptante;

  private ParDeRespuestas() {

  }

  public ParDeRespuestas(String respuestaDelDador, String respuestaDelAdoptante) {
    this.respuestaDelDador = respuestaDelDador;
    this.respuestaDelAdoptante = respuestaDelAdoptante;
  }

  public String getRespuestaDelDador() {
    return respuestaDelDador;
  }

  public String getRespuestaDelAdoptante() {
    return respuestaDelAdoptante;
  }

}
