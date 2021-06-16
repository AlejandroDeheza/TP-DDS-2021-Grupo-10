package modelo.informe;

import excepciones.InformeMascotaEncontradaInvalidaException;
import modelo.mascota.*;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioInformes;
import servicio.notificacion.NotificadorCorreo;
import servicio.notificacion.Notificador;
import utils.DummyData;

import javax.mail.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InformeMascotaConDuenioTest {

  Usuario duenioMascota = DummyData.getDummyUsuario();
  Persona rescatista = DummyData.getDummyPersona();
  Ubicacion ubicacion = DummyData.getDummyUbicacion();

  List<Caracteristica> listaCaracteristicas = DummyData.getDummyListaCaracteristicasParaMascota(
      new RepositorioCaracteristicas()
  );

  RepositorioInformes repositorioInformesMock;
  List<Foto> fotosMascota = DummyData.getDummyFotosMascota();
  List<Foto> fotosMascotaVacio = new ArrayList<>();
  InformeMascotaConDuenio informeConFoto;
  InformeMascotaConDuenio informeSinFoto;
  NotificadorCorreo notificadorCorreoMockeado;
  Transport transportMockeado;
  MascotaEncontrada mascotaEncontradaConFotos = DummyData.getDummyMascotaEncontrada(new RepositorioCaracteristicas(), fotosMascota);
  MascotaEncontrada mascotaEncontradaSinFotos = DummyData.getDummyMascotaEncontrada(new RepositorioCaracteristicas(), fotosMascotaVacio);


  @BeforeEach
  public void contextLoad() {
    repositorioInformesMock = mock(RepositorioInformes.class);
    transportMockeado = mock(Transport.class);
    notificadorCorreoMockeado = new NotificadorCorreo(sesion -> transportMockeado);

    informeSinFoto = generarInformeMascotaEncontrada(notificadorCorreoMockeado, mascotaEncontradaSinFotos);
    informeConFoto = generarInformeMascotaEncontrada(notificadorCorreoMockeado, mascotaEncontradaConFotos);
  }


  @Test
  @DisplayName("si se genera un InformeMascotaEncontrada sin fotos, se genera " +
      "InformeMascotaEncontradaInvalidaException")
  public void InformeMascotaEncontradaInvalidaExceptionTest() {
    assertThrows(InformeMascotaEncontradaInvalidaException.class, () -> informeSinFoto.procesarInforme());
  }

  @Test
  @DisplayName("Al procesar un informe de mascota encontrada con duenio, se envia una notificacion" +
      " y se agrega el informe al repositorio")
  public void MascotaConDuenioNotificarTest() throws MessagingException {
    informeConFoto.procesarInforme();
    verify(transportMockeado, times(1)).connect(any(), any());
    verify(transportMockeado, times(1)).sendMessage(any(), any());
    verify(transportMockeado, times(1)).close();

    verify(transportMockeado, atMostOnce()).connect(any(), any());
    verify(transportMockeado, atMostOnce()).sendMessage(any(), any());
    verify(transportMockeado, atMostOnce()).close();

    assertTrue(repositorioInformesMock.getInformesPendientes().contains(informeConFoto));
  }

  private InformeMascotaConDuenio generarInformeMascotaEncontrada(Notificador notificador, MascotaEncontrada mascota) {
    return new InformeMascotaConDuenio(rescatista, ubicacion, mascota, repositorioInformesMock, duenioMascota, notificador);
  }

}
