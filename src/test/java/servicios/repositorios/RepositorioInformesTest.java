package servicios.repositorios;

import excepciones.InformeMascotaEncontradaInvalidaException;
import modelo.*;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioInformesTest {

    InformeMascotaEncontrada informe;
    RepositorioInformes repositorioInformes;
    List<Foto> fotosMascota;
    Foto foto;
    Mascota beto;
    DatosDeContacto datosRecatista;
    static DuenioMascota pablo;
    static DatosDeContacto datosPablo;
    static Persona pabloPersona;

    @BeforeAll
    public static void crearUsuarios(){
        datosPablo = new DatosDeContacto(null, "pablo@mail.com");
        pabloPersona = new Persona("pablo", "Hernandez", TipoDocumento.DNI, "43212098", datosPablo, LocalDate.of(1995, 8, 7) );
        pablo=new DuenioMascota("pepe","P3p3.3210",pabloPersona);

    }
    @BeforeEach
    public void contextLoad() {
        repositorioInformes = RepositorioInformes.getInstance();
        fotosMascota = new ArrayList<>();
        foto = new Foto();
        fotosMascota.add(foto);
        beto = new Mascota(Animal.PERRO, "pablo", "jp", LocalDate.of(2018,3, 4), Sexo.MACHO, "mancito", null, fotosMascota );
        datosRecatista = new DatosDeContacto(null, "jose@mail.com");
        Persona rescatista = new Persona("jose", "hernandez", TipoDocumento.DNI, "43212098", datosRecatista, LocalDate.of(1995, 8, 7));
        pablo.agregarMascota(beto);
        InformeQR informeQR = new InformeQR(pablo,beto);


        LocalDate fechaDeHoy = LocalDate.now();
        String direccion = "Av. Corrientes 576";

        Ubicacion ubicacion = new Ubicacion("57,44","57,55");
        String estadoActualMascota = "Bien de salud, pero asustado";
        informe = new InformeMascotaEncontrada(informeQR,rescatista,fechaDeHoy,direccion,fotosMascota,ubicacion,estadoActualMascota);
    }

    @AfterEach
    public void destroyContext() throws NoSuchFieldException, IllegalAccessException{
        Field instance = RepositorioInformes.class.getDeclaredField("repositorioInformes");
        instance.setAccessible(true);
        instance.set(null,null);
    }

    @Test
    public void registrarUnaMascotaPerdidaAgregaInformeAInformesPendientes() {
        Assertions.assertEquals(repositorioInformes.getInformesPendientes().size(), 0);
        repositorioInformes.agregarInformeMascotaEncontrada(informe);
        Assertions.assertEquals(repositorioInformes.getInformesPendientes().size(), 1);
    }

    @Test
    public void listarMascotasEncontradasEnLosUltimos10DiasDevuelveUnRegistroInsertadoPreviamente(){
        Assertions.assertEquals(repositorioInformes.listarMascotasEncontradasEnUltimosNDias(10).size(),0);
        repositorioInformes.agregarInformeMascotaEncontrada(informe);
        Assertions.assertEquals(repositorioInformes.listarMascotasEncontradasEnUltimosNDias(10).size(),1);
    }

    @Test
    public void registrarInformeMascotaEncontradaSinFoto(){
        Assertions.assertThrows(InformeMascotaEncontradaInvalidaException.class,() -> {
            repositorioInformes.agregarInformeMascotaEncontrada(crearConstructorSinFoto());
        });

    }

    public InformeMascotaEncontrada crearConstructorSinFoto(){
        repositorioInformes = RepositorioInformes.getInstance();
        InformeQR informeQR = new InformeQR(pablo,beto);
        Persona rescatista = new Persona("jose", "hernandez", TipoDocumento.DNI, "43212098", datosRecatista, LocalDate.of(1995, 8, 7));

        LocalDate fechaDeHoy = LocalDate.now();
        String direccion = "Av. Corrientes 576";
        fotosMascota = new ArrayList<>();
        Ubicacion ubicacion = new Ubicacion("57,44","57,55");
        String estadoActualMascota = "Bien de salud, pero asustado";
        informe = new InformeMascotaEncontrada(informeQR,rescatista,fechaDeHoy,direccion,fotosMascota,ubicacion,estadoActualMascota);
        return informe;
    }

}
