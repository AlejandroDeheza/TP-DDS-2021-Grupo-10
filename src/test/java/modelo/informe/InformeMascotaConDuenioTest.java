package modelo.informe;

import excepciones.InformeMascotaEncontradaInvalidaException;
import modelo.mascota.Foto;
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
  Ubicacion ubicacion = new Ubicacion(57.44, 57.55);
  List<Caracteristica> estadoActualMascota = DummyData.getDummyListaCaracteristicasParaMascota(
      new RepositorioCaracteristicas()
  );

  RepositorioInformes repositorioInformes;
  List<Foto> fotosMascota = DummyData.getDummyFotosMascota();
  List<Foto> fotosMascotaVacio = new ArrayList<>();
  InformeMascotaConDuenio informeConFoto;
  InformeMascotaConDuenio informeSinFoto;
  NotificacionCorreo notificacionCorreoMockeado;
  Transport transportMockeado;

  @BeforeEach
  public void contextLoad() {
    repositorioInformes = new RepositorioInformes();
    transportMockeado = mock(Transport.class);

    informeSinFoto = generarInformeMascotaEncontradaBuilder(fotosMascotaVacio, new NotificacionCorreo(sesion -> transportMockeado));
    informeConFoto = generarInformeMascotaEncontradaBuilder(fotosMascota, new NotificacionCorreo(sesion -> transportMockeado));
  }

  @Test
  @DisplayName("Chequeo igualdad entre Constructor y Builder")
  public void InformeMascotaEncontradaBuilderConstructorTest(){
    InformeMascotaConDuenio informeAux = generarInformeMascotaEncontrada(fotosMascotaVacio);
    Assertions.assertEquals(informeSinFoto.getDuenioMascota(), informeAux.getDuenioMascota());
    Assertions.assertEquals(informeSinFoto.getDireccion(), informeAux.getDireccion());
    Assertions.assertEquals(informeSinFoto.getEstadoActualMascota(), informeAux.getEstadoActualMascota());
    Assertions.assertEquals(informeSinFoto.getFotosMascota(), informeAux.getFotosMascota());
    Assertions.assertEquals(informeSinFoto.getFechaEncuentro(), informeAux.getFechaEncuentro());
    Assertions.assertEquals(informeSinFoto.getLugarDeEncuentro(), informeAux.getLugarDeEncuentro());
    Assertions.assertEquals(informeSinFoto.getRescatista(), informeAux.getRescatista());
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

  private InformeMascotaConDuenio generarInformeMascotaEncontrada(List<Foto> fotosMascota) {
    return new InformeMascotaConDuenio(duenioMascota, rescatista, fechaDeHoy, ubicacion, fotosMascota, ubicacion,
        estadoActualMascota, new NotificacionCorreo(sesion -> transportMockeado), repositorioInformes);
  }

  private InformeMascotaConDuenio generarInformeMascotaEncontradaBuilder(List<Foto> fotosMascota, NotificacionSender notificacionSender) {
    InformeMascotaConDuenioBuilder builder = InformeMascotaConDuenioBuilder.crearBuilder();
    builder.conNotificacionSender(notificacionSender)
        .conDuenioMascota(duenioMascota)
        .conRepositorioInformes(repositorioInformes)
        .conRescatista(rescatista)
        .conFechaEncuentro(fechaDeHoy)
        .conDireccion(ubicacion)
        .conFotosMascota(fotosMascota)
        .conLugarDeEncuentro(ubicacion)
        .conEstadoActualMascota(estadoActualMascota);
    return builder.build();
  }
}
