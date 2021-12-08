package modelo.pregunta;

import modelo.EntidadPersistente;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "par_de_preguntas" )
public class ParDePreguntas extends EntidadPersistente {

  private String concepto;

  private String preguntaDelDador;

  private String preguntaDelAdoptante;

  private Boolean esObligatoria;

  @ElementCollection
  private List<String> respuestasPosiblesDelDador = new ArrayList<>();

  @ElementCollection
  private List<String> respuestasPosiblesDelAdoptante = new ArrayList<>();

  @ElementCollection
  private List<ParDeRespuestas> paresDeRespuestas = new ArrayList<>();

  // para hibernate
  private ParDePreguntas() {

  }

  public ParDePreguntas(String concepto, String preguntaDelDador, String preguntaDelAdoptante, Boolean esObligatoria,
                        List<String> respuestasPosiblesDelDador, List<String> respuestasPosiblesDelAdoptante,
                        List<ParDeRespuestas> paresDeRespuestas) {
    this.concepto = concepto;
    this.preguntaDelDador = preguntaDelDador;
    this.preguntaDelAdoptante = preguntaDelAdoptante;
    this.esObligatoria = esObligatoria;
    this.respuestasPosiblesDelDador = respuestasPosiblesDelDador;
    this.respuestasPosiblesDelAdoptante = respuestasPosiblesDelAdoptante;
    this.paresDeRespuestas = paresDeRespuestas;
  }

  public Boolean esIgualA(ParDePreguntas parDePreguntas) {
    return parDePreguntas.getPreguntaDelAdoptante().equals(preguntaDelAdoptante)
        && parDePreguntas.getPreguntaDelDador().equals(preguntaDelDador);
  }

  public String getConcepto() {
    return concepto;
  }

  public String getPreguntaDelDador() {
    return preguntaDelDador;
  }

  public String getPreguntaDelAdoptante() {
    return preguntaDelAdoptante;
  }

  public List<ParDeRespuestas> getParesDeRespuestas() {
    return paresDeRespuestas;
  }

  public List<String> getRespuestasPosiblesDelDador() {
    return respuestasPosiblesDelDador;
  }

  public List<String> getRespuestasPosiblesDelAdoptante() {
    return respuestasPosiblesDelAdoptante;
  }

  public Boolean getEsObligatoria() {
    return esObligatoria;
  }

}
