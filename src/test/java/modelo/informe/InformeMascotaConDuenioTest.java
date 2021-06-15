package modelo.informe;

import excepciones.InformeMascotaEncontradaInvalidaException;
import modelo.mascota.*;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioInformes;
import servicio.notificacion.NotificacionCorreo;
import servicio.notificacion.NotificacionSender;
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
  LocalDate fechaDeHoy = LocalDate.now();
  List<Caracteristica> listaCaracteristicas = DummyData.getDummyListaCaracteristicasParaMascota(
      new RepositorioCaracteristicas()
  );

  RepositorioInformes repositorioInformes;
  List<Foto> fotosMascota = DummyData.getDummyFotosMascota();
  List<Foto> fotosMascotaVacio = new ArrayList<>();
  InformeMascotaConDuenio informeConFoto;
  InformeMascotaConDuenio informeSinFoto;
  NotificacionCorreo notificacionCorreoMockeado;
  Transport transportMockeado;
  MascotaEncontrada mascotaEncontrada = DummyData.getDummyMascotaEncontrada(listaCaracteristicas);
  MascotaRegistrada mascotaRegistrada = DummyData.getDummyMascotaRegistrada(listaCaracteristicas);

  @BeforeEach
  public void contextLoad() {
    repositorioInformes = new RepositorioInformes();
    transportMockeado = mock(Transport.class);

    informeSinFoto = generarInformeMascotaEncontrada(new NotificacionCorreo(sesion -> transportMockeado));
    informeConFoto = generarInformeMascotaEncontrada(new NotificacionCorreo(sesion -> transportMockeado));
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

    assertTrue(repositorioInformes.getInformesPendientes().contains(informeConFoto));
  }

  private InformeMascotaConDuenio generarInformeMascotaEncontrada(NotificacionSender notificacionSender) {
    return new InformeMascotaConDuenio(rescatista, new Ubicacion(12.25, 25.23), mascotaEncontrada, repositorioInformes, duenioMascota, notificacionSender);
  }

}
