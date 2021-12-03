package modelo.informe;

import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.MascotaRegistrada;
import modelo.persona.Persona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import repositorios.RepositorioMascotaEncontrada;
import repositorios.RepositorioMascotaRegistrada;
import repositorios.RepositorioPersonas;
import utils.CascadeTypeCheck;
import utils.DummyData;
import utils.MockNotificador;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class InformeConQRTest extends NuestraAbstractPersistenceTest {

  ReceptorHogares receptorHogaresMock;
  MockNotificador mockNotificador;
  InformeConQR informeConQR;
  CascadeTypeCheck cascadeTypeCheck;
  RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();
  RepositorioPersonas repositorioPersonas = new RepositorioPersonas();
  RepositorioMascotaEncontrada repositorioMascotaEncontrada = new RepositorioMascotaEncontrada();

  @BeforeEach
  public void contextLoad() {
    receptorHogaresMock = mock(ReceptorHogares.class);
    mockNotificador = DummyData.getMockNotificador();
    informeConQR = generarInformeConQR();
    cascadeTypeCheck = new CascadeTypeCheck(informeConQR);
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

  @Test
  @DisplayName("Al eliminar un InformeConQR, no se elimina la MascotaRegistrada asociada")
  public void eliminarUnInformeConQRNoEliminaALaMascotaRegistradaAsociada() {
    MascotaRegistrada mascotaRegistradaAsociada = informeConQR.getMascotaRegistrada();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(mascotaRegistradaAsociada, 1, 1, 0, 1));
    assertEquals(mascotaRegistradaAsociada.getId(), repositorioMascotaRegistrada.listarTodos().get(0).getId());
  }

  @Test
  @DisplayName("Al eliminar un InformeConQR, no se elimina el Rescatista asociado")
  public void eliminarUnInformeConQRNoEliminaAlRescatistaAsociado() {
    Persona rescatistaAsociado = informeConQR.getRescatista();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(rescatistaAsociado, 1, 1 /*Rescatista*/ + 1 /*DueñoMascota*/, 0, 1 /*Rescatista*/ + 1 /*DueñoMascota*/));
    assertNotNull(repositorioPersonas.buscarPorId(rescatistaAsociado.getId()));
  }

  @Test
  @DisplayName("Al eliminar un InformeConQR, no se elimina el la MascotaEncontrada asociada")
  public void eliminarUnInformeConQRNoEliminaALaMascotaEncontradaAsociada() {
    MascotaEncontrada mascotaEncontradaAsociada = informeConQR.getMascotaEncontrada();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(mascotaEncontradaAsociada, 1, 1, 0, 1));
    assertEquals(mascotaEncontradaAsociada.getId(), repositorioMascotaEncontrada.listarTodos().get(0).getId());
  }

  private InformeConQR generarInformeConQR() {
    return new InformeConQR(
        DummyData.getPersona(mockNotificador.getTipo()),
        DummyData.getUbicacion(),
        DummyData.getMascotaEncontrada(DummyData.getFotos()),
        receptorHogaresMock,
        DummyData.getMascotaRegistrada(mockNotificador.getTipo())
    );
  }

}
