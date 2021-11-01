package modelo.informe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.notificacion.NotificadorCorreo;
import modelo.notificacion.TipoNotificadorPreferido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;
import java.util.ArrayList;
import java.util.List;

public class InformeConQRTest {

  ReceptorHogares receptorHogaresMock;
  NotificadorCorreo notificadorMockeado;
  TipoNotificadorPreferido tipoNotificador;
  InformeConQR informeConQR;

  @BeforeEach
  public void contextLoad() {
    receptorHogaresMock = mock(ReceptorHogares.class);
    notificadorMockeado = mock(NotificadorCorreo.class);
    tipoNotificador = mock(TipoNotificadorPreferido.class);
    when(tipoNotificador.getNotificador(any())).thenReturn(notificadorMockeado);
    informeConQR = generarInformeConQR();
  }

  @Test
  @DisplayName("Al procesar un informe con QR, se envia una notificacion y se marca el informe como procesado")
  public void MascotaConDuenioNotificarTest() {
    informeConQR.procesarInforme();
    verify(notificadorMockeado, times(1)).notificarEncontramosTuMascota(any());
  }

  @Test
  @DisplayName("Obtener Hogares cercanos")
  public void obtenerHogaresDisponiblesParaElInforme() {
    List<Hogar> hogares = new ArrayList<>();
    when(receptorHogaresMock.getHogaresDisponibles(any(), any(), any(), any(), any())).thenReturn(hogares);
    assertEquals(hogares, informeConQR.getHogaresCercanos(1000));
    verify(receptorHogaresMock,
        times(1)).getHogaresDisponibles(any(), any(), any(), any(), any());
  }

  private InformeConQR generarInformeConQR() {
    return new InformeConQR(DummyData.getPersona(tipoNotificador), DummyData.getUbicacion(), null,
        receptorHogaresMock, DummyData.getMascotaRegistrada(tipoNotificador));
  }

}
