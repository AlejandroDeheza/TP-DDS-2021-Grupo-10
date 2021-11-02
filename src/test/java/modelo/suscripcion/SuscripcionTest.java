package modelo.suscripcion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;
import utils.MockNotificador;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SuscripcionTest {

  MockNotificador mockNotificador;

  @BeforeEach
  public void contextLoad() {
    mockNotificador = DummyData.getMockNotificador();
  }

  @Test
  @DisplayName("Si se envia el link de baja al posteador, se envia una Notificacion al posteador")
  public void enviarUnLinkDeBajaEnviaUnaNotificacion() {
    SuscripcionParaAdopcion suscripcion =
        DummyData.getSuscripcionParaAdopcion(mockNotificador.getTipo());
    suscripcion.enviarLinkDeBaja();
    verify(mockNotificador.getNotificador(), times(1)).notificarLinkDeBajaSuscripcionAdopciones(any());
  }

  @Test
  @DisplayName("Si se envian recomendacion de adopcion, se envia una Notificacion al adoptante")
  public void enviarRecomendacionesEnviaUnaNotificacion() {
    SuscripcionParaAdopcion suscripcion =
        DummyData.getSuscripcionParaAdopcion(mockNotificador.getTipo());
    suscripcion.enviarRecomendaciones(
        Arrays.asList(
            DummyData.getPublicacionDeDarEnAdopcion(mockNotificador.getTipo()),
            DummyData.getPublicacionDeDarEnAdopcion(mockNotificador.getTipo())
        )
    );
    verify(mockNotificador.getNotificador(), times(1)).notificarRecomendacionesDeAdopciones(any());
  }

  @Test
  @DisplayName("Si se envian recomendacion de adopcion con lista vacia, no se envia ninguna Notificacion")
  public void enviarRecomendacionesConListaVaciaNoEnviaUnaNotificacion() {
    SuscripcionParaAdopcion suscripcion =
        DummyData.getSuscripcionParaAdopcion(mockNotificador.getTipo());
    suscripcion.enviarRecomendaciones(Collections.emptyList());
    verify(mockNotificador.getNotificador(), times(0)).notificarRecomendacionesDeAdopciones(any());
  }
}
