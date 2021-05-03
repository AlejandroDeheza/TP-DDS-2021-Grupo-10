package servicios.repositorios;

import modelo.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InformeRepositoryTest {

    InformeMascotaEncontrada informe;
    InformesRepository informesRepository;

    @BeforeEach
    public void contextLoad() {
        informesRepository = InformesRepository.getInstance();
        InformeQR informeQR = new InformeQR(new DuenioMascota(),new Mascota());
        Persona rescatista = new Persona();
        LocalDate fechaDeHoy = LocalDate.now();
        String direccion = "Av. Corrientes 576";
        List<Foto> fotosMascota = new ArrayList<>();
        Ubicacion ubicacion = new Ubicacion("57,44","57,55");
        String estadoActualMascota = "Bien de salud, pero asustado";
        informe = new InformeMascotaEncontrada(informeQR,rescatista,fechaDeHoy,direccion,fotosMascota,ubicacion,estadoActualMascota);
    }

    @AfterEach
    public void destroyContext() throws NoSuchFieldException, IllegalAccessException{
        Field instance = InformesRepository.class.getDeclaredField("informesRepository");
        instance.setAccessible(true);
        instance.set(null,null);
    }

    @Test
    public void registrarUnaMascotaPerdidaAgregaInformeAInformesPendientes() {
        Assertions.assertEquals(informesRepository.getInformesPendientes().size(), 0);
        informesRepository.agregarInformeMascotaEncontrada(informe);
        Assertions.assertEquals(informesRepository.getInformesPendientes().size(), 1);
    }

    @Test
    public void listarMascotasEncontradasEnLosUltimos10DiasDevuelveUnRegistroInsertadoPreviamente(){
        Assertions.assertEquals(informesRepository.listarMascotasEncontradasEnUltimosNDias(10).size(),0);
        informesRepository.agregarInformeMascotaEncontrada(informe);
        Assertions.assertEquals(informesRepository.listarMascotasEncontradasEnUltimosNDias(10).size(),1);
    }

}
