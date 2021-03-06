package modelo.publicacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;
import utils.MockNotificador;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PublicacionTest {

  MockNotificador mockNotificador;

  @BeforeEach
  public void contextLoad() {
    mockNotificador = DummyData.getMockNotificador();
  }

  @Test
  @DisplayName("Si un usuario encuentra a su mascota en una publicacion, se envia una Notificacion al rescatista")
  public void encontrarUnaMascotaPerdidaEnviaUnaNotificacion() {
    Rescate publicacion = DummyData.getPublicacionDeRescate(mockNotificador.getTipo());
    publicacion.notificarAlPublicador(DummyData.getUsuario(mockNotificador.getTipo()));
    verify(mockNotificador.getNotificador(), times(1)).notificarDuenioReclamaSuMacota(any());
  }

  @Test
  @DisplayName("Si un adoptante desea adoptar una mascota de una publicacion, se envia una Notificacion al duenio")
  public void procesarUnaPublicacionDeDarEnAdopcionEnviaUnaNotificacion() {
    DarEnAdopcion publicacion = DummyData.getPublicacionDeDarEnAdopcion(mockNotificador.getTipo());
    publicacion.notificarAlPublicador(DummyData.getUsuario(mockNotificador.getTipo()));
    verify(mockNotificador.getNotificador(), times(1)).notificarQuierenAdoptarTuMascota(any(), any());
  }

}
