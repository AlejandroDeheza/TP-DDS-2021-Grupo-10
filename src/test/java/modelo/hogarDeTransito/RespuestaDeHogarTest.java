package modelo.hogarDeTransito;

import modelo.hogarDeTransito.respuestas.RespuestaDeAdmision;
import modelo.hogarDeTransito.respuestas.RespuestaDeHogar;
import modelo.informe.Ubicacion;
import modelo.mascota.Animal;
import modelo.mascota.TamanioMascota;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

public class RespuestaDeHogarTest {
  Ubicacion ubicacionEncuentro,ubicacionHogar;
  RespuestaDeHogar respuestaDeHogar = new RespuestaDeHogar();
  CaracteristicaConValoresPosibles caracteristicasPosiblesTamanio;
  CaracteristicaConValoresPosibles caracteristicasPosiblesComportamiento;
  TamanioMascota tamanioMascota;
  Caracteristica caracteristicaComportamiento;
  List<Caracteristica> listaCaracteristica = new ArrayList<>();
  RespuestaDeAdmision respuestaDeAdmision;
  RepositorioCaracteristicas repositorioCaracteristicas=new RepositorioCaracteristicas();

  @BeforeEach
  public void contextLoad() {
    ubicacionEncuentro = new Ubicacion(70.0, 70.0);
    ubicacionHogar = new Ubicacion(70.1, 70.1);
    caracteristicasPosiblesTamanio = new CaracteristicaConValoresPosibles("tamaño", Arrays.asList("pequeño", "Mediano", "Grande"));
    caracteristicasPosiblesComportamiento = new CaracteristicaConValoresPosibles("Comportamiento", Arrays.asList("Inquieto", "Tranquilo"));
    repositorioCaracteristicas.agregarCaracteristica(caracteristicasPosiblesTamanio);
    repositorioCaracteristicas.agregarCaracteristica(caracteristicasPosiblesComportamiento);
    tamanioMascota = TamanioMascota.CHICO;
    caracteristicaComportamiento = new Caracteristica("Comportamiento", "Inquieto",repositorioCaracteristicas);
    respuestaDeAdmision = new RespuestaDeAdmision();
    respuestaDeAdmision.setAceptaGato(true);
    respuestaDeAdmision.setAceptaPerro(true);
  }

  @Test
  @DisplayName("La mascota cumple todos los requisitos de un hogar")
  public void esPosibleHogarDeTransitoConUnRadioCercano() {
    Integer radioCercania = 1000;
    Animal animal = Animal.PERRO;

    listaCaracteristica.add(caracteristicaComportamiento);
    respuestaDeHogar.setCapacidad(2);
    respuestaDeHogar.setLugaresDisponibles(2);
    respuestaDeHogar.setCaracteristicas(convertirListaCaracteristicasAListaStrings(listaCaracteristica));
    respuestaDeHogar.setAdmisiones(respuestaDeAdmision);
    respuestaDeHogar.setUbicacion(ubicacionHogar);
    respuestaDeHogar.setTienePatio(false);

    assertTrue(respuestaDeHogar.estaDisponible(ubicacionEncuentro, radioCercania, animal, tamanioMascota, listaCaracteristica));

  }


  @Test
  @DisplayName("La mascota cumple todos los requisitos de un hogar")
  public void noEsPosibleHogarDeTransitoFueraDeRadioCercano() {
    Integer radioCercania = 1;
    Animal animal = Animal.PERRO;

    listaCaracteristica.add(caracteristicaComportamiento);
    respuestaDeHogar.setCapacidad(2);
    respuestaDeHogar.setLugaresDisponibles(2);
    respuestaDeHogar.setCaracteristicas(convertirListaCaracteristicasAListaStrings(listaCaracteristica));
    respuestaDeHogar.setAdmisiones(respuestaDeAdmision);
    respuestaDeHogar.setUbicacion(ubicacionHogar);
    respuestaDeHogar.setTienePatio(false);

    assertFalse(respuestaDeHogar.estaDisponible(ubicacionEncuentro, radioCercania, animal, tamanioMascota, listaCaracteristica));

  }



  private List<String> convertirListaCaracteristicasAListaStrings(List<Caracteristica> listaCaracteristica) {
    List<String> valoresCaracteristicasMascota =listaCaracteristica.stream().

  map(caracteristica ->caracteristica.getValorCaracteristica()).

  collect(Collectors.toList());
  return valoresCaracteristicasMascota;
}
}
