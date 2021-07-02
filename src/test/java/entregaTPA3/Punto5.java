package entregaTPA3;

import modelo.adopcion.RecomendadorDeAdopciones;
import modelo.asociacion.Asociacion;
import modelo.mascota.Animal;
import modelo.notificacion.NotificadorCorreo;
import modelo.pregunta.Respuesta;
import modelo.suscripcion.Preferencia;
import modelo.suscripcion.SuscripcionParaAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import utils.DummyData;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class Punto5 {

  NotificadorCorreo notificadorCorreo;
  RecomendadorDeAdopciones recomendador;
  RepositorioSuscripcionesParaAdopciones repositorioSuscripcionesParaAdopciones = new RepositorioSuscripcionesParaAdopciones();
  RepositorioDarEnAdopcion repositorioDarEnAdopcion = new RepositorioDarEnAdopcion();
  SuscripcionParaAdopcion suscripcionParaAdopcion;

  @BeforeEach
  public void contextLoad() {
    notificadorCorreo = mock(NotificadorCorreo.class);
    suscripcionParaAdopcion=  mock(SuscripcionParaAdopcion.class);
    repositorioSuscripcionesParaAdopciones.agregar(suscripcionParaAdopcion);
    recomendador = new RecomendadorDeAdopciones(5, repositorioDarEnAdopcion, repositorioSuscripcionesParaAdopciones);

  }

  @Test
  public void seEnviaRecomendacionCuandoElRecomendadorRecomienda() {
    recomendador.recomendarAdopcionesASuscritos();
    // Por cada usuario suscripto deberia buscar las mascotas que hacen match y enviarle una notificacion.
    verify(notificadorCorreo, times(1)).notificar(any());
  }

}
