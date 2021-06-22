package modelo.informe;


import client.ReceptorHogares;
import com.fasterxml.jackson.core.JsonProcessingException;
import modelo.mascota.Animal;
import modelo.mascota.MascotaEncontrada;
import modelo.persona.Persona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioInformes;
import repositorios.RepositorioPublicaciones;
import servicio.notificacion.NotificadorCorreo;
import utils.DummyData;

import javax.mail.Transport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class InformeSinQRTest {

    Transport transportMockeado;
    Persona rescatista = DummyData.getDummyPersona();
    Ubicacion ubicacion = DummyData.getDummyUbicacion();
    InformeSinQR informeSinQR;
    RepositorioPublicaciones repositorioPublicaciones;
    ReceptorHogares receptorHogaresMock = mock(ReceptorHogares.class);
    NotificadorCorreo notificadorCorreoMockeado;

    MascotaEncontrada mascotaEncontrada = DummyData.getDummyMascotaEncontrada(new RepositorioCaracteristicas(), DummyData.getDummyFotosMascota());
    RepositorioInformes repositorioInformes;


    @BeforeEach
    public void loadContext() {
        transportMockeado = mock(Transport.class);
        repositorioInformes = new RepositorioInformes();
        notificadorCorreoMockeado = new NotificadorCorreo(sesion -> transportMockeado);
        repositorioPublicaciones = new RepositorioPublicaciones();
        informeSinQR = generarInformeMascotaEncontrada(notificadorCorreoMockeado);
    }

    @Test
    @DisplayName("Cuando Se se procesa un informe se genera una publicacion en Repo Publicacion")
    public void procesarInformeGeneraPublicacionEnElRepo(){
        informeSinQR.procesarInforme();
        assertEquals(1, repositorioPublicaciones.getPublicaciones().size());
    }


    @Test
    @DisplayName("Obtener Hogares disponibles")
    public void obtenerHogaresDisponiblesParaElInforme() throws JsonProcessingException {
        assertEquals(0, informeSinQR.getHogaresCercanos(0).size());
    }

    private InformeSinQR generarInformeMascotaEncontrada(NotificadorCorreo notificador) {
        return new InformeSinQR(rescatista, ubicacion, "", mascotaEncontrada, repositorioInformes, receptorHogaresMock,
            Animal.PERRO, DummyData.getDummyListaCaracteristicasParaMascota(new RepositorioCaracteristicas()),
            repositorioPublicaciones, notificador);
    }

}
