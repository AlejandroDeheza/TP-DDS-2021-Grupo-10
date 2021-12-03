package entregaTPA3;

import modelo.adopcion.RecomendadorDeAdopciones;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import utils.DummyData;
import utils.MockNotificador;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class Punto5 {

  MockNotificador mockNotificador;
  RepositorioDarEnAdopcion repositorioDarEnAdopcion;
  RepositorioSuscripcionesParaAdopciones repositorioSuscripcionesParaAdopciones;
  RecomendadorDeAdopciones recomendadorDeAdopciones;

  @BeforeEach
  public void contextLoad() {
    mockNotificador = DummyData.getMockNotificador();
    repositorioDarEnAdopcion = mock(RepositorioDarEnAdopcion.class);
    repositorioSuscripcionesParaAdopciones = mock(RepositorioSuscripcionesParaAdopciones.class);

    when(repositorioDarEnAdopcion.getPublicacionesActivas()).thenReturn(Collections.singletonList(
        DummyData.getPublicacionDeDarEnAdopcion(mockNotificador.getTipo()))
    );
    when(repositorioSuscripcionesParaAdopciones.getSuscripcionesActivas()).thenReturn(Collections.singletonList(
        DummyData.getSuscripcionParaAdopcion(mockNotificador.getTipo())
    ));

    recomendadorDeAdopciones = new RecomendadorDeAdopciones(5,
        repositorioDarEnAdopcion, repositorioSuscripcionesParaAdopciones);
  }

  @Test
  public void seGeneranYEnvianRecomendacionesCorrectamente() {
    recomendadorDeAdopciones.recomendarAdopcionesASuscritos();
    verify(mockNotificador.getNotificador(), times(1)).notificarRecomendacionesDeAdopciones(any());
  }

}
