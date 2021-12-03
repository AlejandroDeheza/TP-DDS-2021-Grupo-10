package modelo.mascota;

import static org.junit.jupiter.api.Assertions.*;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import repositorios.RepositorioUsuarios;
import utils.CascadeTypeCheck;
import utils.DummyData;
import java.util.ArrayList;
import java.util.List;

public class MascotaRegistradaTest extends NuestraAbstractPersistenceTest {
  MascotaRegistrada mascotaRegistrada;
  List<Caracteristica> listaCaracteristica;
  CascadeTypeCheck cascadeTypeCheck;
  RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();

  @BeforeEach
  public void contextLoad() {
    mascotaRegistrada = DummyData.getMascotaRegistrada(null);
    listaCaracteristica = new ArrayList<>();
    cascadeTypeCheck = new CascadeTypeCheck(mascotaRegistrada);
  }

  @Test
  @DisplayName("Una Mascota cumple con caracteristicas de la misma mascota")
  public void cumpleConLasCaracteristicasDeLaMismaMascota() {
    assertTrue(mascotaRegistrada.cumpleConCaracteristicas(DummyData.getCaracteristicasParaMascota()));
  }

  @Test
  @DisplayName("Una Mascota no cumple con caracteristicas")
  public void noCumpleConLasCaracteristicas() {
    listaCaracteristica.add(new Caracteristica("Comportamiento", "Inquieto"));
    assertFalse(mascotaRegistrada.cumpleConCaracteristicas(listaCaracteristica));
  }

  @Test
  @DisplayName("Una Mascota no cumple con las caracteristicas si no matchean todas las caracteristicas")
  public void noCumpleCaracteristicasSiNoMatcheanTodaslasCaracteristicas() {
    listaCaracteristica.add(new Caracteristica("Comportamiento", "Tranquilo"));
    listaCaracteristica.add(new Caracteristica("Comilon", "Si"));
    assertFalse(mascotaRegistrada.cumpleConCaracteristicas(listaCaracteristica));
  }

  @Test
  @DisplayName("Al eliminar una MascotaRegistrada, no se elimina al Usuario dueño asociado")
  public void eliminarUnaMascotaRegistradaNoEliminaAlUsuarioDuenioAsociado() {
    Usuario duenio = mascotaRegistrada.getDuenio();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(duenio, 1, 1, 0, 1));
    assertEquals(duenio.getId(), repositorioUsuarios.listarTodos().get(0).getId());
  }

  @Test
  @DisplayName("Al eliminar una MascotaRegistrada, se elimina la lista de Características asociada")
  public void eliminarUnaMascotaRegistradaSeEliminaLaListaDeCaracteristicasAsociada() {
    List<Caracteristica> caracteristicas = mascotaRegistrada.getCaracteristicas();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(caracteristicas, 1, 1, 0, 0));
  }
}
