package utils;

import modelo.asociacion.Asociacion;
import modelo.informe.Ubicacion;
import modelo.mascota.*;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.notificacion.NotificadorCorreo;
import modelo.persona.*;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.ParDeRespuestas;
import modelo.pregunta.RespuestaDelAdoptante;
import modelo.pregunta.RespuestaDelDador;
import modelo.publicacion.DarEnAdopcion;
import modelo.suscripcion.SuscripcionParaAdopcion;
import modelo.suscripcion.Preferencia;
import modelo.publicacion.Rescate;
import modelo.usuario.TipoUsuario;
import modelo.usuario.Usuario;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioRescates;
import modelo.notificacion.Notificador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyData {

  public static DatosDeContacto getDatosDeContacto(Notificador notificador) {
    return new DatosDeContacto("01147474747", "dds2021g10@gmail.com", notificador);
  }

  public static DocumentoIdentidad getDocumentoIdentidad() {
    return new DocumentoIdentidad(TipoDocumento.DNI, "11111111");
  }

  public static Persona getPersona(Notificador notificador) {
    return new Persona("PersonaNombre", "PersonaApellido", getDocumentoIdentidad(), getDatosDeContacto(notificador),
        LocalDate.of(1995, 8, 7), notificador);
  }

  public static Persona getPersonaSinDatosDeContacto() {
    return new Persona("PersonaNombre", "PersonaApellido", getDocumentoIdentidad(), null, LocalDate.of(1995, 8, 7),null);
  }

  public static Persona getPersonaSinDatosDeContactoNiNombreNiApellido() {
    return new Persona(null, null, getDocumentoIdentidad(), new DatosDeContacto(null, null, new NotificadorCorreo("dds2021g10@gmail.com")), LocalDate.of(1995, 8, 7),null);
  }

  public static Persona getPersonaSinTelefono() {
    return new Persona("PersonaNombre", "PersonaApellido", getDocumentoIdentidad(),
        new DatosDeContacto(null, "dds2021g10@gmail.com", new NotificadorCorreo("dds2021g10@gmail.com")), LocalDate.of(1995, 8, 7), null);
  }

  public static Persona getPersonaSinCorreo() {
    return new Persona("PersonaNombre", "PersonaApellido", getDocumentoIdentidad(),
        new DatosDeContacto("01147474747", null, new NotificadorCorreo("dds2021g10@gmail.com")), LocalDate.of(1995, 8, 7),null);
  }

  public static Usuario getUsuario(Notificador notificador) {
    return new Usuario("DuenioMascota", "Password1234", TipoUsuario.NORMAL, getPersona(notificador));
  }

  public static Usuario getUsuarioVoluntario() {
    return new Usuario("voluntario", "Password1234", TipoUsuario.VOLUNTARIO, getPersona(null));
  }

  public static Usuario getUsuarioAdministrador() {
    return new Usuario("Admin", "Password1234", TipoUsuario.ADMIN, getPersona(null));
  }

  public static CaracteristicaConValoresPosibles getCaracteristicaParaAdmin() {
    return new CaracteristicaConValoresPosibles("Comportamiento", Arrays.asList("Inquieto", "Tranquilo"));
  }

  public static List<Caracteristica> getCaracteristicasParaMascota() {
    List<Caracteristica> listaCaracteristica = new ArrayList<>();
    listaCaracteristica.add(new Caracteristica("Comportamiento", "Tranquilo"));
    return listaCaracteristica;
  }

  public static List<Foto> getFotos() {
    List<Foto> fotos = new ArrayList<>();
    fotos.add(new Foto(null, null));
    return fotos;
  }

  public static MascotaEncontrada getMascotaEncontrada(List<Foto> fotos) {
    return new MascotaEncontrada(fotos, getUbicacion(), "Limpio y Sano", LocalDate.now(), TamanioMascota.CHICO);
  }

  public static MascotaRegistrada getMascotaRegistrada(Notificador notificador) {
    return new MascotaRegistrada(getUsuario(notificador), "Felipe", "Panchito", LocalDate.of(2018, 3, 4), "Pelo largo", Sexo.MACHO,
        Animal.PERRO, getCaracteristicasParaMascota(), getFotos(), TamanioMascota.CHICO);
  }

  public static ParDePreguntas getParDePreguntas1() {
    ParDePreguntas preguntas = new ParDePreguntas(
        "La mascota sufre si está mucho tiempo sola?",
        "Va a estar la mascota mucho tiempo sola?"
    );
    preguntas.agregarRespuesta(new ParDeRespuestas("Si", "No"));
    preguntas.agregarRespuesta(new ParDeRespuestas("No", "Si"));
    preguntas.agregarRespuesta(new ParDeRespuestas("No", "No"));
    return preguntas;
  }

  public static ParDePreguntas getParDePreguntas2() {
    ParDePreguntas preguntas = new ParDePreguntas(
        "Cuantas veces necesita salir la mascota al dia?",
        "Cuantas veces sacarás a pasear a tu mascota al dia?"
    );
    preguntas.agregarRespuesta(new ParDeRespuestas("1", "1"));
    preguntas.agregarRespuesta(new ParDeRespuestas("1", "2"));
    preguntas.agregarRespuesta(new ParDeRespuestas("2", "2"));
    preguntas.agregarRespuesta(new ParDeRespuestas("1", "+2"));
    preguntas.agregarRespuesta(new ParDeRespuestas("2", "+2"));
    preguntas.agregarRespuesta(new ParDeRespuestas("+2", "+2"));
    return preguntas;
  }

  public static DarEnAdopcion getPublicacionDeDarEnAdopcion(Notificador notificador,
                                                            RepositorioDarEnAdopcion repositorio) {
    return new DarEnAdopcion(
        getUsuario(notificador),
        getMascotaRegistrada(notificador),
        repositorio,
        Arrays.asList(
            new RespuestaDelDador("Si", getParDePreguntas1()),
            new RespuestaDelDador("2", getParDePreguntas2())
        ),
        getAsociacion()
    );
  }

  public static Rescate getPublicacionDeRescate(Notificador notificador, RepositorioRescates repositorio) {
    return new Rescate(getPersona(notificador), repositorio, getMascotaEncontrada(getFotos()), getAsociacion());
  }

  public static SuscripcionParaAdopcion getSuscripcionParaAdopcion(Notificador notificador) {
    return new SuscripcionParaAdopcion(
        getUsuario(notificador),
        getAsociacion(),
        new Preferencia(getCaracteristicasParaMascota(), Animal.PERRO),
        Arrays.asList(
            new RespuestaDelAdoptante("Si", getParDePreguntas1()),
            new RespuestaDelAdoptante("2", getParDePreguntas2())
        )
    );
  }

  public static Ubicacion getUbicacion() {
    return new Ubicacion(27.23, 25.78, null);
  }

  public static Asociacion getAsociacion() {
    return new Asociacion("unNombre", getUbicacion());
  }

  public static String getJsonHogaresApi() { // Un response con un solo hogar en la lista de hogares.
    return "{\"total\":40,\"offset\":\"1\",\"hogares\":[{\"id\":\"eyJpdiI6IjV6OHZLa1pxK09KZHRkdEZpclBLUl"
        + "E9PSIsInZhbHVlIjoiY3JwNjZKQW1XcjRjaVBOQ3gxNVRjZz09IiwibWFjIjoiODgwODJhN2Y4YjA5MmNmNGE1MWU4NDY5ZWQ4MGZjMDRk"
        + "YjA0Yzg5MjJmMjQ4ODkzNGUxYzNmMjc1ZDBhMWI0MCJ9\",\"nombre\":\"Pensionado de mascotas \\\"Como en casa\\\"\","
        + "\"ubicacion\":{\"direccion\":\"Av. Ing Eduardo Madero 2300, B1669BZQ Del Viso, Provincia de Buenos Aires\","
        + "\"lat\":-34.46013439745161,\"long\":-58.80857841888721},\"telefono\":\"+541164657462\",\"admisiones\":{\""
        + "perros\":false,\"gatos\":true},\"capacidad\":50,\"lugares_disponibles\":45,\"patio\":true,\"caracteristicas"
        + "\":[\"Tranquilo\"]}]}";
  }

}
