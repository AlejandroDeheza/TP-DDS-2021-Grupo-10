package modelo.hogarDeTransito;

import modelo.hogarDeTransito.respuestas.RespuestaDeAdmision;
import modelo.hogarDeTransito.respuestas.RespuestaDeHogar;
import modelo.informe.Ubicacion;
import modelo.mascota.Animal;
import modelo.mascota.TamanioMascota;
import modelo.mascota.caracteristica.Caracteristica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import utils.DummyData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class RespuestaDeHogarTest {
  Ubicacion ubicacionEncuentro;
  RespuestaDeHogar respuestaDeHogar;
  TamanioMascota tamanio;
  List<Caracteristica> caracteristicas;
  Animal animal;

  @BeforeEach
  public void contextLoad() {
    caracteristicas = DummyData.getCaracteristicasParaMascota();
    ubicacionEncuentro = new Ubicacion(70.0, 70.0, null);
    animal = Animal.PERRO;
    tamanio = TamanioMascota.CHICO;
    respuestaDeHogar = new RespuestaDeHogar(null, null, new Ubicacion(70.1, 70.1, null),
        null, new RespuestaDeAdmision(true, false), null, 2,
        false, getValores(caracteristicas));
  }

  @Test
  @DisplayName("Si se cumplen todos los requisitos, el hogar esta disponible")
  public void siSeCumplenTodosLosRequisitosElHogarEstaDisponible() {
    assertTrue(respuestaDeHogar.estaDisponible(ubicacionEncuentro, 1000, animal, tamanio, caracteristicas));
  }

  @Test
  @DisplayName("Si el hogar esta muy lejos para el rescatista, entonces no esta disponible")
  public void fueraDeRadio() {
    assertFalse(respuestaDeHogar.estaDisponible(ubicacionEncuentro, 1, animal, tamanio, caracteristicas));
  }

  @Test
  @DisplayName("Si la mascota es grande y el hogar no tiene patio, entonces no esta disponible")
  public void mascotaGrande() {
    assertFalse(respuestaDeHogar.estaDisponible(ubicacionEncuentro, 1000, animal, TamanioMascota.GRANDE,
        caracteristicas));
  }

  @Test
  @DisplayName("Si el hogar no acepta el tipo de mascota, entonces no esta disponible")
  public void noAceptaGatos() {
    assertFalse(respuestaDeHogar.estaDisponible(ubicacionEncuentro, 1000, Animal.GATO, tamanio,
        caracteristicas));
  }

  @Test
  @DisplayName("Si la mascota no cumple con las caracteristicas que pide el hogar, entonces no esta disponible")
  public void noCumpleCaracteristicasEspecificas() {
    assertFalse(respuestaDeHogar.estaDisponible(ubicacionEncuentro, 1000, animal, tamanio,
        new ArrayList<>()));
  }

  private List<String> getValores(List<Caracteristica> listaCaracteristica) {
    return listaCaracteristica
        .stream()
        .map(Caracteristica::getValorCaracteristica)
        .collect(Collectors.toList());
  }
}
