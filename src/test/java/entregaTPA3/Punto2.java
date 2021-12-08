package entregaTPA3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import modelo.asociacion.Asociacion;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.ParDeRespuestas;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import repositorios.RepositorioParDePreguntas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import java.util.ArrayList;
import java.util.List;

public class Punto2 extends NuestraAbstractPersistenceTest {
  RepositorioParDePreguntas repositorioParDePreguntas = new RepositorioParDePreguntas();
  ParDePreguntas parDePreguntas;
  Asociacion asociacion = new Asociacion("", DummyData.getUbicacion());

  @BeforeEach
  public void loadContext() {
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
    assertEquals(0, repositorioParDePreguntas.getPreguntasObligatorias().size());
    repositorioParDePreguntas.agregar(parDePreguntas);
    assertEquals(1, repositorioParDePreguntas.getPreguntasObligatorias().size());
  }

  private ParDePreguntas getParDePreguntas() {
    List<ParDeRespuestas> paresDeRespuestas = new ArrayList<>();

    paresDeRespuestas.add(new ParDeRespuestas("Si", "No"));
    paresDeRespuestas.add(new ParDeRespuestas("No", "Si"));
    paresDeRespuestas.add(new ParDeRespuestas("No", "No"));

    return new ParDePreguntas(
        "Mascotas solitarias",
        "La mascota sufre si está mucho tiempo sola?",
        "Va a estar la mascota mucho tiempo sola?",
        true,
        null,
        null,
        paresDeRespuestas
    );
  }

}
