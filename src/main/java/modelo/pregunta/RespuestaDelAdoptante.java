package modelo.pregunta;

import modelo.EntidadPersistente;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "respuesta_del_adoptante")
public class RespuestaDelAdoptante extends EntidadPersistente {

  private String respuesta;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private ParDePreguntas parDePreguntas;

  // para hibernate
  private RespuestaDelAdoptante() {

  }

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
