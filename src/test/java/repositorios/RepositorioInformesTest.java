package repositorios;

import excepciones.DuenioMascotaMascotaRegistradaException;
import excepciones.InformeMascotaEncontradaInvalidaException;
import modelo.informe.InformeMascotaEncontrada;
import modelo.informe.Ubicacion;
import modelo.mascota.Foto;
import modelo.mascota.Mascota;
import modelo.persona.Persona;
import modelo.usuario.Administrador;
import modelo.usuario.DuenioMascota;
import org.junit.jupiter.api.*;
import utils.DummyData;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioInformesTest {

    RepositorioInformes repositorioInformes;

    DuenioMascota duenioMascota = DummyData.getDummyDuenioMascota();
    Persona rescatista = DummyData.getDummyPersona();
    LocalDate fechaDeHoy = LocalDate.now();
    String direccion = "Av. Corrientes 576";
    List<Foto> fotosMascota = DummyData.getDummyFotosMascota();
    Ubicacion ubicacion = new Ubicacion("57,44","57,55");
    String estadoActualMascota = "Bien de salud, pero asustado";

    @BeforeEach
    public void contextLoad() {
        repositorioInformes = RepositorioInformes.getInstance();
        Administrador admin = DummyData.getDummyAdministrador();
        admin.agregarCaracteristica(DummyData.getDummyCaracteristicaParaAdmin());
    }

    @AfterEach
    public void destroyContext() throws NoSuchFieldException, IllegalAccessException{
        Field instance = RepositorioInformes.class.getDeclaredField("repositorioInformes");
        instance.setAccessible(true);
        instance.set(null,null);
    }

    @Test
    @DisplayName("si se crea un InformeMascotaPerdida, se agrega un informe a InformesPendientes " +
        "en RepositorioInformes ")
    public void InformesPendientesTest() {
        Assertions.assertEquals(repositorioInformes.getInformesPendientes().size(), 0);
        new InformeMascotaEncontrada(
                duenioMascota, rescatista, fechaDeHoy, direccion, fotosMascota, ubicacion, estadoActualMascota);
        Assertions.assertEquals(repositorioInformes.getInformesPendientes().size(), 1);
    }

    @Test
    @DisplayName("si se utiliza listarMascotasEncontradasEnLosUltimos10Dias(), este devuelve " +
        "un registro insertado previamente")
    public void listarMascotasEncontradasEnLosUltimos10DiasTest(){
        Assertions.assertEquals(
            repositorioInformes.listarMascotasEncontradasEnUltimosNDias(10).size(),0);
        new InformeMascotaEncontrada(
                duenioMascota, rescatista, fechaDeHoy, direccion, fotosMascota, ubicacion, estadoActualMascota);
        Assertions.assertEquals(
            repositorioInformes.listarMascotasEncontradasEnUltimosNDias(10).size(),1);
    }

    @Test
    @DisplayName("si se genera un InformeMascotaEncontrada sin fotos, se genera " +
        "InformeMascotaEncontradaInvalidaException")
    public void InformeMascotaEncontradaInvalidaExceptionTest(){
        Assertions.assertThrows(
            InformeMascotaEncontradaInvalidaException.class, this::generarInformeMascotaEncontradaSinFoto);
    }

    public void generarInformeMascotaEncontradaSinFoto(){
        fotosMascota = new ArrayList<>();
        new InformeMascotaEncontrada(
            duenioMascota,rescatista,fechaDeHoy,direccion,fotosMascota,ubicacion,estadoActualMascota);
    }

    //test de duenio mascota, esta aca para no repetir codigo
    @Test
    @DisplayName("si un DuenioMascota intenta registrar a la misma mascota 2 veces, se genera " +
        "DuenioMascotaMascotaRegistradaException")
    public void DuenioMascotaMascotaRegistradaExceptionTest() {
        DuenioMascota duenioMascota = DummyData.getDummyDuenioMascota();
        Mascota mascota1 = DummyData.getDummyMascota();
        duenioMascota.agregarMascota(mascota1);
        Assertions.assertThrows(DuenioMascotaMascotaRegistradaException.class,
            () -> duenioMascota.agregarMascota(mascota1));
    }

}
