package modelo.informe;

import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioInformes;
import modelo.notificacion.NotificadorCorreo;
import utils.DummyData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InformeConQRTest {

  RepositorioInformes repositorioInformes;
  ReceptorHogares receptorHogaresMock;
  NotificadorCorreo notificadorMockeado;
  InformeConQR informeConQR;

  @BeforeEach
  public void contextLoad() {
    repositorioInformes = new RepositorioInformes();
    receptorHogaresMock = mock(ReceptorHogares.class);
    notificadorMockeado = mock(NotificadorCorreo.class);
    informeConQR = generarInformeConQR();
  }

  @Test
  @DisplayName("Al procesar un informe con QR, se envia una notificacion y se marca el informe como procesado")
  public void MascotaConDuenioNotificarTest() {
    repositorioInformes.agregarInformeRescate(informeConQR);
    assertTrue(repositorioInformes.getInformesPendientes().contains(informeConQR));
    informeConQR.procesarInforme();
    assertTrue(repositorioInformes.getInformesProcesados().contains(informeConQR));

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
    return new InformeConQR(DummyData.getPersona(notificadorMockeado), DummyData.getUbicacion(), null,
        repositorioInformes, receptorHogaresMock, DummyData.getMascotaRegistrada(notificadorMockeado));
  }

}
