package repositorios;

import client.ObtenerHogaresClient;
import modelo.informe.InformeConQR;
import modelo.informe.Ubicacion;
import modelo.mascota.Foto;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.MascotaRegistrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import servicio.notificacion.Notificador;
import servicio.notificacion.NotificadorCorreo;
import utils.DummyData;

import javax.mail.Transport;
import java.time.LocalDate;
import java.util.List;

public class RepositorioInformesTest {

  RepositorioInformes repositorioInformes;

  Usuario duenioMascota = DummyData.getDummyUsuario();
  Persona rescatista = DummyData.getDummyPersona();
  LocalDate fechaDeHoy = LocalDate.now();
  Ubicacion ubicacion = DummyData.getDummyUbicacion();
  List<Foto> fotosMascota = DummyData.getDummyFotosMascota();
  Ubicacion lugarDeEncuentro = new Ubicacion(57.44, 57.55);
  List<Caracteristica> estadoActualMascota = DummyData.getDummyListaCaracteristicasParaMascota(
      new RepositorioCaracteristicas()
  );
  MascotaRegistrada mascotaRegistrada = DummyData.getDummyMascotaRegistrada(new RepositorioCaracteristicas());
  RepositorioProperties repositorioProperties = RepositorioProperties.getInstance();

  InformeConQR informe;

  MascotaEncontrada mascota = DummyData.getDummyMascotaEncontrada(new RepositorioCaracteristicas(), fotosMascota);

  Transport transportMockeado;
  NotificadorCorreo notificadorCorreoMockeado;


  @BeforeEach
  public void contextLoad() {
    repositorioInformes = new RepositorioInformes();
    transportMockeado = mock(Transport.class);
    notificadorCorreoMockeado = new NotificadorCorreo(sesion -> transportMockeado);
    informe = generarInformeMascotaEncontrada(notificadorCorreoMockeado, mascota);
  }

  @Test
  @DisplayName("si se crea un InformeMascotaPerdida, se agrega un informe a InformesPendientes en RepositorioInformes")
  public void InformesPendientesTest() {
    assertEquals(repositorioInformes.getInformesPendientes().size(), 0);
    repositorioInformes.agregarInformeMascotaEncontrada(informe);
    assertEquals(repositorioInformes.getInformesPendientes().size(), 1);
  }

  @Test
  @DisplayName("si se utiliza listarMascotasEncontradasEnLosUltimos10Dias(), este devuelve " +
      "un registro insertado previamente")
  public void listarMascotasEncontradasEnLosUltimos10DiasTest() {
    assertEquals(repositorioInformes.listarMascotasEncontradasEnUltimosNDias(10).size(), 0);
    repositorioInformes.agregarInformeMascotaEncontrada(informe);
    assertEquals(repositorioInformes.listarMascotasEncontradasEnUltimosNDias(10).size(), 1);
  }

  private InformeConQR generarInformeMascotaEncontrada(Notificador notificador, MascotaEncontrada mascotaEncontrada) {
    return new InformeConQR(rescatista, ubicacion, "", mascotaEncontrada, repositorioInformes, new ObtenerHogaresClient(),
        mascotaRegistrada, notificador, repositorioProperties);

  }
}
