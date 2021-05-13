package repositorios;

import excepciones.InformeMascotaEncontradaInvalidaException;
import modelo.informe.InformeMascotaEncontrada;
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
  LocalDate fechaDeHoy = LocalDate.now();
  String direccion = "Av. Corrientes 576";
  List<Foto> fotosMascota = DummyData.getDummyFotosMascota();
  List<Foto> fotosMascotaVacio = new ArrayList<>();
  Ubicacion ubicacion = new Ubicacion("57,44", "57,55");
  String estadoActualMascota = "Bien de salud, pero asustado";

  @BeforeEach
  public void contextLoad() {
    repositorioInformes = new RepositorioInformes();
  }

  @Test
  @DisplayName("si se crea un InformeMascotaPerdida, se agrega un informe a InformesPendientes en RepositorioInformes ")
  public void InformesPendientesTest() {
    assertEquals(repositorioInformes.getInformesPendientes().size(), 0);
    generarInformeMascotaEncontrada(fotosMascota);
    assertEquals(repositorioInformes.getInformesPendientes().size(), 1);
  }

  @Test
  @DisplayName("si se utiliza listarMascotasEncontradasEnLosUltimos10Dias(), este devuelve " +
      "un registro insertado previamente")
  public void listarMascotasEncontradasEnLosUltimos10DiasTest() {
    assertEquals(repositorioInformes.listarMascotasEncontradasEnUltimosNDias(10).size(), 0);
    generarInformeMascotaEncontrada(fotosMascota);
    assertEquals(repositorioInformes.listarMascotasEncontradasEnUltimosNDias(10).size(), 1);
  }

  @Test
  @DisplayName("si se genera un InformeMascotaEncontrada sin fotos, se genera " +
      "InformeMascotaEncontradaInvalidaException")
  public void InformeMascotaEncontradaInvalidaExceptionTest() {
    assertThrows(InformeMascotaEncontradaInvalidaException.class,
        () -> generarInformeMascotaEncontrada(fotosMascotaVacio));
  }

  private void generarInformeMascotaEncontrada(List<Foto> fotosMascota) {
    new InformeMascotaEncontrada(
        duenioMascota, rescatista, fechaDeHoy, direccion, fotosMascota, ubicacion, estadoActualMascota,
        repositorioInformes);
  }

}
