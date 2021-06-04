package modelo.informe;


import com.fasterxml.jackson.core.JsonProcessingException;
import modelo.mascota.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioPublicaciones;
import servicio.notificacion.NotificacionCorreo;
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
        repositorioPublicaciones = new RepositorioPublicaciones(new NotificacionCorreo(asdas -> transportMockeado));
        informeMascotaSinDuenioBuilder.conRepositorioPublicaciones(repositorioPublicaciones);
        informeMascotaSinDuenioBuilder.conEstadoActualMascota(DummyData.getDummyListaCaracteristicasParaMascota());
        informeMascotaSinDuenio =informeMascotaSinDuenioBuilder.build();

    }

    @Test
    @DisplayName("Cuando Se se procesa un informe se genera una publicacion en Repo Publicacion")
    public void procesarInformeGeneraPublicacionEnElRepo(){
        informeMascotaSinDuenio.procesarInforme();
        assertEquals(1,repositorioPublicaciones.listarPublicacionesSinDuenio().size());
    }


    @Test
    @DisplayName("Obtener Hogares disponibles")
    public void obtenerHogaresDisponiblesParaElInforme() throws JsonProcessingException {
        assertEquals(0,informeMascotaSinDuenio.getHogaresTransitorios(0).size());
    }

}
