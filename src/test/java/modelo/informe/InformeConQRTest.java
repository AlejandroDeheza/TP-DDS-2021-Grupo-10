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
import utils.DummyData;
import utils.MockNotificador;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class InformeConQRTest extends NuestraAbstractPersistenceTest {

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

  @Test
  @DisplayName("Al eliminar un InformeConQR, no se elimina la MascotaRegistrada asociada")
  public void eliminarUnInformeConQRNoEliminaALaMascotaRegistradaAsociada() {
    MascotaRegistrada mascotaRegistradaAsociada = informeConQR.getMascotaRegistrada();

    entityManager().persist(informeConQR);
    assertEquals(1, entityManager().createQuery("from InformeConQR", InformeConQR.class).getResultList().size());
    assertEquals(1, entityManager().createQuery("from MascotaRegistrada", MascotaRegistrada.class).getResultList().size());

    entityManager().remove(informeConQR);
    assertEquals(0, entityManager().createQuery("from InformeConQR", InformeConQR.class).getResultList().size());
    assertEquals(mascotaRegistradaAsociada.getId(), entityManager().createQuery("from MascotaRegistrada", MascotaRegistrada.class).getResultList().get(0).getId());
  }
  
  @Test
  @DisplayName("Al eliminar un InformeConQR, no se elimina el Rescatista asociado")
  public void eliminarUnInformeConQRNoEliminaAlRescatistaAsociado() {
    Persona rescatistaAsociado = informeConQR.getRescatista();

    entityManager().persist(informeConQR);
    assertEquals(1, entityManager().createQuery("from InformeConQR", InformeConQR.class).getResultList().size());

    entityManager().remove(informeConQR);
    assertEquals(0, entityManager().createQuery("from InformeConQR", InformeConQR.class).getResultList().size());

    Persona elMismoRescatistaAsociado = entityManager().find(Persona.class, rescatistaAsociado.getId());    
    assertNotNull(elMismoRescatistaAsociado);
  }
  
  @Test
  @DisplayName("Al eliminar un InformeConQR, no se elimina el la MascotaEncontrada asociada")
  public void eliminarUnInformeConQRNoEliminaALaMascotaEncontradaAsociada() {
    MascotaEncontrada mascotaEncontradaAsociada = informeConQR.getMascotaEncontrada();

    entityManager().persist(informeConQR);
    assertEquals(1, entityManager().createQuery("from InformeConQR", InformeConQR.class).getResultList().size());
    assertEquals(1, entityManager().createQuery("from MascotaEncontrada", MascotaEncontrada.class).getResultList().size());

    entityManager().remove(informeConQR);
    assertEquals(0, entityManager().createQuery("from InformeConQR", InformeConQR.class).getResultList().size());
    assertEquals(mascotaEncontradaAsociada.getId(), entityManager().createQuery("from MascotaEncontrada", MascotaEncontrada.class).getResultList().get(0).getId());
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
