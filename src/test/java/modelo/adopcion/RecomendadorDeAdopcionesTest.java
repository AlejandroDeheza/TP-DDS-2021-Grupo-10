package modelo.adopcion;

import modelo.mascota.Animal;
import modelo.mascota.MascotaRegistrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.pregunta.RespuestaDelDador;
import modelo.publicacion.DarEnAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import utils.DummyData;
import utils.MockNotificador;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RecomendadorDeAdopcionesTest {

  MockNotificador mockNotificador;
  DarEnAdopcion publicacion1;
  DarEnAdopcion publicacion2;
  RepositorioDarEnAdopcion repositorioDarEnAdopcion;
  RepositorioSuscripcionesParaAdopciones repositorioSuscripcionesParaAdopciones;
  RecomendadorDeAdopciones recomendadorDeAdopciones;

  @BeforeEach
  public void contextLoad() {
    mockNotificador = DummyData.getMockNotificador();
    repositorioDarEnAdopcion = mock(RepositorioDarEnAdopcion.class);
    repositorioSuscripcionesParaAdopciones = mock(RepositorioSuscripcionesParaAdopciones.class);

    publicacion1 = DummyData.getPublicacionDeDarEnAdopcion(mockNotificador.getTipo());
    publicacion2 = new DarEnAdopcion(
        DummyData.getUsuario(mockNotificador.getTipo()),
        DummyData.getMascotaRegistrada(mockNotificador.getTipo()),
        Arrays.asList(
            new RespuestaDelDador("bla", DummyData.getParDePreguntas1()),
            new RespuestaDelDador("bla", DummyData.getParDePreguntas2())
        ),
        DummyData.getAsociacion()
    );

    when(repositorioDarEnAdopcion.getPublicacionesActivas()).thenReturn(Arrays.asList(publicacion1, publicacion2));
    when(repositorioSuscripcionesParaAdopciones.getSuscripcionesActivas()).thenReturn(Arrays.asList(
        DummyData.getSuscripcionParaAdopcion(mockNotificador.getTipo()),
        DummyData.getSuscripcionParaAdopcion(mockNotificador.getTipo())
    ));

    recomendadorDeAdopciones = new RecomendadorDeAdopciones(2,
        repositorioDarEnAdopcion, repositorioSuscripcionesParaAdopciones);
  }

  @Test
  public void seEnvianRecomendacionesCorrectamenteCuandoElRecomendadorRecomienda() {
    recomendadorDeAdopciones.recomendarAdopcionesASuscritos();
    verify(mockNotificador.getNotificador(), times(2)).notificarRecomendacionesDeAdopciones(any());
  }

  @Test
  public void noSeEnvianRecomendacionesSiNoHay() {
    when(repositorioDarEnAdopcion.getPublicacionesActivas()).thenReturn(Collections.emptyList());
    recomendadorDeAdopciones.recomendarAdopcionesASuscritos();
    verify(mockNotificador.getNotificador(), times(0)).notificarRecomendacionesDeAdopciones(any());
  }

  @Test
  public void noSeEnvianRecomendacionesSiNoHayTipoDeMascota() {
    MascotaRegistrada mascotaRegistrada = new MascotaRegistrada(null, null, null, null,
        null, null, Animal.GATO, DummyData.getCaracteristicasParaMascota(), null, null);

    when(repositorioDarEnAdopcion.getPublicacionesActivas()).thenReturn(Collections.singletonList(
        new DarEnAdopcion(
            DummyData.getUsuario(mockNotificador.getTipo()),
            mascotaRegistrada,
            Arrays.asList(
                new RespuestaDelDador("Si", DummyData.getParDePreguntas1()),
                new RespuestaDelDador("2", DummyData.getParDePreguntas2())
            ),
            DummyData.getAsociacion()
        )
    ));

    recomendadorDeAdopciones.recomendarAdopcionesASuscritos();
    verify(mockNotificador.getNotificador(), times(0)).notificarRecomendacionesDeAdopciones(any());
  }

  @Test
  public void noSeEnvianRecomendacionesSiNoSeCumplenCaracteristicas() {
    List<Caracteristica> listaCaracteristica = new ArrayList<>();
    listaCaracteristica.add(new Caracteristica("Comportamiento", "Inquieto"));

    MascotaRegistrada mascotaRegistrada = new MascotaRegistrada(null, null, null, null,
        null, null, Animal.PERRO, listaCaracteristica, null, null);

    when(repositorioDarEnAdopcion.getPublicacionesActivas()).thenReturn(Collections.singletonList(
        new DarEnAdopcion(
            DummyData.getUsuario(mockNotificador.getTipo()),
            mascotaRegistrada,
            Arrays.asList(
                new RespuestaDelDador("Si", DummyData.getParDePreguntas1()),
                new RespuestaDelDador("2", DummyData.getParDePreguntas2())
            ),
            DummyData.getAsociacion()
        )
    ));

    recomendadorDeAdopciones.recomendarAdopcionesASuscritos();
    verify(mockNotificador.getNotificador(), times(0)).notificarRecomendacionesDeAdopciones(any());
  }

  @Test
  public void lasRecomendacionesSeOrdenanCorrectamente() {
    List<DarEnAdopcion> recomendaciones = recomendadorDeAdopciones.generarRecomendaciones(
        DummyData.getSuscripcionParaAdopcion(mockNotificador.getTipo())
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
        DummyData.getSuscripcionParaAdopcion(mockNotificador.getTipo())
    );
    assertEquals(1, recomendaciones.size());
    assertEquals(publicacion1, recomendaciones.get(0));
  }
}
