package modelo.asociacion;

import modelo.informe.Ubicacion;
import modelo.pregunta.ParDePreguntas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;


public class AsociacionTests {


  RepositorioAsociaciones repositorioAsociaciones;
  ParDePreguntas parDePreguntas;

  Ubicacion ubicacion = new Ubicacion(50.01,50.01, null);
  Asociacion asociacion = new Asociacion(ubicacion);

  Ubicacion ubicacion2 = new Ubicacion(1.01,1.01, null);
  Asociacion asociacion2 = new Asociacion(ubicacion2);

  Ubicacion ubicacion3 = new Ubicacion(100.01,100.01, null);
  Asociacion asociacion3 = new Asociacion(ubicacion3);

  UbicadorAsociaciones ubicadorAsociaciones;


  @BeforeEach
  public void loadContext() {
    repositorioAsociaciones = new RepositorioAsociaciones();
    ubicadorAsociaciones = new UbicadorAsociaciones(repositorioAsociaciones);
  }

  @Test
  @DisplayName("Se elije la asociacion más cercana")
  public void seleccionAsociacionMasCercana() {
    repositorioAsociaciones.agregarAsociacion(asociacion); //50
    repositorioAsociaciones.agregarAsociacion(asociacion2); //1
    repositorioAsociaciones.agregarAsociacion(asociacion3); //100
    Ubicacion ubicacionABuscar = new Ubicacion(0.01,0.01,null);
    assertEquals(asociacion2,ubicadorAsociaciones.getAsociacionMasCercana(ubicacionABuscar));
  }

}
