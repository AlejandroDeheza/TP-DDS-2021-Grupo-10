package modelo.informe;

import modelo.mascota.Animal;
import modelo.mascota.Mascota;
import modelo.mascota.MascotaBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InformeMascotaSinDuenioBuilderTest {
  Ubicacion direccion = new Ubicacion(57.44, 57.55);
  @Test
  @DisplayName("Construir Informe Mascota sin Duenio por el Builder")
  public void construirInformeMascotaSinDuenioBuilder() {

//      MascotaBuilder mascotaBuilder= new MascotaBuilder();
//      mascotaBuilder.conFotos(DummyData.getDummyFotosMascota());
//      mascotaBuilder.conCaracteristicas(DummyData.getDummyListaCaracteristicasParaMascota());
//      mascotaBuilder.conAnimal(Animal.PERRO);
//      Mascota mascota=mascotaBuilder.build();

      InformeMascotaSinDuenioBuilder informeMascotaBuilder=new InformeMascotaSinDuenioBuilder();
      informeMascotaBuilder.conFotosMascota(DummyData.getDummyFotosMascota());
      informeMascotaBuilder.conDireccion(direccion);
      informeMascotaBuilder.conFechaEncuentro(LocalDate.now());
      informeMascotaBuilder.conRescatista(DummyData.getDummyPersona());
      informeMascotaBuilder.conLugarDeEncuentro(direccion);

      informeMascotaBuilder.conAnimal(Animal.PERRO);
      informeMascotaBuilder.build();
  }
}
