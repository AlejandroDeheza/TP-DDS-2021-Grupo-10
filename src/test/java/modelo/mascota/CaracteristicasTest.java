package modelo.mascota;

import excepciones.CaracteristicasInvalidaException;
import excepciones.ValorCaracteristicaIncompatibleException;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.usuario.Administrador;
import org.junit.jupiter.api.*;
import repositorios.RepositorioCaracteristicas;
import utils.DummyData;

public class CaracteristicasTest {

	Administrador admin = DummyData.getDummyAdministrador();
	CaracteristicaConValoresPosibles caracteristica = DummyData.getDummyCaracteristicaParaAdmin();

	@BeforeEach
	public void contextLoad() {
		admin.agregarCaracteristica(caracteristica);
	}

	@Test
	@DisplayName("si un administrador ingresa una caracterstica nueva, esta se guarda en RepositorioCaracteristicas")
	public void administradorCaracteristicaTest(){
		Assertions.assertTrue(RepositorioCaracteristicas.getInstance()
				.getCaracteristicas().contains(caracteristica));
	}

	@Test
	@DisplayName("si un usuario ingresa una caracterstica valida, no se genera ningun problema")
	public void caracteristicaValidaTest(){
		Assertions.assertDoesNotThrow(
				() -> new Caracteristica("Comportamiento", "Bueno"));
	}

	@Test
	@DisplayName("si un usuario ingresa una caracterstica invalida, se genera CaracteristicasInvalidaException")
	public void CaracteristicasInvalidaExceptionTest(){
		Assertions.assertThrows(
				CaracteristicasInvalidaException.class,
				() -> new Caracteristica("Dormilon", "SI"));
	}

	@Test
	@DisplayName("si un usuario asigna un valor INVALIDO a una caracterstica VALIDA, se genera" +
					"ValorCaracteristicaIncompatibleException")
	public void ValorCaracteristicaIncompatibleExceptionTest(){
		Assertions.assertThrows(
				ValorCaracteristicaIncompatibleException.class,
				() -> new Caracteristica("Comportamiento", "Maso"));
	}

}
