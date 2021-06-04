package modelo.informe;


import com.fasterxml.jackson.core.JsonProcessingException;
import modelo.mascota.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioPublicaciones;
import servicio.notificacion.NotificacionCorreo;
import utils.DummyData;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class InformeMascotaSinDuenioTest {
    Ubicacion direccion = new Ubicacion(57.44, 57.55);

    @Test
    @DisplayName("Cuando Se se procesa un informe se genera una publicacion en Repo Publicacion")
    public void procesarInformeGeneraPublicacionEnElRepo(){

        InformeMascotaSinDuenioBuilder informeMascotaBuilder=new InformeMascotaSinDuenioBuilder();
        informeMascotaBuilder.conFotosMascota(DummyData.getDummyFotosMascota());
        informeMascotaBuilder.conDireccion(direccion);
        informeMascotaBuilder.conFechaEncuentro(LocalDate.now());
        informeMascotaBuilder.conRescatista(DummyData.getDummyPersona());
        informeMascotaBuilder.conLugarDeEncuentro(direccion);
        informeMascotaBuilder.conAnimal(Animal.PERRO);
        informeMascotaBuilder.conEstadoActualMascota(DummyData.getDummyListaCaracteristicasParaMascota());
        InformeMascotaSinDuenio informeMascotaSinDuenio =informeMascotaBuilder.build();
        informeMascotaSinDuenio.procesarInforme();
        assertEquals(1,RepositorioPublicaciones.getInstance().listarPublicacionesSinDuenio().size());
    }


    @Test
    @DisplayName("Obtener Hogares disponibles")
    public void obtenerHogaresDisponiblesParaElInforme() throws JsonProcessingException {

        InformeMascotaSinDuenioBuilder informeMascotaBuilder=new InformeMascotaSinDuenioBuilder();
        informeMascotaBuilder.conFotosMascota(DummyData.getDummyFotosMascota());
        informeMascotaBuilder.conDireccion(direccion);
        informeMascotaBuilder.conFechaEncuentro(LocalDate.now());
        informeMascotaBuilder.conEstadoActualMascota(DummyData.getDummyListaCaracteristicasParaMascota());
        informeMascotaBuilder.conRescatista(DummyData.getDummyPersona());
        informeMascotaBuilder.conLugarDeEncuentro(direccion);
        informeMascotaBuilder.conAnimal(Animal.PERRO);
        InformeMascotaSinDuenio informeMascotaSinDuenio =informeMascotaBuilder.build();
        assertEquals(0,informeMascotaSinDuenio.getHogaresTransitorios(0).size());
    }



}
