package modelo.informe;

import excepciones.InformeMascotaEncontradaInvalidaException;
import modelo.mascota.Foto;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.Mascota;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import properties.MisProperties;
import repositorios.RepositorioCaracteristicas;
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
  List<Foto> fotosMascota = DummyData.getDummyFotosMascota();
  List<Foto> fotosMascotaVacio = new ArrayList<>();
  Ubicacion ubicacion = new Ubicacion(57.44, 57.55);
  List<Caracteristica> estadoActualMascota = DummyData.getDummyListaCaracteristicasParaMascota();
  InformeMascotaConDuenio informeConFoto;
  InformeMascotaConDuenio informeSinFoto;
  RepositorioInformes repositorioInformes;
  Mascota mascota = DummyData.getDummyMascota();
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
        duenioMascota, rescatista, fechaDeHoy, ubicacion, fotosMascota, ubicacion, estadoActualMascota);
  }

  private InformeMascotaConDuenio generarInformeMascotaEncontradaBuilder(List<Foto> fotosMascota) {
    InformeMascotaConDuenioBuilder builder = InformeMascotaConDuenioBuilder.crearBuilder();
    builder.conDuenioMascota(duenioMascota)
        .conRescatista(rescatista)
        .conFechaEncuentro(fechaDeHoy)
        .conDireccion(ubicacion)
        .conFotosMascota(fotosMascota)
        .conLugarDeEncuentro(ubicacion)
        .conEstadoActualMascota(estadoActualMascota);
    return builder.build();
  }

  private String getSubjectEmail() throws MessagingException {
    Properties prop = new Properties();
    MisProperties.cargarInfoPropertiesTests(prop);

    Session sesion = Session.getInstance(prop);
    //imprime log cuando esta en true
    sesion.setDebug(false);

    Store store = sesion.getStore("pop3");
    store.connect(
        prop.getProperty("host"),
        prop.getProperty("user"),
        prop.getProperty("password"));
    Folder folder = store.getFolder(prop.getProperty("folder"));
    folder.open(Folder.READ_ONLY);

    Message[] mensajes = folder.getMessages();
    //retorno el mensaje mas actual
    return mensajes[mensajes.length-1].getFrom()[0].toString();
  }

}
