package entregaTPA3;

import modelo.adopcion.RecomendadorDeAdopciones;
import modelo.notificacion.Notificador;
import modelo.notificacion.NotificadorCorreo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import utils.DummyData;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class Punto5 {

  Notificador notificadorMockeado;
  RepositorioDarEnAdopcion repositorioDarEnAdopcion;
  RepositorioSuscripcionesParaAdopciones repositorioSuscripcionesParaAdopciones;
  RecomendadorDeAdopciones recomendadorDeAdopciones;

  @BeforeEach
  public void contextLoad() {
    notificadorMockeado = mock(NotificadorCorreo.class);

    repositorioDarEnAdopcion = new RepositorioDarEnAdopcion();
    repositorioDarEnAdopcion.agregar(DummyData.getPublicacionDeDarEnAdopcion(
        notificadorMockeado, repositorioDarEnAdopcion));

    repositorioSuscripcionesParaAdopciones = new RepositorioSuscripcionesParaAdopciones();
    repositorioSuscripcionesParaAdopciones.agregar(DummyData.getSuscripcionParaAdopcion(notificadorMockeado));

    recomendadorDeAdopciones = new RecomendadorDeAdopciones(5,
        repositorioDarEnAdopcion, repositorioSuscripcionesParaAdopciones);
  }

  @Test
  public void seGeneranYEnvianRecomendacionesCorrectamente() {
    recomendadorDeAdopciones.recomendarAdopcionesASuscritos();
    verify(notificadorMockeado, times(1)).notificarRecomendacionesDeAdopciones(any());
  }

}
