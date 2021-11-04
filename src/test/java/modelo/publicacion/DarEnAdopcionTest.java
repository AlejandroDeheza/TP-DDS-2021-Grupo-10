package modelo.publicacion;

import modelo.asociacion.Asociacion;
import modelo.mascota.MascotaRegistrada;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.RespuestaDelAdoptante;
import modelo.pregunta.RespuestaDelDador;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import utils.DummyData;
import utils.MockNotificador;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DarEnAdopcionTest extends NuestraAbstractPersistenceTest {

  MockNotificador mockNotificador;
  ParDePreguntas parDePreguntas = DummyData.getParDePreguntas1();
  DarEnAdopcion darEnAdopcion;

  @BeforeEach
  public void contextLoad() {
    mockNotificador = DummyData.getMockNotificador();
    List<RespuestaDelDador> respuestasDelDador = new ArrayList<>();
    respuestasDelDador.add(new RespuestaDelDador("No", parDePreguntas));
    darEnAdopcion = new DarEnAdopcion(DummyData.getUsuario(mockNotificador.getTipo()),
        DummyData.getMascotaRegistrada(mockNotificador.getTipo()), respuestasDelDador, DummyData.getAsociacion());
  }

  @Test
  @DisplayName("Si hay una sola respuesta y es valida la cantidad de Matches es uno")
  public void siEsUnaRespuestaValidaLaCantidadDePreguntasQueMatcheanEsUno() {
    List<RespuestaDelAdoptante> respuestasIncorrecta = new ArrayList<>();
    respuestasIncorrecta.add(new RespuestaDelAdoptante("Si", parDePreguntas));
    assertEquals(1, darEnAdopcion.cantidadConLasQueMatchea(respuestasIncorrecta));
  }

  @Test
  @DisplayName("Si hay una sola respuesta y es invalida la cantidad de Matches es cero")
  public void siEsUnaRespuestaInvalidaLaCantidadDePreguntasQueMatcheanEsCero() {
    List<RespuestaDelAdoptante> respuestasIncorrecta = new ArrayList<>();
    respuestasIncorrecta.add(new RespuestaDelAdoptante("bla", parDePreguntas));
    assertEquals(0, darEnAdopcion.cantidadConLasQueMatchea(respuestasIncorrecta));
  }

  @Test
  @DisplayName("Al eliminar una publicación DarEnAdopcion, no se elimina al Usuario publicador asociado")
  public void eliminarUnDarEnAdopcionNoEliminaAlUsuarioPublicadorAsociado() {
    entityManager().persist(darEnAdopcion);
    assertEquals(1, entityManager().createQuery("from DarEnAdopcion", DarEnAdopcion.class).getResultList().size());
    assertEquals(1 /*Dueño*/ + 1 /*Publicador*/, entityManager().createQuery("from Usuario", Usuario.class).getResultList().size());

    entityManager().remove(darEnAdopcion);
    assertEquals(0, entityManager().createQuery("from DarEnAdopcion", DarEnAdopcion.class).getResultList().size());
    assertEquals(1 /*Dueño*/ + 1 /*Publicador*/, entityManager().createQuery("from Usuario", Usuario.class).getResultList().size());
  }

  @Test
  @DisplayName("Al eliminar una publicación DarEnAdopcion, no se elimina su Asociación asociada")
  public void eliminarUnaSuscripcionParaAdopcionNoEliminaSuAsociacionAsociada() {
    Asociacion asociacion = darEnAdopcion.getAsociacion();

    entityManager().persist(darEnAdopcion);
    assertEquals(1, entityManager().createQuery("from DarEnAdopcion", DarEnAdopcion.class).getResultList().size());
    assertEquals(1, entityManager().createQuery("from Asociacion", Asociacion.class).getResultList().size());

    entityManager().remove(darEnAdopcion);
    assertEquals(0, entityManager().createQuery("from DarEnAdopcion", DarEnAdopcion.class).getResultList().size());
    assertEquals(asociacion.getId(), entityManager().createQuery("from Asociacion", Asociacion.class).getResultList().get(0).getId());
  }
  
  @Test
  @DisplayName("Al eliminar una publicación DarEnAdopcion, se elimina sus RespuestaDelDador asociadas")
  public void eliminarUnaSuscripcionParaAdopcionEliminaSusRespuestaDelAdoptanteAsociadas() {
    List<RespuestaDelDador> respuestasDelDador = darEnAdopcion.getRespuestasDelDador();

    entityManager().persist(darEnAdopcion);
    assertEquals(1, entityManager().createQuery("from DarEnAdopcion", DarEnAdopcion.class).getResultList().size());
    assertEquals(respuestasDelDador.get(0).getId(), entityManager().createQuery("from RespuestaDelDador", RespuestaDelDador.class).getResultList().get(0).getId());

    entityManager().remove(darEnAdopcion);
    assertEquals(0, entityManager().createQuery("from DarEnAdopcion", DarEnAdopcion.class).getResultList().size());
    assertEquals(0, entityManager().createQuery("from RespuestaDelDador", RespuestaDelDador.class).getResultList().size());
  }

  @Test
  @DisplayName("Al eliminar una publicación DarEnAdopcion, no se elimina su MascotaRegistrada en adopción asociada")
  public void eliminarUnaPublicacionDarEnAdopcionNoSeEliminaLaMascotaRegistradaAsociada() {
    MascotaRegistrada mascotaRegistradaAsociada = darEnAdopcion.getMascotaEnAdopcion();

    entityManager().persist(darEnAdopcion);
    assertEquals(1, entityManager().createQuery("from DarEnAdopcion", DarEnAdopcion.class).getResultList().size());
    assertEquals(1, entityManager().createQuery("from MascotaRegistrada", MascotaRegistrada.class).getResultList().size());

    entityManager().remove(darEnAdopcion);
    assertEquals(0, entityManager().createQuery("from DarEnAdopcion", DarEnAdopcion.class).getResultList().size());
    assertEquals(mascotaRegistradaAsociada.getId(), entityManager().createQuery("from MascotaRegistrada", MascotaRegistrada.class).getResultList().get(0).getId());
  }
}
