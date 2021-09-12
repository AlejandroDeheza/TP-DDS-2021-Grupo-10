package modelo.pregunta;

import java.util.List;
import java.util.stream.Collectors;

public class RespuestaDelDador {
  private String respuesta;
  private ParDePreguntas parDePreguntas;

  public RespuestaDelDador(String respuesta, ParDePreguntas parDePreguntas) {
    this.respuesta = respuesta;
    this.parDePreguntas = parDePreguntas;
  }

  public Boolean correspondeConAlguna(List<RespuestaDelAdoptante> comodidades) {
    return comodidades.stream()
        .filter(respuestaDelAdoptante -> respuestaDelAdoptante.getParDePreguntas().esIgualA(parDePreguntas))
        .anyMatch(respuestaDelAdoptante -> this.correspondeCon(respuestaDelAdoptante.getRespuesta()));
  }

  private Boolean correspondeCon(String respuestaDelAdoptante) {
    return parDePreguntas.getParesDeRespuestas().stream()
        .filter(parDeRespuestas -> parDeRespuestas.getRespuestaDelDador().equals(respuesta))
        .map(ParDeRespuestas::getRespuestaDelAdoptante)
        .collect(Collectors.toList())
        .contains(respuestaDelAdoptante);
  }

  public String getRespuesta() {
    return respuesta;
  }

  public ParDePreguntas getParDePreguntas() {
    return parDePreguntas;
  }

}
