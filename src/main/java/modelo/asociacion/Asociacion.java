package modelo.asociacion;

import modelo.EntidadPersistente;
import modelo.informe.Ubicacion;
import modelo.pregunta.ParDePreguntas;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Asociacion extends EntidadPersistente {

  private String nombre;

  @Embedded
  private Ubicacion ubicacion;

  @OneToMany
  @JoinColumn(name = "asociacion_id")
  @Cascade(CascadeType.ALL)
  private List<ParDePreguntas> preguntas = new ArrayList<>();

  // para hibernate
  private Asociacion() {

  }

  public Asociacion(String nombre, Ubicacion ubicacion) {
    this.nombre = nombre;
    this.ubicacion = ubicacion;
  }

  public void agregarPregunta(ParDePreguntas pregunta) {
    preguntas.add(pregunta);
  }

  public void eliminarPregunta(ParDePreguntas pregunta) {
    preguntas.remove(pregunta);
  }

  public String getNombre() {
    return nombre;
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }

  public List<ParDePreguntas> getPreguntas() {
    return preguntas;
  }
  
  public List<ParDePreguntas> getPreguntasObligatorias() {
    return preguntas.stream().filter(p -> p.getEsObligatoria().equals(true)).collect(Collectors.toList());
  }
  
  public List<ParDePreguntas> getPreguntasNoObligatorias() {
    return preguntas.stream().filter(p -> p.getEsObligatoria().equals(false)).collect(Collectors.toList());
  }

}
