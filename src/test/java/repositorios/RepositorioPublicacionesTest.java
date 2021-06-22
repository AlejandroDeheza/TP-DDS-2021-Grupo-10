package repositorios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import modelo.notificacion.NotificadorCorreo;
import utils.DummyData;

import javax.mail.Transport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RepositorioPublicacionesTest {
    RepositorioPublicaciones repositorioPublicaciones;
    Transport transportMockeado;
    NotificadorCorreo notificadorCorreo;

    @BeforeEach
    public void contextLoad() {
        transportMockeado = mock(Transport.class);
        repositorioPublicaciones = new RepositorioPublicaciones();
        notificadorCorreo = new NotificadorCorreo(session -> transportMockeado);
    }

    @Test
    @DisplayName("Agregar una publicacion no da error")
    public void agregarUnaPublicacionNoDaError() {
        assertDoesNotThrow(
            () -> repositorioPublicaciones.agregarPublicacion(DummyData.getDummyPublicacion(notificadorCorreo))
        );
    }

}
