package modelo.persona;

import excepciones.DatosDeContactoIncompletosException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;
import java.time.LocalDate;

public class PersonaTest {

	@Test
	@DisplayName("una Persona valida no genera problemas")
	public void personaValidaTest() {
		Assertions.assertDoesNotThrow(() -> DummyData.getDummyPersona());
	}

	@Test
	@DisplayName("otra Persona valida no genera problemas")
	public void personaValidaTest2(){
		Assertions.assertDoesNotThrow(
				() -> new Persona(
						"Emi","Mazzaglia", TipoDocumento.DNI,"35353535",
						new DatosDeContacto(null,null), LocalDate.now())
		);
	}

	@Test
	@DisplayName("una Persona sin DatosDeContacto genera NullPointerException")
	public void personaSinDatosDeContactoTest() {
		Assertions.assertThrows(NullPointerException.class, () -> DummyData.getDummyPersonaSinDatosDeContacto());
	}

	@Test
	@DisplayName("una Persona sin DatosDeContacto ni nombre y apellido genera DatosDeContactoIncompletosException")
	public void DatosDeContactoIncompletosExceptionTest() {
		Assertions.assertThrows(DatosDeContactoIncompletosException.class,
				() -> DummyData.getDummyPersonaSinDatosDeContactoNiNombreNiApellido());
	}

}
