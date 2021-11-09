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
import utils.CascadeTypeCheck;
import utils.DummyData;
import utils.MockNotificador;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DarEnAdopcionTest extends NuestraAbstractPersistenceTest {

  MockNotificador mockNotificador;
  ParDePreguntas parDePreguntas = DummyData.getParDePreguntas1();
  DarEnAdopcion darEnAdopcion;
  CascadeTypeCheck cascadeTypeCheck;

  @BeforeEach
  public void contextLoad() {
    mockNotificador = DummyData.getMockNotificador();
    List<RespuestaDelDador> respuestasDelDador = new ArrayList<>();
    respuestasDelDador.add(new RespuestaDelDador("No", parDePreguntas));
    darEnAdopcion = new DarEnAdopcion(DummyData.getUsuario(mockNotificador.getTipo()),
        DummyData.getMascotaRegistrada(mockNotificador.getTipo()), respuestasDelDador, DummyData.getAsociacion());
    cascadeTypeCheck = new CascadeTypeCheck(darEnAdopcion);
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
    Usuario usuarioAsociado = darEnAdopcion.getPublicador();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(usuarioAsociado, 1, 2, 0, 2));
    assertNotNull(entityManager().find(Usuario.class, usuarioAsociado.getId()));
  }

  @Test
  @DisplayName("Al eliminar una publicación DarEnAdopcion, no se elimina su Asociación asociada")
  public void eliminarUnaSuscripcionParaAdopcionNoEliminaSuAsociacionAsociada() {
    Asociacion asociacion = darEnAdopcion.getAsociacion();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(asociacion, 1, 1, 0, 1));
    assertEquals(asociacion.getId(), entityManager().createQuery("from Asociacion", Asociacion.class).getResultList().get(0).getId());
  }
  
  @Test
  @DisplayName("Al eliminar una publicación DarEnAdopcion, se elimina sus RespuestaDelDador asociadas")
  public void eliminarUnaSuscripcionParaAdopcionEliminaSusRespuestaDelAdoptanteAsociadas() {
    List<RespuestaDelDador> respuestasDelDador = darEnAdopcion.getRespuestasDelDador();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(respuestasDelDador, 1, 1, 0, 0));
  }

  @Test
  @DisplayName("Al eliminar una publicación DarEnAdopcion, no se elimina su MascotaRegistrada en adopción asociada")
  public void eliminarUnaPublicacionDarEnAdopcionNoSeEliminaLaMascotaRegistradaAsociada() {
    MascotaRegistrada mascotaRegistradaAsociada = darEnAdopcion.getMascotaEnAdopcion();
    assertTrue(cascadeTypeCheck.contemplaElCascadeType(mascotaRegistradaAsociada, 1, 1, 0, 1));
    assertEquals(mascotaRegistradaAsociada.getId(), entityManager().createQuery("from MascotaRegistrada", MascotaRegistrada.class).getResultList().get(0).getId());
  }
}
