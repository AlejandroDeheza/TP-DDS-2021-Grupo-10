package modelo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class MascotaTest {

    private static Mascota beto;
    private static DuenioMascota pablo;
    private static Persona pabloPersona;


    @BeforeAll
    public static void contextTest(){
        pabloPersona = new Persona("pablo", "Hernandez", TipoDocumento.DNI, "43212098", null, LocalDate.of(1995, 8, 7) );
        pablo =new DuenioMascota("pepe2","P3p3.3210",pabloPersona);
        beto = new Mascota(Animal.PERRO, "beto", "jp", LocalDate.of(2018,3, 4), Sexo.MACHO, "mancito", null, null );
        pablo.agregarMascota(beto);
    }

    @Test
    public void testCrearMascota() {
        Assertions.assertEquals("beto",beto.getNombre());
    }

    @Test
    public void testDuenioAdoptaAMascota() {
        Assertions.assertTrue(pablo.getListaMascotas().contains(beto));
    }
}
