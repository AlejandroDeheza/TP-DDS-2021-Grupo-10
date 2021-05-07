package modelo.persona;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

public class PersonaTest {
	@Test
	@DisplayName("test getters de Persona")
	public void creacionPersona() {
		Persona persona = DummyData.getDummyPersona();
		Assertions.assertSame(persona.getApellido(),DummyData.getDummyPersona().getApellido());
		Assertions.assertSame(persona.getNombre(),DummyData.getDummyPersona().getNombre());
		Assertions.assertEquals(persona.getFechaNacimiento(),DummyData.getDummyPersona().getFechaNacimiento());
		Assertions.assertSame(persona.getTipoDocumento(),DummyData.getDummyPersona().getTipoDocumento());
		Assertions.assertSame(persona.getNumeroDeDocumento(),DummyData.getDummyPersona().getNumeroDeDocumento());
	}

}
