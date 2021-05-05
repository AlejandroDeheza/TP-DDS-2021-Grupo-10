package modelo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicios.repositorios.RepositorioCaracteristicas;
import servicios.repositorios.RepositorioInformes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CaracteristicasTest {

	Mascota beto;
	DuenioMascota pablo;
	Persona pabloPersona;
	DatosDeContacto datosPablo;
	DatosDeContacto datosAdmin;
	Persona adminPersona;
	Administrador admin;

	@BeforeEach
	public void contextTest(){

		datosAdmin = new DatosDeContacto(null, "admin@mail.com");
		adminPersona = new Persona("admin3", "admin", TipoDocumento.DNI, "1000001", datosAdmin, LocalDate.of(1995, 8, 7) );
		admin = new Administrador("eladmin3","ElC4p0.123",adminPersona);

	}

	@Test
	public void unAdministradorPuedeAgregarCaracteristicas(){
		List<String> valoresPosible = Arrays.asList("SI", "NO");
		Caracteristica caracteristica = new Caracteristica("Castrado",valoresPosible);
		admin.agregarCaracteristica(caracteristica);
	}

}
