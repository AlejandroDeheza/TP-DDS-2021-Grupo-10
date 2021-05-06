package modelo.mascota;

import modelo.mascota.Animal;
import modelo.mascota.Mascota;
import modelo.mascota.Sexo;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.DatosDeContacto;
import modelo.persona.Persona;
import modelo.persona.TipoDocumento;
import modelo.usuario.Administrador;
import modelo.usuario.DuenioMascota;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MascotaTest {

    private static Mascota beto;
    private static DuenioMascota pablo;
    private static Persona pabloPersona;
    private static DatosDeContacto datosPablo;
    private static DatosDeContacto datosAdmin;
    private static Persona adminPersona;
    private static Administrador admin;

    @BeforeAll
    public static void contextTest(){
        datosAdmin = new DatosDeContacto(null, "admin@mail.com");
        adminPersona = new Persona("admin", "admin", TipoDocumento.DNI, "1000001", datosAdmin, LocalDate.of(1995, 8, 7) );
        datosPablo = new DatosDeContacto(null, "pablo@mail.com");
        pabloPersona = new Persona("pablo", "Hernandez", TipoDocumento.DNI, "43212098", datosPablo, LocalDate.of(1995, 8, 7) );
        pablo =new DuenioMascota("pepe2","P3p3.3210",pabloPersona);
        admin = new Administrador("eladmin","ElC4p0.123",adminPersona);
        List<String> valoresPosible = Arrays.asList("Bueno", "Malo", "Mediocre");
        Caracteristica caracteristicaPosible = new Caracteristica("Comportamiento",valoresPosible);
        admin.agregarCaracteristica(caracteristicaPosible);
        Caracteristica caracteristica= new Caracteristica("Comportamiento", Collections.singletonList("Bueno"));
        List<Caracteristica> listaCaracteristica=new ArrayList<>();
        listaCaracteristica.add(caracteristica);
        beto = new Mascota(Animal.PERRO, "beto", "jp", LocalDate.of(2018,3, 4), Sexo.MACHO, "mancito", listaCaracteristica, null );
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
