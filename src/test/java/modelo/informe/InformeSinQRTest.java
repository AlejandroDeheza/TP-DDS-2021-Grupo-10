package modelo.informe;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import repositorios.RepositorioAsociaciones;
import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioInformes;
import repositorios.RepositorioRescates;
import utils.DummyData;
import java.util.ArrayList;
import java.util.List;

public class InformeSinQRTest extends NuestraAbstractPersistenceTest {

  RepositorioInformes repositorioInformes = new RepositorioInformes();
  ReceptorHogares receptorHogaresMock;
  InformeSinQR informeSinQR;

  @BeforeEach
  public void loadContext() {
    receptorHogaresMock = mock(ReceptorHogares.class);
    entityManager().persist(DummyData.getAsociacion());
    informeSinQR = generarInforme();
  }

  @Test
  @DisplayName("Cuando se procesa un informe sin QR se agrega una publicación de rescate al RepositorioRescates")
  public void procesarInformeGeneraPublicacionEnElRepo() {
    entityManager().persist(informeSinQR);
    assertTrue(repositorioInformes.getInformesPendientes().contains(informeSinQR));
    informeSinQR.procesarInforme();
    assertTrue(repositorioInformes.getInformesProcesados().contains(informeSinQR));

    assertEquals(1, new RepositorioRescates().getRescates().size());
  }

  @Test
  @DisplayName("Obtener Hogares cercanos")
  public void obtenerHogaresDisponiblesParaElInforme() {
    List<Hogar> hogares = new ArrayList<>();
    when(receptorHogaresMock.getHogaresDisponibles(any(), any(), any(), any(), any())).thenReturn(hogares);
    assertEquals(hogares,
        receptorHogaresMock.getHogaresDisponibles(informeSinQR.getUbicacionRescatista(), 1000,
            informeSinQR.getTipoAnimal(), informeSinQR.getMascotaEncontrada().getTamanio(),
            informeSinQR.getCaracteristicas()));
    verify(receptorHogaresMock, times(1)).getHogaresDisponibles(any(), any(), any(), any(), any());
  }

  private InformeSinQR generarInforme() {
    return new InformeSinQR(DummyData.getPersona(null), DummyData.getUbicacion(),
        DummyData.getMascotaEncontrada(DummyData.getFotos()), receptorHogaresMock, Animal.PERRO,
        DummyData.getCaracteristicasParaMascota());
  }

}
