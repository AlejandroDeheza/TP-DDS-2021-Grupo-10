package modelo.adopcion;

import modelo.mascota.Animal;
import modelo.mascota.MascotaRegistrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.notificacion.Notificador;
import modelo.notificacion.NotificadorCorreo;
import modelo.pregunta.RespuestaDelDador;
import modelo.publicacion.DarEnAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import utils.DummyData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RecomendadorDeAdopcionesTest {

  Notificador notificadorMockeado;
  DarEnAdopcion publicacion1;
  DarEnAdopcion publicacion2;
  RepositorioDarEnAdopcion repositorioDarEnAdopcion;
  RepositorioSuscripcionesParaAdopciones repositorioSuscripcionesParaAdopciones;
  RecomendadorDeAdopciones recomendadorDeAdopciones;

  @BeforeEach
  public void contextLoad() {
    notificadorMockeado = mock(NotificadorCorreo.class);

    repositorioDarEnAdopcion = new RepositorioDarEnAdopcion();
    publicacion1 = DummyData.getPublicacionDeDarEnAdopcion(notificadorMockeado, repositorioDarEnAdopcion);
    repositorioDarEnAdopcion.agregar(publicacion1);
    publicacion2 = new DarEnAdopcion(
        DummyData.getDatosDeContacto(notificadorMockeado),
        DummyData.getMascotaRegistrada(notificadorMockeado),
        repositorioDarEnAdopcion,
        Arrays.asList(
            new RespuestaDelDador("bla", DummyData.getParDePreguntas1()),
            new RespuestaDelDador("bla", DummyData.getParDePreguntas2())
        ),
        DummyData.getAsociacion()
    );
    repositorioDarEnAdopcion.agregar(publicacion2);

    repositorioSuscripcionesParaAdopciones = new RepositorioSuscripcionesParaAdopciones();
    repositorioSuscripcionesParaAdopciones.agregar(DummyData.getSuscripcionParaAdopcion(notificadorMockeado));
    repositorioSuscripcionesParaAdopciones.agregar(DummyData.getSuscripcionParaAdopcion(notificadorMockeado));

    recomendadorDeAdopciones = new RecomendadorDeAdopciones(2,
        repositorioDarEnAdopcion, repositorioSuscripcionesParaAdopciones);
  }

  @Test
  public void seEnvianRecomendacionesCorrectamenteCuandoElRecomendadorRecomienda() {
    recomendadorDeAdopciones.recomendarAdopcionesASuscritos();
    verify(notificadorMockeado, times(2)).notificarRecomendacionesDeAdopciones(any());
  }

  @Test
  public void noSeEnvianRecomendacionesSiNoHay() {
    repositorioDarEnAdopcion.marcarComoProcesada(publicacion1);
    repositorioDarEnAdopcion.marcarComoProcesada(publicacion2);
    recomendadorDeAdopciones.recomendarAdopcionesASuscritos();
    verify(notificadorMockeado, times(0)).notificarRecomendacionesDeAdopciones(any());
  }

  @Test
  public void noSeEnvianRecomendacionesSiNoHayTipoDeMascota() {
    repositorioDarEnAdopcion.marcarComoProcesada(publicacion1);
    repositorioDarEnAdopcion.marcarComoProcesada(publicacion2);
    MascotaRegistrada mascotaRegistrada = new MascotaRegistrada(null, null, null, null,
        null, null, Animal.GATO, DummyData.getCaracteristicasParaMascota(), null, null);

    repositorioDarEnAdopcion.agregar(
        new DarEnAdopcion(
            DummyData.getDatosDeContacto(notificadorMockeado),
            mascotaRegistrada,
            repositorioDarEnAdopcion,
            Arrays.asList(
                new RespuestaDelDador("Si", DummyData.getParDePreguntas1()),
                new RespuestaDelDador("2", DummyData.getParDePreguntas2())
            ),
            DummyData.getAsociacion()
        )
    );
    recomendadorDeAdopciones.recomendarAdopcionesASuscritos();
    verify(notificadorMockeado, times(0)).notificarRecomendacionesDeAdopciones(any());
  }

  @Test
  public void noSeEnvianRecomendacionesSiNoSeCumplenCaracteristicas() {
    repositorioDarEnAdopcion.marcarComoProcesada(publicacion1);
    repositorioDarEnAdopcion.marcarComoProcesada(publicacion2);
    List<Caracteristica> listaCaracteristica = new ArrayList<>();
    listaCaracteristica.add(new Caracteristica("Comportamiento", "Inquieto"));

    MascotaRegistrada mascotaRegistrada = new MascotaRegistrada(null, null, null, null,
        null, null, Animal.PERRO, listaCaracteristica, null, null);

    repositorioDarEnAdopcion.agregar(
        new DarEnAdopcion(
            DummyData.getDatosDeContacto(notificadorMockeado),
            mascotaRegistrada,
            repositorioDarEnAdopcion,
            Arrays.asList(
                new RespuestaDelDador("Si", DummyData.getParDePreguntas1()),
                new RespuestaDelDador("2", DummyData.getParDePreguntas2())
            ),
            DummyData.getAsociacion()
        )
    );
    recomendadorDeAdopciones.recomendarAdopcionesASuscritos();
    verify(notificadorMockeado, times(0)).notificarRecomendacionesDeAdopciones(any());
  }

  @Test
  public void lasRecomendacionesSeOrdenanCorrectamente() {
    List<DarEnAdopcion> recomendaciones = recomendadorDeAdopciones.generarRecomendaciones(
        DummyData.getSuscripcionParaAdopcion(notificadorMockeado)
    );
    assertEquals(2, recomendaciones.size());
    assertEquals(publicacion1, recomendaciones.get(0));
    assertEquals(publicacion2, recomendaciones.get(1));
  }

  @Test
  public void seLimitanLasRecomendacionesCorrectamente() {
    recomendadorDeAdopciones = new RecomendadorDeAdopciones(1,
        repositorioDarEnAdopcion, repositorioSuscripcionesParaAdopciones);

    List<DarEnAdopcion> recomendaciones = recomendadorDeAdopciones.generarRecomendaciones(
        DummyData.getSuscripcionParaAdopcion(notificadorMockeado)
    );
    assertEquals(1, recomendaciones.size());
    assertEquals(publicacion1, recomendaciones.get(0));
  }
}
