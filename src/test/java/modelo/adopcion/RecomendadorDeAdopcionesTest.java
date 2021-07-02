package modelo.adopcion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import utils.DummyData;

import static org.junit.jupiter.api.Assertions.*;

public class RecomendadorDeAdopcionesTest {
  RecomendadorDeAdopciones recomendadorDeAdopciones;
  RepositorioDarEnAdopcion repositorioDarEnAdopcion;
  RepositorioSuscripcionesParaAdopciones repositorioSuscripcionesParaAdopciones;
  @BeforeEach
  public void contextLoad() {
    repositorioDarEnAdopcion= new RepositorioDarEnAdopcion();
    repositorioSuscripcionesParaAdopciones = new RepositorioSuscripcionesParaAdopciones();
    recomendadorDeAdopciones= new RecomendadorDeAdopciones(1,repositorioDarEnAdopcion,repositorioSuscripcionesParaAdopciones);
  }

  @Test
  @DisplayName("Recomienda a los subscriptores")
  public void recomendacionesALosSuscriptores() {
    recomendadorDeAdopciones.recomendarAdopcionesASuscritos();

  }
}
