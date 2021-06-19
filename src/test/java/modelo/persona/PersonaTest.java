package modelo.persona;

import excepciones.DatosDeContactoIncompletosException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import java.time.LocalDate;

public class PersonaTest {

  private String nombre = "Emi";
  private String apellido = "Mazzaglia";
  private DocumentoIdentidad documentoIdentidad =
      new DocumentoIdentidad(TipoDocumento.DNI, "35353535");
  private DatosDeContacto datosDeContacto =
      new DatosDeContacto("0123456789", "emimazzaglia@gmail.com");
  private LocalDate fechaNacimiento = LocalDate.now();
  private Persona persona;

  @BeforeEach
  public void contextLoad() {
    persona = generarPersonaBuilder();
  }

  @Test
  @DisplayName("Chequeo igualdad entre Constructor y Builder")
  public void PersonaBuilderConstructorTest() {
    Persona personaAux = this.generarPersona();
    Assertions.assertEquals(persona.getNombre(), personaAux.getNombre());
    Assertions.assertEquals(persona.getApellido(), personaAux.getApellido());
    Assertions.assertEquals(persona.getDocumentoIdentidad().getTipoDocumento(), personaAux.getDocumentoIdentidad().getTipoDocumento());
    Assertions.assertEquals(persona.getDocumentoIdentidad().getNumeroDocumento(), personaAux.getDocumentoIdentidad().getNumeroDocumento());
    Assertions.assertEquals(persona.getDatosDeContacto(), personaAux.getDatosDeContacto());
    Assertions.assertEquals(persona.getFechaNacimiento(), personaAux.getFechaNacimiento());
  }

  @Test
  @DisplayName("si se crea una Persona valida, no se generan problemas")
  public void personaValidaTest() {
    assertDoesNotThrow(DummyData::getDummyPersona);
  }

  @Test
  @DisplayName("si se crea otra Persona valida, sin telefono ni mail, no se generan problemas")
  public void personaValidaTest2() {
    assertDoesNotThrow(() -> this.generarPersonaBuilder());
  }

  @Test
  @DisplayName("si se crea una Persona sin referencia a DatosDeContacto, se genera NullPointerException")
  public void personaSinDatosDeContactoTest() {
    assertThrows(NullPointerException.class, DummyData::getDummyPersonaSinDatosDeContacto);
  }

  @Test
  @DisplayName("si se crea una Persona sin telefono, ni mail, ni nombre y apellido, se genera "
      + "DatosDeContactoIncompletosException")
  public void DatosDeContactoIncompletosExceptionTest() {
    assertThrows(DatosDeContactoIncompletosException.class,
        DummyData::getDummyPersonaSinDatosDeContactoNiNombreNiApellido);
  }

  @Test
  public void UnaPersonaDebeTenerUnCorreoAsociadoEnSuDatoDeContactoTest() {
    assertThrows(DatosDeContactoIncompletosException.class,
        () -> DummyData.getDummyPersonaSinCorreoAsociadoEnDatosDeContacto());
  }

  private Persona generarPersonaBuilder() {
    PersonaBuilder builder = PersonaBuilder.crearBuilder();
    builder.conNombre(nombre).conApellido(apellido)
        .conDocumentoIdentidad(documentoIdentidad)
        .conDatosDeContacto(datosDeContacto)
        .conFechaNacimiento(fechaNacimiento);
    return builder.build();
  }

  private Persona generarPersona() {
    return new Persona(this.nombre, this.apellido, this.documentoIdentidad,
        this.datosDeContacto, this.fechaNacimiento);
  }

}
