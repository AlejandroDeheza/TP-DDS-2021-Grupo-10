package modelo.informe;

import excepciones.InformeMascotaEncontradaInvalidaException;
import modelo.mascota.Foto;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioInformes;
import utils.DummyData;

import javax.mail.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InformeMascotaConDuenioTest {

    Usuario duenioMascota = DummyData.getDummyUsuario();
    Persona rescatista = DummyData.getDummyPersona();
    Usuario voluntario = DummyData.getDummyUsuarioVoluntario();
    LocalDate fechaDeHoy = LocalDate.now();
    String direccion = "Av. Corrientes 576";
    List<Foto> fotosMascota = DummyData.getDummyFotosMascota();
    List<Foto> fotosMascotaVacio = new ArrayList<>();
    Ubicacion ubicacion = new Ubicacion("57,44", "57,55");
    String estadoActualMascota = "Bien de salud, pero asustado";
    InformeMascotaConDuenio informeConFoto;
    InformeMascotaConDuenio informeSinFoto;
    RepositorioInformes repositorioInformes;

    @BeforeEach
    public void contextLoad() {
        repositorioInformes = new RepositorioInformes();
        informeSinFoto = generarInformeMascotaEncontradaBuilder(fotosMascotaVacio);
        informeConFoto = generarInformeMascotaEncontradaBuilder(fotosMascota);
    }

    @Test
    @DisplayName("Chequeo igualdad entre Constructor y Builder")
    public void InformeMascotaEncontradaBuilderConstructorTest(){
        InformeMascotaConDuenio informeAux = generarInformeMascotaEncontrada(fotosMascotaVacio);
        Assertions.assertEquals(informeSinFoto.getDuenioMascota(), informeAux.getDuenioMascota());
        Assertions.assertEquals(informeSinFoto.getDireccion(), informeAux.getDireccion());
        Assertions.assertEquals(informeSinFoto.getEstadoActualMascota(), informeAux.getEstadoActualMascota());
        Assertions.assertEquals(informeSinFoto.getFotosMascota(), informeAux.getFotosMascota());
        Assertions.assertEquals(informeSinFoto.getFechaEncuentro(), informeAux.getFechaEncuentro());
        Assertions.assertEquals(informeSinFoto.getLugarDeEncuentro(), informeAux.getLugarDeEncuentro());
        Assertions.assertEquals(informeSinFoto.getRescatista(), informeAux.getRescatista());
    }


    @Test
    @DisplayName("si se genera un InformeMascotaEncontrada sin fotos, se genera " +
            "InformeMascotaEncontradaInvalidaException")
    public void InformeMascotaEncontradaInvalidaExceptionTest() {
        assertThrows(InformeMascotaEncontradaInvalidaException.class, () -> informeSinFoto.procesarInforme());
    }

    @Test
    @DisplayName("Se debe notificar al duenio cuando la mascota tiene chapita")
    public void MascotaConDuenioNotificarTest() throws MessagingException {
        informeConFoto.procesarInforme();
        String subjectEmail = this.getSubjectEmail();
        assertEquals("rescatepatitasdds21@gmail.com", subjectEmail);
    }

    private InformeMascotaConDuenio generarInformeMascotaEncontrada(List<Foto> fotosMascota) {
        return new InformeMascotaConDuenio(
                duenioMascota, rescatista, fechaDeHoy, direccion, fotosMascota, ubicacion, estadoActualMascota,
                repositorioInformes);
    }

    private InformeMascotaConDuenio generarInformeMascotaEncontradaBuilder(List<Foto> fotosMascota) {
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

    private String getSubjectEmail() throws MessagingException {
        Properties prop = new Properties();

        prop.setProperty("mail.pop3.starttls.enable", "false");

        prop.setProperty("mail.pop3.socketFactory.class","javax.net.ssl.SSLSocketFactory" );
        prop.setProperty("mail.pop3.socketFactory.fallback", "false");

        prop.setProperty("mail.pop3.port","995");
        prop.setProperty("mail.pop3.socketFactory.port", "995");

        Session sesion = Session.getInstance(prop);
        //imprime log cuando esta en true
        sesion.setDebug(false);

        Store store = sesion.getStore("pop3");
        store.connect("pop.gmail.com","dds2021g10@gmail.com","viernesNoche21");
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);

        Message[] mensajes = folder.getMessages();
        //retorno el mensaje mas actual
        return mensajes[mensajes.length-1].getFrom()[0].toString();
    }

}
