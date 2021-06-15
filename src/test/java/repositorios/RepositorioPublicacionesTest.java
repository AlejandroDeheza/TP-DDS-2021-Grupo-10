package repositorios;

import modelo.publicacion.Publicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import servicio.notificacion.NotificacionCorreo;
import utils.DummyData;

import javax.mail.MessagingException;
import javax.mail.Transport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RepositorioPublicacionesTest {
    RepositorioPublicaciones repositorioPublicaciones;
    Transport transportMockeado;
    NotificacionCorreo notificacionCorreo;

    @BeforeEach
    public void contextLoad() {
        transportMockeado = mock(Transport.class);
        repositorioPublicaciones = new RepositorioPublicaciones();
        notificacionCorreo = new NotificacionCorreo(session -> transportMockeado);
    }

    @Test
    @DisplayName("Agregar una publicacion no da error")
    public void agregarUnaPublicacionNoDaError() {
        assertDoesNotThrow(()->repositorioPublicaciones.agregarPublicacion(DummyData.getDummyPublicacion(notificacionCorreo)));
    }

    @Test
    @DisplayName("Encontrar una Mascota envia una Notificacion")
    public void encontrarunaMascotaPerdidaEnviaUnaNotificacion() throws MessagingException {
        Publicacion publicacion= DummyData.getDummyPublicacion(notificacionCorreo);
        publicacion.notificarEncuentroAlRescatista(DummyData.getDummyUsuario());
        verify(transportMockeado).sendMessage(any(), any());
    }

}
