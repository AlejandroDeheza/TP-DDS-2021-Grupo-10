package modelo.informe;

import excepciones.InformeMascotaEncontradaInvalidaException;
import modelo.mascota.Foto;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioInformes;
import utils.DummyData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InformeMascotaConDuenioTest {

    Usuario duenioMascota = DummyData.getDummyUsuario();
    Persona rescatista = DummyData.getDummyPersona();
    LocalDate fechaDeHoy = LocalDate.now();
    String direccion = "Av. Corrientes 576";
    List<Foto> fotosMascota = DummyData.getDummyFotosMascota();
    List<Foto> fotosMascotaVacio = new ArrayList<>();
    Ubicacion ubicacion = new Ubicacion("57,44", "57,55");
    String estadoActualMascota = "Bien de salud, pero asustado";
    InformeMascotaConDuenio informe;
    RepositorioInformes repositorioInformes;

    @BeforeEach
    public void contextLoad() {
        repositorioInformes = new RepositorioInformes();
        informe = generarInformeMascotaEncontradaBuilder(fotosMascotaVacio);
    }

    @Test
    @DisplayName("si se genera un InformeMascotaEncontrada sin fotos, se genera " +
            "InformeMascotaEncontradaInvalidaException")
    public void InformeMascotaEncontradaInvalidaExceptionTest() {
        assertThrows(InformeMascotaEncontradaInvalidaException.class,
                () -> informe.procesarInforme());
    }

    private InformeMascotaConDuenio generarInformeMascotaEncontrada(List<Foto> fotosMascota) {
        return new InformeMascotaConDuenio(
                duenioMascota, rescatista, fechaDeHoy, direccion, fotosMascota, ubicacion, estadoActualMascota,
                repositorioInformes);
    }

    private InformeMascotaConDuenio generarInformeMascotaEncontradaBuilder(List<Foto> fotosMascota){
        InformeMascotaConDuenioBuilder builder = InformeMascotaConDuenioBuilder.crearBuilder();
        builder.conDuenioMascota(duenioMascota)
            .conRescatista(rescatista)
            .conFechaEncuentro(fechaDeHoy)
            .conDireccion(direccion)
            .conFotosMascota(fotosMascota)
            .conLugarDeEncuentro(ubicacion)
            .conEstadoActualMascota(estadoActualMascota)
            .conRepositorioInformes(repositorioInformes);
        return builder.build();
    }

}
