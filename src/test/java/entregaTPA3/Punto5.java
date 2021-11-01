package entregaTPA3;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import modelo.adopcion.RecomendadorDeAdopciones;
import modelo.notificacion.Notificador;
import modelo.notificacion.NotificadorCorreo;
import modelo.notificacion.TipoNotificadorPreferido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import utils.DummyData;
import java.util.Collections;

public class Punto5 {

  TipoNotificadorPreferido tipoNotificadorPreferido;
  Notificador notificadorMockeado;
  RepositorioDarEnAdopcion repositorioDarEnAdopcion;
  RepositorioSuscripcionesParaAdopciones repositorioSuscripcionesParaAdopciones;
  RecomendadorDeAdopciones recomendadorDeAdopciones;

  @BeforeEach
  public void contextLoad() {
    notificadorMockeado = mock(NotificadorCorreo.class);
    repositorioDarEnAdopcion = mock(RepositorioDarEnAdopcion.class);
    repositorioSuscripcionesParaAdopciones = mock(RepositorioSuscripcionesParaAdopciones.class);

    tipoNotificadorPreferido = mock(TipoNotificadorPreferido.class);
    when(tipoNotificadorPreferido.getNotificador(any())).thenReturn(notificadorMockeado);

    when(repositorioDarEnAdopcion.getPublicaciones()).thenReturn(Collections.singletonList(
        DummyData.getPublicacionDeDarEnAdopcion(tipoNotificadorPreferido))
    );
    when(repositorioSuscripcionesParaAdopciones.getSuscripciones()).thenReturn(Collections.singletonList(
        DummyData.getSuscripcionParaAdopcion(tipoNotificadorPreferido)
    ));


    recomendadorDeAdopciones = new RecomendadorDeAdopciones(5,
        repositorioDarEnAdopcion, repositorioSuscripcionesParaAdopciones);
  }

  @Test
  public void seGeneranYEnvianRecomendacionesCorrectamente() {
    recomendadorDeAdopciones.recomendarAdopcionesASuscritos();
    verify(notificadorMockeado, times(1)).notificarRecomendacionesDeAdopciones(any());
  }

}
