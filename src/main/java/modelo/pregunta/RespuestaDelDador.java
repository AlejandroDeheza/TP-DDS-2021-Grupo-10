package modelo.pregunta;

import modelo.EntidadPersistente;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "respuesta_del_dador")
public class RespuestaDelDador extends EntidadPersistente {

  private String respuesta;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //Correcto
  private ParDePreguntas parDePreguntas;

  // para hibernate
  private RespuestaDelDador() {

  }

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
