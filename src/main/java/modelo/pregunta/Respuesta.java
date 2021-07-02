package modelo.pregunta;


public class Respuesta {
  private String respuesta;
  private ParDePreguntas parDePreguntas;

  public Respuesta(String respuesta, ParDePreguntas parDePreguntas) {
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
