package entregaTPA3;

import modelo.adopcion.RecomendadorDeAdopciones;
import modelo.notificacion.NotificadorCorreo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesAdopcion;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class Punto5 {

    NotificadorCorreo notificadorCorreo;
    RecomendadorDeAdopciones recomendador;
    RepositorioSuscripcionesAdopcion repositorioSuscripcionesAdopcion = new RepositorioSuscripcionesAdopcion();
    RepositorioDarEnAdopcion repositorioDarEnAdopcion = new RepositorioDarEnAdopcion();

    @BeforeEach
    public void contextLoad() {
        notificadorCorreo = mock(NotificadorCorreo.class);
        recomendador = new RecomendadorDeAdopciones(5,repositorioSuscripcionesAdopcion,repositorioDarEnAdopcion);
    }

//    @Test
//    public void SeSugierenCincoAdopcionesEn30Segundos(){
//        recomendador.recomendarAdopciones(); // Por cada usuario suscripto deberia buscar las mascotas que hacen match y enviarle una notificacion.
//        verify(notificadorCorreo, times(1)).notificar(any());
//    }

}
