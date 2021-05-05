package servicios.repositorios;

import excepciones.InformeMascotaEncontradaInvalidaException;
import modelo.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioInformesTest {

    InformeMascotaEncontrada informe;
    RepositorioInformes repositorioInformes;
    List<Foto> fotosMascota;
    Foto foto;


    @BeforeEach
    public void contextLoad() {
        repositorioInformes = RepositorioInformes.getInstance();

        InformeQR informeQR = new InformeQR(
            new DuenioMascota(null, "dadaasssddddaa", null, null),
            new Mascota());
        Persona rescatista = new Persona(
            null, null, null, null);
        LocalDate fechaDeHoy = LocalDate.now();
        String direccion = "Av. Corrientes 576";
        fotosMascota = new ArrayList<>();
        foto = new Foto();
        fotosMascota.add(foto);
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

    @Test
    public void procesarInformeAgregaYRemueveElInformeCorrespondiente(){
        Assertions.assertEquals(repositorioInformes.getInformesProcesados().size(), 0);
        repositorioInformes.procesarInforme(informe);
        Assertions.assertEquals(repositorioInformes.getInformesPendientes().size(), 0);
        Assertions.assertEquals(repositorioInformes.getInformesProcesados().size(), 1);
    }


    public InformeMascotaEncontrada crearConstructorSinFoto(){
        repositorioInformes = RepositorioInformes.getInstance();
        InformeQR informeQR = new InformeQR(
            new DuenioMascota(null, "dadaasssddddaa", null, null),
            new Mascota());
        Persona rescatista = new Persona(
            null, null, null, null);
        LocalDate fechaDeHoy = LocalDate.now();
        String direccion = "Av. Corrientes 576";
        fotosMascota = new ArrayList<>();
        Ubicacion ubicacion = new Ubicacion("57,44","57,55");
        String estadoActualMascota = "Bien de salud, pero asustado";
        informe = new InformeMascotaEncontrada(informeQR,rescatista,fechaDeHoy,direccion,fotosMascota,ubicacion,estadoActualMascota);
        return informe;
    }

}
