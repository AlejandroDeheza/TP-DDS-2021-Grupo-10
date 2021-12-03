package entregaTPA4.repositorios;

import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import modelo.suscripcion.SuscripcionParaAdopcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import utils.DummyData;
import utils.MockNotificador;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositorioSuscripcionesParaAdopcionesTest extends NuestraAbstractPersistenceTest {

  RepositorioSuscripcionesParaAdopciones repo = new RepositorioSuscripcionesParaAdopciones();
  MockNotificador mockNotificador;

  @BeforeEach
  public void contextLoad() {
    mockNotificador = DummyData.getMockNotificador();
  }

  @Test
  @DisplayName("Agregar una suscripcion aumenta el numero de suscripciones en una")
  public void agregarSuscripcionTest(){
    SuscripcionParaAdopcion suscripcionParaAdopcion =
        DummyData.getSuscripcionParaAdopcion(mockNotificador.getTipo());
    int cantidadSuscripcionesPrevia = repo.getSuscripcionesActivas().size();
    repo.agregar(suscripcionParaAdopcion);
    assertEquals(cantidadSuscripcionesPrevia +1, repo.getSuscripcionesActivas().size());
  }

  @Test
  @DisplayName("Dar de baja una suscripcion recibe una suscripcion con el flag isActivo cambiado y updatea el registro en la DB")
  public void darDeBajaSuscripcionTest(){
    SuscripcionParaAdopcion suscripcionParaAdopcion =
        DummyData.getSuscripcionParaAdopcion(mockNotificador.getTipo());
    repo.agregar(suscripcionParaAdopcion);
    suscripcionParaAdopcion.desactivar();
    assertEquals(1,  repo.getSuscripcionesDeBaja().size());
  }

}
