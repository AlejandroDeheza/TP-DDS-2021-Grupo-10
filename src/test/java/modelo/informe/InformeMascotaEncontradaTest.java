package modelo.informe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DummyData;

public class InformeMascotaEncontradaTest {

    @Test
    @DisplayName("test getters de InformeMascotaEncontrada")
    public void informeMascotaEncontradaTest() {
        InformeMascotaEncontrada informeMascotaEncontrada = DummyData.getDummyInformeMascotaEncontrada();
        Assertions.assertSame(informeMascotaEncontrada.getEstadoActualMascota(),DummyData.getDummyInformeMascotaEncontrada().getEstadoActualMascota());
        Assertions.assertSame(informeMascotaEncontrada.getDireccion(),DummyData.getDummyInformeMascotaEncontrada().getDireccion());
        Assertions.assertSame(informeMascotaEncontrada.getCodigoQRInformacion().getMascota(),DummyData.getDummyInformeMascotaEncontrada().getCodigoQRInformacion().getMascota());
        Assertions.assertSame(informeMascotaEncontrada.getFechaEncuentroAnimal().getDayOfYear(),DummyData.getDummyInformeMascotaEncontrada().getFechaEncuentroAnimal().getDayOfYear());
        Assertions.assertSame(informeMascotaEncontrada.getFotosAnimal().size(),DummyData.getDummyInformeMascotaEncontrada().getFotosAnimal().size());
        Assertions.assertSame(informeMascotaEncontrada.getLugarDeEncuentro().getLatitud(),DummyData.getDummyInformeMascotaEncontrada().getLugarDeEncuentro().getLatitud());
        Assertions.assertSame(informeMascotaEncontrada.getRescatista().getNombre(),DummyData.getDummyInformeMascotaEncontrada().getRescatista().getNombre());
    }

}
