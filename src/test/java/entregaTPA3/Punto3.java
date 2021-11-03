package entregaTPA3;

import modelo.publicacion.DarEnAdopcion;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;
import utils.MockNotificador;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class Punto3 {

  MockNotificador mockNotificador;

  @BeforeEach
  public void contextLoad() {
    mockNotificador = DummyData.getMockNotificador();
  }

  @Test
  @DisplayName("Si un interesado desea adoptar una mascota de una publicacion, se envia una Notificacion al duenio")
  public void procesarUnaPublicacionDeDarEnAdopcionEnviaUnaNotificacion() {
    DarEnAdopcion publicacion = DummyData.getPublicacionDeDarEnAdopcion(mockNotificador.getTipo());
    Usuario adoptante = DummyData.getUsuario(mockNotificador.getTipo());
    publicacion.notificarAlPublicador(adoptante);
    verify(mockNotificador.getTipo().getNotificador(any()), times(1)).notificarQuierenAdoptarTuMascota(any(), any());
  }
}
