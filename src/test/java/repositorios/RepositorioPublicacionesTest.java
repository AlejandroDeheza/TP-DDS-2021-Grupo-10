package repositorios;

import excepciones.PublicacionExistenteException;
import excepciones.PublicacionInexistenteException;
import modelo.publicacion.Publicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import servicio.notificacion.NotificacionCorreo;
import utils.DummyData;

import javax.mail.Transport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RepositorioPublicacionesTest {
    RepositorioPublicaciones repositorioPublicaciones;
    Transport transportMockeado;

    @BeforeEach
    public void contextLoad() {
        transportMockeado = mock(Transport.class);
        repositorioPublicaciones = new RepositorioPublicaciones(new NotificacionCorreo(sesion -> transportMockeado));
    }

    @Test
    @DisplayName("Agregar una publicacion no da error")
    public void agregarUnaPublicacionNoDaError() {
        assertDoesNotThrow(()->repositorioPublicaciones.agregarPublicacion(DummyData.getDummyPublicacion()));
    }

    @Test
    @DisplayName("Agregar dos publicaciones iguales da error")
    public void agregarDosPublicacionesIgualesDaError() {
        Publicacion publicacionDuplicada= DummyData.getDummyPublicacion();
        repositorioPublicaciones.agregarPublicacion(publicacionDuplicada);
        assertThrows(PublicacionExistenteException.class,() ->repositorioPublicaciones.agregarPublicacion(publicacionDuplicada));
    }


    @Test
    @DisplayName("Listar Publicaciones Sin Duenio")
    public void listarPublicacionesSinDuenio() {
        Publicacion publicacion= DummyData.getDummyPublicacion();
        repositorioPublicaciones.agregarPublicacion(publicacion);
        assertEquals(1,repositorioPublicaciones.listarPublicacionesSinDuenio().size());
    }

    @Test
    @DisplayName("Listar Publicaciones Con Duenio")
    public void listarPublicacionesConDuenio() {
        Publicacion publicacion= DummyData.getDummyPublicacion();
        repositorioPublicaciones.agregarPublicacion(publicacion);
        repositorioPublicaciones.encontreMiMascota(publicacion,DummyData.getDummyUsuario());
        assertEquals(1,repositorioPublicaciones.listarPublicacionesConDuenio().size());
    }

    @Test
    @DisplayName("Encontrar una Mascota que no esta perdida da error")
    public void encontrarunaMascotaQueNoEstaPerdidadDaError() {
        Publicacion publicacion= DummyData.getDummyPublicacion();
        repositorioPublicaciones.agregarPublicacion(publicacion);
        Publicacion publicacion2= DummyData.getDummyPublicacion();
        assertThrows(PublicacionInexistenteException.class,()->repositorioPublicaciones.encontreMiMascota(publicacion2,DummyData.getDummyUsuario()));
    }

    @Test
    @DisplayName("Encontrar una Mascota envia una Notificacion")
    public void encontrarunaMascotaPerdidaEnviaUnaNotificacion() {
        NotificacionCorreo notificacionCorreoMock = new NotificacionCorreo(sesion -> transportMockeado);
        Publicacion publicacion= DummyData.getDummyPublicacion();
        repositorioPublicaciones.agregarPublicacion(publicacion);
        repositorioPublicaciones.encontreMiMascota(publicacion,DummyData.getDummyUsuario());
        verify(notificacionCorreoMock).enviarNotificacion(any());
    }

}
