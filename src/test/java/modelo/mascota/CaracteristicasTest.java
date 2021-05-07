package modelo.mascota;

import excepciones.CaracteristicasVacioException;
import excepciones.ValorCaracteristicaIncompatibleException;
import modelo.mascota.Animal;
import modelo.mascota.Mascota;
import modelo.mascota.Sexo;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.DatosDeContacto;
import modelo.persona.Persona;
import modelo.persona.TipoDocumento;
import modelo.usuario.Administrador;
import modelo.usuario.DuenioMascota;
import org.junit.jupiter.api.*;
import repositorios.RepositorioCaracteristicas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CaracteristicasTest {
	  RepositorioCaracteristicas respositorioCaracteristicas= RepositorioCaracteristicas.getInstance();
	  Mascota beto;
	  Persona pabloPersona;
	  DatosDeContacto datosPablo;
	  DatosDeContacto datosAdmin;
	  Persona adminPersona;
	  Administrador admin;
	  DuenioMascota duenioMascota;

	@BeforeEach
	public void contextTest(){
		duenioMascota = crearDuenioMascota();
		datosAdmin = new DatosDeContacto(null, "admin@mail.com");
		adminPersona = new Persona("admin3", "admin", TipoDocumento.DNI, "1000001", datosAdmin, LocalDate.of(1995, 8, 7) );
		admin = new Administrador("eladmin3","ElC4p0.123",adminPersona);
	}

	@Test
	public void unAdministradorPuedeAgregarCaracteristicas(){
		List<String> valoresPosible = Arrays.asList("SI", "NO");
		Caracteristica caracteristica = new Caracteristica("Castrado",valoresPosible);
		admin.agregarCaracteristica(caracteristica);
		Assertions.assertTrue(respositorioCaracteristicas.getListaCaracteristicas().contains(caracteristica));
	}

	@Test
	public void unMascotaTieneCaracteristicaInvalida(){
		Caracteristica caracteristica= new Caracteristica("Dormilon", Collections.singletonList("SI"));
		Mascota mascota=crearMascota(caracteristica);
		Assertions.assertThrows(CaracteristicasVacioException.class,()->duenioMascota.agregarMascota(mascota));
	}

	@Test
	@DisplayName("Persona asigna un valor invalido a una caracterstica previamente cargada lanza una exception")
	public void personaAsignaValorInvalidoACaractersticaGeneraUnaException(){
		Caracteristica caracteristica= new Caracteristica("Castrado", Collections.singletonList("Maso"));
		List<Caracteristica> listaCaracteristica=new ArrayList<>();
		listaCaracteristica.add(caracteristica);
		beto = new Mascota(Animal.PERRO, "beto", "jp", LocalDate.of(2018,3, 4), Sexo.MACHO, "mancito", listaCaracteristica, null );

		Assertions.assertThrows(ValorCaracteristicaIncompatibleException.class,()->duenioMascota.agregarMascota(beto));
	}

	private Mascota crearMascota(Caracteristica caracteristica) {
		List<Caracteristica> listaCaracteristica=new ArrayList<>();
		listaCaracteristica.add(caracteristica);
		return new Mascota(Animal.PERRO, "beto", "jp", LocalDate.of(2018,3, 4), Sexo.MACHO, "mancito", listaCaracteristica, null );
	}

	private DuenioMascota crearDuenioMascota() {
		datosPablo = new DatosDeContacto(null, "pablo@mail.com");
		pabloPersona = new Persona("pablo", "Hernandez", TipoDocumento.DNI, "43212098", datosPablo, LocalDate.of(1995, 8, 7));
		return new DuenioMascota("pepe6", "P3p3.3210", pabloPersona);
	}
}
