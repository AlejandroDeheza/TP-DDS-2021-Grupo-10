package modelo.informe;

import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;
import utils.MockNotificador;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InformeConQRTest {

  ReceptorHogares receptorHogaresMock;
  MockNotificador mockNotificador;
  InformeConQR informeConQR;

  @BeforeEach
  public void contextLoad() {
    receptorHogaresMock = mock(ReceptorHogares.class);
    mockNotificador = DummyData.getMockNotificador();
    informeConQR = generarInformeConQR();
  }

  @Test
  @DisplayName("Al procesar un informe con QR, se envia una notificacion y se marca el informe como procesado")
  public void MascotaConDuenioNotificarTest() {
    informeConQR.procesarInforme();
    verify(mockNotificador.getNotificador(), times(1)).notificarEncontramosTuMascota(any());
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
    return new InformeConQR(DummyData.getPersona(mockNotificador.getTipo()), DummyData.getUbicacion(), null,
        receptorHogaresMock, DummyData.getMascotaRegistrada(mockNotificador.getTipo()));
  }

}
