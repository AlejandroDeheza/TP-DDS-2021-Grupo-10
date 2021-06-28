package modelo.informe;

import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioInformes;
import repositorios.RepositorioRescates;
import utils.DummyData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class InformeSinQRTest {

  RepositorioInformes repositorioInformes;
  RepositorioRescates repositorioRescates;
  ReceptorHogares receptorHogaresMock;
  InformeSinQR informeSinQR;

  @BeforeEach
  public void loadContext() {
    repositorioInformes = new RepositorioInformes();
    repositorioRescates = new RepositorioRescates();
    receptorHogaresMock = mock(ReceptorHogares.class);
    informeSinQR = generarInforme();
  }

  @Test
  @DisplayName("Cuando Se se procesa un informe sin QR se agrega una publicación de rescate al RepositorioRescates")
  public void procesarInformeGeneraPublicacionEnElRepo() {
    repositorioInformes.agregarInformeRescate(informeSinQR);
    assertTrue(repositorioInformes.getInformesPendientes().contains(informeSinQR));
    informeSinQR.procesarInforme();
    assertTrue(repositorioInformes.getInformesProcesados().contains(informeSinQR));

    assertEquals(1, repositorioRescates.getRescates().size());
  }

  @Test
  @DisplayName("Obtener Hogares cercanos")
  public void obtenerHogaresDisponiblesParaElInforme() {
    List<Hogar> hogares = new ArrayList<>();
    when(receptorHogaresMock.getHogaresDisponibles(any(), any(), any(), any(), any())).thenReturn(hogares);
    assertEquals(hogares, informeSinQR.getHogaresCercanos(1000));
    verify(receptorHogaresMock, times(1)).getHogaresDisponibles(any(), any(), any(), any(), any());
  }

  private InformeSinQR generarInforme() {
    return new InformeSinQR(DummyData.getPersona(), DummyData.getUbicacion(), null, DummyData.getMascotaEncontrada(DummyData.getFotos()),
        repositorioInformes, receptorHogaresMock, Animal.PERRO, DummyData.getCaracteristicasParaMascota(new RepositorioCaracteristicas()),
        repositorioRescates, null);
  }

}
