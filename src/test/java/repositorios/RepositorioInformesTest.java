package repositorios;

import modelo.informe.InformeMascotaConDuenio;
import modelo.informe.InformeMascotaConDuenioTest;
import modelo.informe.Ubicacion;
import modelo.mascota.Foto;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import utils.DummyData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioInformesTest {

  RepositorioInformes repositorioInformes;

  Usuario duenioMascota = DummyData.getDummyUsuario();
  Persona rescatista = DummyData.getDummyPersona();
  Usuario voluntario = DummyData.getDummyUsuarioVoluntario();
  LocalDate fechaDeHoy = LocalDate.now();
  String direccion = "Av. Corrientes 576";
  List<Foto> fotosMascota = DummyData.getDummyFotosMascota();
  List<Foto> fotosMascotaVacio = new ArrayList<>();
  Ubicacion ubicacion = new Ubicacion("57,44", "57,55");
  String estadoActualMascota = "Bien de salud, pero asustado";
  InformeMascotaConDuenio informe;

  @BeforeEach
  public void contextLoad() {
    repositorioInformes = new RepositorioInformes();
    informe = generarInformeMascotaEncontrada(fotosMascota);
  }

  @AfterEach
  public void destroyContext() {
    repositorioInformes = null;
    informe = null;
  }

  @Test
  @DisplayName("si se crea un InformeMascotaPerdida, se agrega un informe a InformesPendientes en RepositorioInformes ")
  public void InformesPendientesTest() {
    assertEquals(repositorioInformes.getInformesPendientes().size(), 0);
    informe.procesarInforme(voluntario);
    assertEquals(repositorioInformes.getInformesPendientes().size(), 1);
  }

  @Test
  @DisplayName("si se utiliza listarMascotasEncontradasEnLosUltimos10Dias(), este devuelve " +
      "un registro insertado previamente")
  public void listarMascotasEncontradasEnLosUltimos10DiasTest() {
    assertEquals(repositorioInformes.listarMascotasEncontradasEnUltimosNDias(10).size(), 0);
    informe.procesarInforme(voluntario);
    assertEquals(repositorioInformes.listarMascotasEncontradasEnUltimosNDias(10).size(), 1);
  }

  private InformeMascotaConDuenio generarInformeMascotaEncontrada(List<Foto> fotosMascota) {
    return new InformeMascotaConDuenio(
        duenioMascota, rescatista, fechaDeHoy, direccion, fotosMascota, ubicacion, estadoActualMascota,
        repositorioInformes);
  }

}
