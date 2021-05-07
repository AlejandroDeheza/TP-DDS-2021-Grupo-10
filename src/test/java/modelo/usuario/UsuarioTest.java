package modelo.usuario;

import excepciones.DatosDeContactoIncompletosException;
import modelo.persona.DatosDeContacto;
import modelo.persona.Persona;
import modelo.persona.TipoDocumento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import java.time.LocalDate;

public class UsuarioTest {

  private static DuenioMascota pablo;
  private static Persona pabloPersona;
  private static DatosDeContacto datosPablo;
  private static Persona damianPersona;
  private static DatosDeContacto datosDamian;
  private static Administrador damian;

  @BeforeAll
  public static void contextLoad() {
    datosPablo = new DatosDeContacto(null, "pablo@mail.com");
    pabloPersona = new Persona("pablo", "Hernandez", TipoDocumento.DNI, "43212098", datosPablo, LocalDate.of(1995, 8, 7) );
    pablo =new DuenioMascota("pepe3","P3p3.3210",pabloPersona);
    datosDamian = new DatosDeContacto("1143091234", null);
    damianPersona = new Persona("damian", "perez", TipoDocumento.DNI, "49102921", datosDamian, LocalDate.of(2001,4,1) );
    damian = new Administrador("damian", "Awqde1oP", damianPersona);
  }

  @Test
  @DisplayName("se puede crear un usuario valido")
  public void sePuedeCrearUnUsuarioValido(){
    Assertions.assertDoesNotThrow(() -> DummyData.getDummyDuenioMascota());
  }

  @Test
  @DisplayName("un usuario dummy se puede autenticar correctamente")
  public void unUsuarioDummySePuedeAutenticarCorrectamente(){
    DuenioMascota duenioMascota = DummyData.getDummyDuenioMascota();
    Assertions.assertDoesNotThrow(() -> duenioMascota.autenticarUsuario("Password1234"));
  }

  @Test
  public void sePuedeCrearUnUsuarioAdministrador(){
    Assertions.assertSame(Administrador.class, damian.getClass());
  }

  @Test
  public void crearSinDatoDeContacto() {
    Assertions.assertThrows(DatosDeContactoIncompletosException.class,()->new Persona(null, null, TipoDocumento.DNI, "43212098", new DatosDeContacto(null,null), LocalDate.of(1995, 8, 7) ));
  }

}
