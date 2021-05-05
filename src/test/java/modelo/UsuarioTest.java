package modelo;

import excepciones.DatosDeContactoIncompletos;
import excepciones.LoginInvalidoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
    datosDamian = new DatosDeContacto(1143091234, null);
    damianPersona = new Persona("damian", "perez", TipoDocumento.DNI, "49102921", datosDamian, LocalDate.of(2001,4,1) );
    damian = new Administrador("damian", "Awqde1oP", damianPersona);
  }

  @Test
  public void testCrearDosUsuariosMismoLogin() {
    Assertions.assertThrows(LoginInvalidoException.class,()->new DuenioMascota("pepe3","P3p3.3210",pabloPersona));
  }


  @Test
  public void testCrearUsuarioAdministrador(){

    Assertions.assertSame(Administrador.class, damian.getClass());

  }

  @Test
  public void testCrearSinDatoDeContacto() {
    Assertions.assertThrows(DatosDeContactoIncompletos.class,()->new Persona(null, null, TipoDocumento.DNI, "43212098", new DatosDeContacto(), LocalDate.of(1995, 8, 7) ));
  }

}
