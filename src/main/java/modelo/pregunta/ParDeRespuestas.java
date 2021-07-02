package modelo.pregunta;

public class ParDeRespuestas {
  private String respuestaDelDador;
  private String respuestaDelAdoptante;

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
