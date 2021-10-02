package modelo.asociacion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import modelo.informe.Ubicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import entregaTPA4.persistencia.NuestraAbstractPersistenceTest;
import repositorios.RepositorioAsociaciones;

public class AsociacionTests extends NuestraAbstractPersistenceTest {

  Ubicacion ubicacion = new Ubicacion(50.01, 50.01, null);
  Asociacion asociacion = new Asociacion("", ubicacion);

  Ubicacion ubicacion2 = new Ubicacion(1.01, 1.01, null);
  Asociacion asociacion2 = new Asociacion("", ubicacion2);

  Ubicacion ubicacion3 = new Ubicacion(100.01, 100.01, null);
  Asociacion asociacion3 = new Asociacion("", ubicacion3);

  UbicadorAsociaciones ubicadorAsociaciones;

  @BeforeEach
  public void loadContext() {
    entityManager().persist(asociacion); //50
    entityManager().persist(asociacion2); //1
    entityManager().persist(asociacion3); //100
    ubicadorAsociaciones = new UbicadorAsociaciones(new RepositorioAsociaciones());
  }

  @Test
  @DisplayName("Se elije la asociacion más cercana")
  public void seleccionAsociacionMasCercana() {
    assertEquals(asociacion2, ubicadorAsociaciones.getAsociacionMasCercana(new Ubicacion(0.01, 0.01, null)));
  }

}
