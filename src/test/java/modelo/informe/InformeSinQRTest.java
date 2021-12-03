package modelo.informe;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import repositorios.*;
import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.Animal;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.CascadeTypeCheck;
import utils.DummyData;
import java.util.ArrayList;
import java.util.List;

public class InformeSinQRTest extends NuestraAbstractPersistenceTest {

  RepositorioInformes repositorioInformes = new RepositorioInformes();
  RepositorioAsociaciones repositorioAsociaciones = new RepositorioAsociaciones();
  RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
  RepositorioPersonas repositorioPersonas = new RepositorioPersonas();
  RepositorioMascotaEncontrada repositorioMascotaEncontrada = new RepositorioMascotaEncontrada();
  ReceptorHogares receptorHogaresMock;
  InformeSinQR informeSinQR;
  CascadeTypeCheck cascadeTypeCheck;

  @BeforeEach
  public void loadContext() {
    receptorHogaresMock = mock(ReceptorHogares.class);
    repositorioAsociaciones.agregar(DummyData.getAsociacion());
    informeSinQR = generarInforme();
    cascadeTypeCheck = new CascadeTypeCheck(informeSinQR);
  }

  @Test
  @DisplayName("Cuando se procesa un informe sin QR se agrega una publicación de rescate al RepositorioRescates")
  public void procesarInformeGeneraPublicacionEnElRepo() {
    repositorioInformes.agregar(informeSinQR);
    assertTrue(repositorioInformes.getInformesPendientes().contains(informeSinQR));
    informeSinQR.procesarInforme();
    assertTrue(repositorioInformes.getInformesProcesados().contains(informeSinQR));
    assertEquals(1, new RepositorioRescates().getRescatesActivos().size());
  }

  @Test
  @DisplayName("Obtener Hogares cercanos")
  public void obtenerHogaresDisponiblesParaElInforme() {
    List<Hogar> hogares = new ArrayList<>();
    when(receptorHogaresMock.getHogaresDisponibles(any(), any(), any(), any(), any())).thenReturn(hogares);
    assertEquals(hogares, informeSinQR.getHogaresCercanos(1000));
    verify(receptorHogaresMock, times(1)).getHogaresDisponibles(any(), any(), any(), any(), any());
  }

  @Test
  @DisplayName("Al eliminar un InformeSinQR, no se elimina la lista de Características asociada")
  public void eliminarUnInformeSinQRNoEliminaLaListaDeCaracteristicasAsociada() {
    List<Caracteristica> caracteristicas = informeSinQR.getCaracteristicas();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(caracteristicas, 1, 1, 0, 1));
    assertEquals(caracteristicas.get(0).getId(), repositorioCaracteristicas.listarTodos().get(0).getId());
  }

  @Test
  @DisplayName("Al eliminar un InformeSinQR, no se elimina el Rescatista asociado")
  public void eliminarUnInformeSinQRNoEliminaAlRescatistaAsociado() {
    Persona rescatistaAsociado = informeSinQR.getRescatista();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(rescatistaAsociado, 1, 1, 0, 1));
    assertEquals(rescatistaAsociado.getId(), repositorioPersonas.listarTodos().get(0).getId());
  }

  @Test
  @DisplayName("Al eliminar un InformeSinQR, no se elimina el la MascotaEncontrada asociada")
  public void eliminarUnInformeSinQRNoEliminaALaMascotaEncontradaAsociada() {
    MascotaEncontrada mascotaEncontradaAsociada = informeSinQR.getMascotaEncontrada();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(mascotaEncontradaAsociada, 1, 1, 0, 1));
    assertEquals(mascotaEncontradaAsociada.getId(), repositorioMascotaEncontrada.listarTodos().get(0).getId());
  }

  private InformeSinQR generarInforme() {
    return new InformeSinQR(DummyData.getPersona(null), DummyData.getUbicacion(),
        DummyData.getMascotaEncontrada(DummyData.getFotos()), receptorHogaresMock, Animal.PERRO,
        DummyData.getCaracteristicasParaMascota());
  }
}
