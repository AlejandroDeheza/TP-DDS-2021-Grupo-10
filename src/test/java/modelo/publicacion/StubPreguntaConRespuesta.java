package modelo.publicacion;

public class StubPreguntaConRespuesta implements PreguntaConRespuesta {

  private String pregunta; // Suponemos que siempre hay una pregunta y no nunca será null
  private String respuesta;

  public StubPreguntaConRespuesta(String pregunta, String respuesta) {
    this.pregunta = pregunta;
    this.respuesta = respuesta;
  }

  public String getPregunta() {
    return this.pregunta;
  }

  public String getRespuesta() {
    return this.respuesta;
  }

  @Override
  public boolean esPreguntaSinRespuesta() {
    return this.respuesta == null;
  }

}
