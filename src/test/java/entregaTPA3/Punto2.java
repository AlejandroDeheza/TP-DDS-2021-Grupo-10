package entregaTPA3;

import modelo.asociacion.Asociacion;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.ParDeRespuestas;
import modelo.pregunta.RepositorioPreguntasObligatorias;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Punto2 {
  RepositorioPreguntasObligatorias repositorioPreguntasObligatorias;
  ParDePreguntas parDePreguntas;
  Asociacion asociacion = new Asociacion(DummyData.getUbicacion());

  @BeforeEach
  public void loadContext() {
    repositorioPreguntasObligatorias = new RepositorioPreguntasObligatorias();
    parDePreguntas = getParDePreguntas();
  }

  @Test
  @DisplayName("Se puede definir un ParDePreguntas en una Asociacion")
  public void agregarParDePreguntasAAsociacion() {
    assertEquals(0, asociacion.getPreguntas().size());
    asociacion.agregarPregunta(parDePreguntas);
    assertEquals(1, asociacion.getPreguntas().size());
  }

  @Test
  @DisplayName("Se puede agregar una ParDePreguntas al RepositorioPreguntas")
  public void agregarParDePreguntasAlRepositorioPreguntas() {
    assertEquals(0, repositorioPreguntasObligatorias.getPreguntas().size());
    repositorioPreguntasObligatorias.agregarPregunta(parDePreguntas);
    assertEquals(1, repositorioPreguntasObligatorias.getPreguntas().size());
  }

  private ParDePreguntas getParDePreguntas() {
    ParDePreguntas preguntas = new ParDePreguntas(
        "La mascota sufre si está mucho tiempo sola?",
        "Va a estar la mascota mucho tiempo sola?");
    ParDeRespuestas respuesta1 = new ParDeRespuestas("Si", "No");
    ParDeRespuestas respuesta2 = new ParDeRespuestas("No", "Si");
    ParDeRespuestas respuesta3 = new ParDeRespuestas("No", "No");
    preguntas.agregarRespuesta(respuesta1);
    preguntas.agregarRespuesta(respuesta2);
    preguntas.agregarRespuesta(respuesta3);

    return preguntas;
  }

}
