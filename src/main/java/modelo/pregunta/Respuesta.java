package modelo.pregunta;

import java.util.List;
import java.util.stream.Collectors;

public class Respuesta {
  private String respuesta;
  private ParDePreguntas parDePreguntas;

  public Respuesta(String respuesta, ParDePreguntas parDePreguntas) {
    this.respuesta = respuesta;
    this.parDePreguntas = parDePreguntas;
  }

  public boolean matcheaConAlguna(List<Respuesta> respuestasDelDador) {
    return respuestasDelDador
        .stream()
        .filter(r -> r.getParDePreguntas().esIgualA(parDePreguntas))
        .anyMatch(r -> r.getRespuestasDelAdoptanteRelacionadas().contains(respuesta));
  }

  private List<String> getRespuestasDelAdoptanteRelacionadas() {
    return parDePreguntas.getParesDeRespuestas()
        .stream()
        .filter(parDeRespuestas -> parDeRespuestas.getRespuestaDelDador().equals(respuesta))
        .map(ParDeRespuestas::getRespuestaDelAdoptante)
        .collect(Collectors.toList());
  }

  public String getRespuesta() {
    return respuesta;
  }

  public ParDePreguntas getParDePreguntas() {
    return parDePreguntas;
  }

}
