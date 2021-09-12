package modelo.pregunta;

public class RespuestaDelAdoptante {

  private String respuesta;
  private ParDePreguntas parDePreguntas;

  public RespuestaDelAdoptante(String respuesta, ParDePreguntas parDePreguntas) {
    this.respuesta = respuesta;
    this.parDePreguntas = parDePreguntas;
  }

  public String getRespuesta() {
    return respuesta;
  }

  public ParDePreguntas getParDePreguntas() {
    return parDePreguntas;
  }
}
