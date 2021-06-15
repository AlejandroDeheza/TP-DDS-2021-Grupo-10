package modelo.informe;


import com.fasterxml.jackson.core.JsonProcessingException;
import modelo.mascota.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioPublicaciones;
import utils.DummyData;

import javax.mail.Transport;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class InformeMascotaSinDuenioTest {

    Transport transportMockeado;
    Ubicacion direccion = new Ubicacion(57.44, 57.55);
    InformeMascotaSinDuenioBuilder informeMascotaSinDuenioBuilder;
    InformeMascotaSinDuenio informeMascotaSinDuenio;
    RepositorioPublicaciones repositorioPublicaciones;

    @BeforeEach
    public void loadContext() {
        transportMockeado = mock(Transport.class);
        informeMascotaSinDuenioBuilder=new InformeMascotaSinDuenioBuilder();
        informeMascotaSinDuenioBuilder.conFotosMascota(DummyData.getDummyFotosMascota());
        informeMascotaSinDuenioBuilder.conDireccion(direccion);
        informeMascotaSinDuenioBuilder.conFechaEncuentro(LocalDate.now());
        informeMascotaSinDuenioBuilder.conRescatista(DummyData.getDummyPersona());
        informeMascotaSinDuenioBuilder.conLugarDeEncuentro(direccion);
        informeMascotaSinDuenioBuilder.conAnimal(Animal.PERRO);
        repositorioPublicaciones = new RepositorioPublicaciones();
        informeMascotaSinDuenioBuilder.conRepositorioPublicaciones(repositorioPublicaciones);
        informeMascotaSinDuenioBuilder.conEstadoActualMascota(DummyData.getDummyListaCaracteristicasParaMascota(
            new RepositorioCaracteristicas()
            )
        );
        informeMascotaSinDuenio =informeMascotaSinDuenioBuilder.build();

    }

    @Test
    @DisplayName("Cuando Se se procesa un informe se genera una publicacion en Repo Publicacion")
    public void procesarInformeGeneraPublicacionEnElRepo(){
        informeMascotaSinDuenio.procesarInforme();
        assertEquals(1,repositorioPublicaciones.getPublicaciones().size());
    }


    @Test
    @DisplayName("Obtener Hogares disponibles")
    public void obtenerHogaresDisponiblesParaElInforme() throws JsonProcessingException {
        assertEquals(0,informeMascotaSinDuenio.getHogaresTransitorios(0).size());
    }

}
