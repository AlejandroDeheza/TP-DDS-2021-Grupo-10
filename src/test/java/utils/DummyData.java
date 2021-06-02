package utils;

import modelo.mascota.Animal;
import modelo.mascota.Foto;
import modelo.mascota.Mascota;
import modelo.mascota.Sexo;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.persona.DatosDeContacto;
import modelo.persona.Persona;
import modelo.persona.PersonaBuilder;
import modelo.persona.TipoDocumento;
import modelo.usuario.TipoUsuario;
import modelo.usuario.Usuario;
import repositorios.RepositorioCaracteristicas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyData {

  public static DatosDeContacto getDummyDatosDeContacto() {
    return new DatosDeContacto("01147474747", "pablo@mail.com");
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

  public static Usuario getDummyUsuario() {
    return new Usuario("DuenioMascota", "Password1234", TipoUsuario.NORMAL, getDummyPersona());
  }

  public static Usuario getDummyUsuarioAdministrador() {
    return new Usuario("Admin", "Password1234", TipoUsuario.ADMIN, getDummyPersona());
  }

  public static CaracteristicaConValoresPosibles getDummyCaracteristicaParaAdmin() {
    return new CaracteristicaConValoresPosibles("Comportamiento", Arrays.asList("Bueno", "Malo"));
  }

  public static List<Caracteristica> getDummyListaCaracteristicasParaMascota(
      RepositorioCaracteristicas repo) {
    Caracteristica caracteristica = new Caracteristica("Comportamiento", "Bueno", repo);
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

  // TODO: Implementar MascotaBuilder
  public static Mascota getDummyMascota(RepositorioCaracteristicas repo) {
    return new Mascota(Animal.PERRO, "Perro", "PerroApodo", LocalDate.of(2018, 3, 4), Sexo.MACHO,
        "Descripcion Dummy", getDummyListaCaracteristicasParaMascota(repo), getDummyFotosMascota());
  }

}
