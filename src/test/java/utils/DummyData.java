package utils;

import modelo.informe.Ubicacion;
import modelo.mascota.*;
import modelo.mascota.MascotaBuilder;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.persona.DatosDeContacto;
import modelo.persona.Persona;
import modelo.persona.PersonaBuilder;
import modelo.persona.TipoDocumento;
import modelo.publicacion.Publicacion;
import modelo.usuario.TipoUsuario;
import modelo.usuario.Usuario;
import repositorios.RepositorioCaracteristicas;
import servicio.notificacion.NotificacionSender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyData {

  public static DatosDeContacto getDummyDatosDeContacto() {
    return new DatosDeContacto("01147474747", "dds2021g10@gmail.com");
  }

  public static DatosDeContacto getDummyDatosDeContactoSinCorreoAsociado() {
    return new DatosDeContacto("01147474747", null);
  }

  public static Persona getDummyPersona() {
    return PersonaBuilder.crearBuilder().conNombre("PersonaNombre").conApellido("PersonaApellido")
        .conTipoDocumento(TipoDocumento.DNI).conNumeroDeDocumento("11111111")
        .conDatosDeContacto(getDummyDatosDeContacto()).conFechaNacimiento(LocalDate.of(1995, 8, 7))
        .build();
  }

  public static Persona getDummyPersonaSinDatosDeContacto() {
    return PersonaBuilder.crearBuilder().conNombre("PersonaNombre").conApellido("PersonaApellido")
        .conTipoDocumento(TipoDocumento.DNI).conNumeroDeDocumento("11111111")
        .conDatosDeContacto(null).conFechaNacimiento(LocalDate.of(1995, 8, 7)).build();
  }

  public static Persona getDummyPersonaSinDatosDeContactoNiNombreNiApellido() {
    return PersonaBuilder.crearBuilder().conNombre(null).conApellido(null)
        .conTipoDocumento(TipoDocumento.DNI).conNumeroDeDocumento("11111111")
        .conDatosDeContacto(new DatosDeContacto(null, null))
        .conFechaNacimiento(LocalDate.of(1995, 8, 7)).build();
  }

  public static Persona getDummyPersonaSinCorreoAsociadoEnDatosDeContacto() {
    return PersonaBuilder.crearBuilder().conNombre("PersonaNombre").conApellido("PersonaApellido")
        .conTipoDocumento(TipoDocumento.DNI).conNumeroDeDocumento("11111111")
        .conDatosDeContacto(getDummyDatosDeContactoSinCorreoAsociado())
        .conFechaNacimiento(LocalDate.of(1995, 8, 7)).build();
  }

  public static Usuario getDummyUsuario() {
    return new Usuario("DuenioMascota", "Password1234", TipoUsuario.NORMAL, getDummyPersona());
  }

  public static Usuario getDummyUsuarioSinCorreoAsociado() {
    return new Usuario("DuenioMascota", "Password1234", TipoUsuario.NORMAL,
        getDummyPersonaSinCorreoAsociadoEnDatosDeContacto());
  }

  public static Usuario getDummyUsuarioAdministrador() {
    return new Usuario("Admin", "Password1234", TipoUsuario.ADMIN, getDummyPersona());
  }

  public static CaracteristicaConValoresPosibles getDummyCaracteristicaParaAdmin() {
    return new CaracteristicaConValoresPosibles("Comportamiento", Arrays.asList("Bueno", "Malo"));
  }

  public static List<Caracteristica> getDummyListaCaracteristicasParaMascota(
      RepositorioCaracteristicas repositorioCaracteristicas) {
    repositorioCaracteristicas.agregarCaracteristica(getDummyCaracteristicaParaAdmin());
    Caracteristica caracteristica = new Caracteristica(
        "Comportamiento", "Bueno", repositorioCaracteristicas
    );
    List<Caracteristica> listaCaracteristica = new ArrayList<>();
    listaCaracteristica.add(caracteristica);
    return listaCaracteristica;
  }

  public static List<Foto> getDummyFotosMascota() {
    List<Foto> fotosMascota = new ArrayList<>();
    Foto foto = new Foto(null, null);
    fotosMascota.add(foto);
    return fotosMascota;
  }

  public static MascotaEncontrada getDummyMascotaEncontrada(RepositorioCaracteristicas RepositorioCaracteristicas) {
    return new MascotaEncontrada(Animal.PERRO, Sexo.MACHO, "Pelo largo", getDummyListaCaracteristicasParaMascota(RepositorioCaracteristicas), getDummyFotosMascota(), "Limpio y Sano", getDummyContexto(), TamanioMascota.CHICA);

  }

  public static MascotaRegistrada getDummyMascotaRegistrada(RepositorioCaracteristicas RepositorioCaracteristicas) {
    return new MascotaRegistrada(Animal.PERRO, Sexo.MACHO, "Pelo largo", getDummyListaCaracteristicasParaMascota(RepositorioCaracteristicas), getDummyFotosMascota(), "Felipe","Panchito",LocalDate.of(2018, 3, 4));

  }

  public static Usuario getDummyUsuarioVoluntario() {
    return new Usuario("Admin", "Password1234", TipoUsuario.VOLUNTARIO, getDummyPersona());
  }

  public static Publicacion getDummyPublicacion(NotificacionSender notificacionCorreo) {
    return new Publicacion(getDummyDatosDeContacto(), getDummyFotosMascota(), notificacionCorreo);
  }

  public static Contexto getDummyContexto(){
    return new Contexto(LocalDate.of(2021, 3, 4), new Ubicacion(54.22, 72.23));
  }

}
