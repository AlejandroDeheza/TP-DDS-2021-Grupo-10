package modelo.asociacion;

import modelo.EntitidadPersistente;
import modelo.informe.Ubicacion;
import modelo.pregunta.ParDePreguntas;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Asociacion extends EntitidadPersistente {
  private String nombre;
  @Embedded
  private Ubicacion ubicacion;

  @OneToMany
  @JoinColumn
  private List<ParDePreguntas> preguntas = new ArrayList<>();

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

}
