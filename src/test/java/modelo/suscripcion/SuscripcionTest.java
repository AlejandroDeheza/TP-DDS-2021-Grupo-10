package modelo.suscripcion;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import modelo.notificacion.NotificadorCorreo;
import modelo.notificacion.TipoNotificadorPreferido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import utils.DummyData;
import java.util.Arrays;
import java.util.Collections;

public class SuscripcionTest {

  NotificadorCorreo notificadorCorreo;
  TipoNotificadorPreferido tipoNotificadorPreferido;

  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
    tipoNotificadorPreferido = mock(TipoNotificadorPreferido.class);
    when(tipoNotificadorPreferido.getNotificador(any())).thenReturn(notificadorCorreo);
  }

  @Test
  @DisplayName("Si se envia el link de baja al posteador, se envia una Notificacion al posteador")
  public void enviarUnLinkDeBajaEnviaUnaNotificacion() {
    SuscripcionParaAdopcion suscripcion =
        DummyData.getSuscripcionParaAdopcion(tipoNotificadorPreferido);
    suscripcion.enviarLinkDeBaja();
    verify(notificadorCorreo, times(1)).notificarLinkDeBajaSuscripcionAdopciones(any());
  }

  @Test
  @DisplayName("Si se envian recomendacion de adopcion, se envia una Notificacion al adoptante")
  public void enviarRecomendacionesEnviaUnaNotificacion() {
    SuscripcionParaAdopcion suscripcion =
        DummyData.getSuscripcionParaAdopcion(tipoNotificadorPreferido);
    suscripcion.enviarRecomendaciones(
        Arrays.asList(
            DummyData.getPublicacionDeDarEnAdopcion(tipoNotificadorPreferido),
            DummyData.getPublicacionDeDarEnAdopcion(tipoNotificadorPreferido)
        )
    );
    verify(notificadorCorreo, times(1)).notificarRecomendacionesDeAdopciones(any());
  }

  @Test
  @DisplayName("Si se envian recomendacion de adopcion con lista vacia, no se envia ninguna Notificacion")
  public void enviarRecomendacionesConListaVaciaNoEnviaUnaNotificacion() {
    SuscripcionParaAdopcion suscripcion =
        DummyData.getSuscripcionParaAdopcion(tipoNotificadorPreferido);
    suscripcion.enviarRecomendaciones(Collections.emptyList());
    verify(notificadorCorreo, times(0)).notificarRecomendacionesDeAdopciones(any());
  }
}
