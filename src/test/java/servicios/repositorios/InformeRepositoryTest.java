package servicios.repositorios;

import modelo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InformeRepositoryTest {

    InformesRepository informesRepository;
    InformeMascotaEncontrada informe;

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

    @Test
    public void agregarPrimerRegistroInforme(){
        Assertions.assertEquals(informesRepository.getInformesPendientes().size(),0);
        informesRepository.agregarInformeMascotaEncontrada(informe);
        Assertions.assertEquals(informesRepository.getInformesPendientes().size(),1);
    }

    @Test
    public void registrarUnaMascotaPerdidaAgregaInformeAInformesPendientes(){
        Integer cantidadPrevia = informesRepository.getInformesPendientes().size();
        informesRepository.agregarInformeMascotaEncontrada(informe);
        Assertions.assertEquals(informesRepository.getInformesPendientes().size(),++cantidadPrevia);
    }

    @Test
    public void listarMascotasEncontradasEnLosUltimos10DiasDevuelveUnRegistroInsertadoPreviamente(){
        Integer cantidadPrevia = informesRepository.listarMascotasEncontradasEnUltimosNDias(10).size();
        informesRepository.agregarInformeMascotaEncontrada(informe);
        Assertions.assertEquals(informesRepository.listarMascotasEncontradasEnUltimosNDias(10).size(),++cantidadPrevia);
    }

}
