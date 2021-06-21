package modelo.hogares;

import modelo.informe.Ubicacion;
import modelo.mascota.Animal;
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

public class HogarTest {
  Ubicacion ubicacionEncuentro,ubicacionHogar;
  Hogar hogar = new Hogar();
  CaracteristicaConValoresPosibles caracteristicasPosiblesTamanio;
  CaracteristicaConValoresPosibles caracteristicasPosiblesComportamiento;
  Caracteristica caracteristicaTamanio;
  Caracteristica caracteristicaComportamiento;
  List<Caracteristica> listaCaracteristica = new ArrayList<>();
  Admision admision;
  RepositorioCaracteristicas repositorioCaracteristicas=new RepositorioCaracteristicas();

  @BeforeEach
  public void contextLoad() {
    ubicacionEncuentro = new Ubicacion(70.0, 70.0);
    ubicacionHogar = new Ubicacion(70.1, 70.1);
    caracteristicasPosiblesTamanio = new CaracteristicaConValoresPosibles("tamaño", Arrays.asList("pequeño", "Mediano", "Grande"));
    caracteristicasPosiblesComportamiento = new CaracteristicaConValoresPosibles("Comportamiento", Arrays.asList("Inquieto", "Tranquilo"));
    repositorioCaracteristicas.agregarCaracteristica(caracteristicasPosiblesTamanio);
    repositorioCaracteristicas.agregarCaracteristica(caracteristicasPosiblesComportamiento);
    caracteristicaTamanio = new Caracteristica("tamaño", "pequeño",repositorioCaracteristicas);
    caracteristicaComportamiento = new Caracteristica("Comportamiento", "Inquieto",repositorioCaracteristicas);
    admision = new Admision();
    admision.setAceptaGato(true);
    admision.setAceptaPerro(true);
  }

  @Test
  @DisplayName("La mascota cumple todos los requisitos de un hogar")
  public void esPosibleHogarDeTransitoConUnRadioCercano() {
    Integer radioCercania = 1000;
    Animal animal = Animal.PERRO;

    listaCaracteristica.add(caracteristicaTamanio);
    listaCaracteristica.add(caracteristicaComportamiento);
    hogar.setCapacidad(2);
    hogar.setLugaresDisponibles(2);
    hogar.setCaracteristicas(convertirListaCaracteristicasAListaStrings(listaCaracteristica));
    hogar.setAdmisiones(admision);
    hogar.setUbicacion(ubicacionHogar);
    hogar.setTienePatio(false);

    assertTrue(hogar.esPosibleHogarDeTransito(radioCercania,animal,listaCaracteristica,ubicacionEncuentro));

  }


  @Test
  @DisplayName("La mascota cumple todos los requisitos de un hogar")
  public void noEsPosibleHogarDeTransitoFueraDeRadioCercano() {
    Integer radioCercania = 1;
    Animal animal = Animal.PERRO;

    listaCaracteristica.add(caracteristicaTamanio);
    listaCaracteristica.add(caracteristicaComportamiento);
    hogar.setCapacidad(2);
    hogar.setLugaresDisponibles(2);
    hogar.setCaracteristicas(convertirListaCaracteristicasAListaStrings(listaCaracteristica));
    hogar.setAdmisiones(admision);
    hogar.setUbicacion(ubicacionHogar);
    hogar.setTienePatio(false);

    assertFalse(hogar.esPosibleHogarDeTransito(radioCercania,animal,listaCaracteristica,ubicacionEncuentro));

  }



  private List<String> convertirListaCaracteristicasAListaStrings(List<Caracteristica> listaCaracteristica) {
    List<String> valoresCaracteristicasMascota =listaCaracteristica.stream().

  map(caracteristica ->caracteristica.getValorCaracteristica()).

  collect(Collectors.toList());
  return valoresCaracteristicasMascota;
}
}
