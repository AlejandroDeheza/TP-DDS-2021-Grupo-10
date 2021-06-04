package repositorios;

import modelo.informe.InformeMascotaConDuenio;
import modelo.informe.Ubicacion;
import modelo.mascota.Foto;
import modelo.mascota.Mascota;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import utils.DummyData;

import java.time.LocalDate;
import java.util.List;

public class RepositorioInformesTest {

  RepositorioInformes repositorioInformes;

  Usuario duenioMascota = DummyData.getDummyUsuario();
  Persona rescatista = DummyData.getDummyPersona();
  LocalDate fechaDeHoy = LocalDate.now();
  Ubicacion direccion = new Ubicacion(57.44, 57.55);
  List<Foto> fotosMascota = DummyData.getDummyFotosMascota();
  Ubicacion lugarDeEncuentro = new Ubicacion(57.44, 57.55);
  List<Caracteristica> estadoActualMascota = DummyData.getDummyListaCaracteristicasParaMascota();
  InformeMascotaConDuenio informe;

  Mascota mascota = DummyData.getDummyMascota();

  @BeforeEach
  public void contextLoad() {
    repositorioInformes = new RepositorioInformes();
    informe = generarInformeMascotaEncontrada(mascota);
  }

  @AfterEach
  public void destroyContext() {
    repositorioInformes = null;
    informe = null;
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

  private InformeMascotaConDuenio generarInformeMascotaEncontrada(Mascota mascota) {
    return new InformeMascotaConDuenio(duenioMascota, rescatista, fechaDeHoy, direccion,fotosMascota, lugarDeEncuentro,estadoActualMascota);
  }
}
