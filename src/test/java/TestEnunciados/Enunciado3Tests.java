package TestEnunciados;

import modelo.asociacion.Asociacion;
import modelo.asociacion.RepositorioAsociaciones;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.ParDeRespuestas;
import modelo.pregunta.RepositorioPreguntas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class Enunciado3Tests {
  RepositorioAsociaciones repositorioAsociaciones;
  RepositorioPreguntas repositorioPreguntas;
  ParDePreguntas parDePreguntas;
  Asociacion asociacion= new Asociacion(DummyData.getUbicacion());

  @BeforeEach
  public void loadContext() {
    repositorioAsociaciones = new RepositorioAsociaciones();
    repositorioPreguntas = new RepositorioPreguntas();
  }

  @Test
  @DisplayName("Se puede agregar una pregunta al RepositorioPreguntas")
  public void agregarParDePreguntasAlRepositorioPreguntas() {
    parDePreguntas = getParDePreguntas();
    assertEquals(0, repositorioPreguntas.getPreguntasObligatorias().size());
    repositorioPreguntas.agregarPregunta(parDePreguntas);
    assertEquals(1, repositorioPreguntas.getPreguntasObligatorias().size());
  }

  @Test
  @DisplayName("Se puede agregar un ParDePreguntas a la Asociacion")
  public void agregarParDePreguntasAAsociacion() {
    parDePreguntas = getParDePreguntas();
    assertEquals(0, asociacion.getPreguntas().size());
    asociacion.agregarPregunta(parDePreguntas);
    assertEquals(1, asociacion.getPreguntas().size());
  }

  @Test
  @DisplayName("Se puede agregar una Asociacion al RepositorioAsociaciones")
  public void agregarAsociacionAlRepositorioAsociaciones() {
    assertEquals(0, repositorioAsociaciones.getAsociaciones().size());
    repositorioAsociaciones.agregarAsociaciones(asociacion);
    assertEquals(1, repositorioAsociaciones.getAsociaciones().size());
  }

  private ParDePreguntas getParDePreguntas(){
    ParDePreguntas preguntas = new ParDePreguntas("La mascota sufre si está mucho tiempo sola?", "Va a estar la mascota mucho tiempo sola?");
    ParDeRespuestas respuesta1 = new ParDeRespuestas("Si", "No");
    ParDeRespuestas respuesta2 = new ParDeRespuestas("No", "Si");
    ParDeRespuestas respuesta3 = new ParDeRespuestas("Si", "Si");
    ParDeRespuestas respuesta4 = new ParDeRespuestas("No", "No");
    preguntas.agregarRespuesta(respuesta1);
    preguntas.agregarRespuesta(respuesta2);
    preguntas.agregarRespuesta(respuesta3);
    preguntas.agregarRespuesta(respuesta4);
    return preguntas;
  }

  }
