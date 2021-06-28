package utils;

import modelo.informe.Ubicacion;
import modelo.mascota.*;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.persona.*;
import modelo.publicacion.DarEnAdopcion;
import modelo.publicacion.Rescate;
import modelo.usuario.TipoUsuario;
import modelo.usuario.Usuario;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioPublicaciones;
import modelo.notificacion.Notificador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyData {

  public static DatosDeContacto getDatosDeContacto() {
    return new DatosDeContacto("01147474747", "dds2021g10@gmail.com");
  }

  public static DocumentoIdentidad getDocumentoIdentidad() {
    return new DocumentoIdentidad(TipoDocumento.DNI, "11111111");
  }

  public static Persona getPersona() {
    return new Persona("PersonaNombre", "PersonaApellido", getDocumentoIdentidad(),
        getDatosDeContacto(), LocalDate.of(1995, 8, 7));
  }

  public static Persona getPersonaSinDatosDeContacto() {
    return new Persona("PersonaNombre", "PersonaApellido", getDocumentoIdentidad(), null,
        LocalDate.of(1995, 8, 7));
  }

  public static Persona getPersonaSinDatosDeContactoNiNombreNiApellido() {
    return new Persona(null, null, getDocumentoIdentidad(), new DatosDeContacto(null, null),
        LocalDate.of(1995, 8, 7));
  }

  public static Persona getPersonaSinTelefono() {
    return new Persona("PersonaNombre", "PersonaApellido", getDocumentoIdentidad(),
        new DatosDeContacto(null, "dds2021g10@gmail.com"), LocalDate.of(1995, 8, 7));
  }

  public static Persona getPersonaSinCorreo() {
    return new Persona("PersonaNombre", "PersonaApellido", getDocumentoIdentidad(),
        new DatosDeContacto("01147474747", null), LocalDate.of(1995, 8, 7));
  }

  public static Usuario getUsuario() {
    return new Usuario("DuenioMascota", "Password1234", TipoUsuario.NORMAL, getPersona());
  }

  public static Usuario getUsuarioVoluntario() {
    return new Usuario("voluntario", "Password1234", TipoUsuario.VOLUNTARIO, getPersona());
  }

  public static Usuario getUsuarioAdministrador() {
    return new Usuario("Admin", "Password1234", TipoUsuario.ADMIN, getPersona());
  }

  public static CaracteristicaConValoresPosibles getCaracteristicaParaAdmin() {
    return new CaracteristicaConValoresPosibles("Comportamiento",
        Arrays.asList("Inquieto", "Tranquilo"));
  }

  /* Publicaciones */
  public static DarEnAdopcion getPublicacionDeDarEnAdopcionCorrecta(Notificador notificador,
      RepositorioPublicaciones repositorioPublicaciones) {
    return new DarEnAdopcion(DummyData.getDatosDeContacto(), DummyData.getUbicacion(), notificador,
        DummyData.getMascotaRegistrada(new RepositorioCaracteristicas()), repositorioPublicaciones);
  }

  public static List<Caracteristica> getCaracteristicasParaMascota(
      RepositorioCaracteristicas repo) {
    repo.agregarCaracteristica(getCaracteristicaParaAdmin());
    List<Caracteristica> listaCaracteristica = new ArrayList<>();
    listaCaracteristica.add(new Caracteristica("Comportamiento", "Tranquilo", repo));
    return listaCaracteristica;
  }

  public static List<Foto> getFotos() {
    List<Foto> fotos = new ArrayList<>();
    fotos.add(new Foto(null, null));
    return fotos;
  }

  public static MascotaEncontrada getMascotaEncontrada(List<Foto> fotos) {
    return new MascotaEncontrada(fotos, getUbicacion(), "Limpio y Sano", LocalDate.now(),
        TamanioMascota.CHICO);
  }

  public static MascotaRegistrada getMascotaRegistrada(
      RepositorioCaracteristicas RepositorioCaracteristicas) {
    return new MascotaRegistrada(getUsuario(), "Felipe", "Panchito", LocalDate.of(2018, 3, 4),
        "Pelo largo", Sexo.MACHO, Animal.PERRO,
        getCaracteristicasParaMascota(RepositorioCaracteristicas), getFotos(),
        TamanioMascota.CHICO);
  }

  public static Rescate getPublicacionDeRescate(Notificador notificacionCorreo,
      RepositorioPublicaciones repositorioPublicaciones) {
    return new Rescate(getDatosDeContacto(), getUbicacion(), notificacionCorreo,
        repositorioPublicaciones, getMascotaEncontrada(getFotos()));
  }

  public static Ubicacion getUbicacion() {
    return new Ubicacion(27.23, 25.78, null);
  }

  public static String getJsonHogaresApi() { // Un response con un solo hogar en la lista de
                                             // hogares.
    return "{\"total\":40,\"offset\":\"1\",\"hogares\":[{\"id\":\"eyJpdiI6IjV6OHZLa1pxK09KZHRkdEZpclBLUl"
        + "E9PSIsInZhbHVlIjoiY3JwNjZKQW1XcjRjaVBOQ3gxNVRjZz09IiwibWFjIjoiODgwODJhN2Y4YjA5MmNmNGE1MWU4NDY5ZWQ4MGZjMDRk"
        + "YjA0Yzg5MjJmMjQ4ODkzNGUxYzNmMjc1ZDBhMWI0MCJ9\",\"nombre\":\"Pensionado de mascotas \\\"Como en casa\\\"\","
        + "\"ubicacion\":{\"direccion\":\"Av. Ing Eduardo Madero 2300, B1669BZQ Del Viso, Provincia de Buenos Aires\","
        + "\"lat\":-34.46013439745161,\"long\":-58.80857841888721},\"telefono\":\"+541164657462\",\"admisiones\":{\""
        + "perros\":false,\"gatos\":true},\"capacidad\":50,\"lugares_disponibles\":45,\"patio\":true,\"caracteristicas"
        + "\":[\"Tranquilo\"]}]}";
  }

}
