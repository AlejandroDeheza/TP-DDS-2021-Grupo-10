package modelo.pregunta;


public class Respuesta {
  private String Respuesta;
  private ParDePreguntas parDePreguntas;

  public Respuesta(String respuesta, ParDePreguntas parDePreguntas) {
    Respuesta = respuesta;
    this.parDePreguntas = parDePreguntas;
  }

  public String getRespuesta() {
    return Respuesta;
  }

  public ParDePreguntas getParDePreguntas() {
    return parDePreguntas;
  }
}
