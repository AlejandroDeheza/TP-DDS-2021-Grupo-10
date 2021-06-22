package utils;

import modelo.informe.Ubicacion;
import modelo.mascota.*;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.persona.*;
import modelo.publicacion.Publicacion;
import modelo.usuario.TipoUsuario;
import modelo.usuario.Usuario;
import repositorios.RepositorioCaracteristicas;
import modelo.notificacion.Notificador;

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

  public static DocumentoIdentidad getDummyDocumentoIdentidad() {
    return new DocumentoIdentidad(TipoDocumento.DNI, "11111111");
  }

  public static Persona getDummyPersona() {
    return new Persona("PersonaNombre", "PersonaApellido", getDummyDocumentoIdentidad(),
        getDummyDatosDeContacto(), LocalDate.of(1995, 8, 7));
  }

  public static Persona getDummyPersonaSinDatosDeContacto() {
    return new Persona("PersonaNombre", "PersonaApellido", getDummyDocumentoIdentidad(),
        null, LocalDate.of(1995, 8, 7));
  }

  public static Persona getDummyPersonaSinDatosDeContactoNiNombreNiApellido() {
    return new Persona(null, null, getDummyDocumentoIdentidad(),
        new DatosDeContacto(null, null), LocalDate.of(1995, 8, 7));
  }

  public static Persona getDummyPersonaSinCorreoAsociadoEnDatosDeContacto() {
    return new Persona("PersonaNombre", "PersonaApellido", getDummyDocumentoIdentidad(),
        getDummyDatosDeContactoSinCorreoAsociado(), LocalDate.of(1995, 8, 7));
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

  public static MascotaEncontrada getDummyMascotaEncontrada(RepositorioCaracteristicas RepositorioCaracteristicas, List<Foto> fotos) {
    return new MascotaEncontrada("Limpio y Sano", getDummyContexto(), fotos, TamanioMascota.CHICO);

  }

  public static MascotaRegistrada getDummyMascotaRegistrada(RepositorioCaracteristicas RepositorioCaracteristicas) {
    return new MascotaRegistrada(getDummyUsuario(),"Felipe","Panchito", LocalDate.of(2018, 3, 4),
        "Pelo largo", Sexo.MACHO, Animal.PERRO, getDummyListaCaracteristicasParaMascota(RepositorioCaracteristicas),
        getDummyFotosMascota(), TamanioMascota.CHICO);

  }

  public static Usuario getDummyUsuarioVoluntario() {
    return new Usuario("Admin", "Password1234", TipoUsuario.VOLUNTARIO, getDummyPersona());
  }

  public static Publicacion getDummyPublicacion(Notificador notificacionCorreo) {
    return new Publicacion(getDummyMascotaEncontrada(new RepositorioCaracteristicas(), getDummyFotosMascota()),
        getDummyDatosDeContacto(), notificacionCorreo);
  }

  public static Contexto getDummyContexto(){
    return new Contexto(LocalDate.now(), getDummyUbicacion());
  }

  public static Ubicacion getDummyUbicacion(){
    return new Ubicacion(27.23, 25.78);
  }

}
