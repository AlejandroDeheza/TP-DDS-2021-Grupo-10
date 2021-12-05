package main;

import modelo.asociacion.Asociacion;
import modelo.informe.Ubicacion;
import modelo.mascota.*;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.persona.DatosDeContacto;
import modelo.persona.DocumentoIdentidad;
import modelo.persona.Persona;
import modelo.persona.TipoDocumento;
import modelo.pregunta.ParDePreguntas;
import modelo.pregunta.ParDeRespuestas;
import modelo.pregunta.RespuestaDelAdoptante;
import modelo.pregunta.RespuestaDelDador;
import modelo.publicacion.DarEnAdopcion;
import modelo.suscripcion.Preferencia;
import modelo.suscripcion.SuscripcionParaAdopcion;
import modelo.usuario.TipoUsuario;
import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

  RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();
  RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
  RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
  RepositorioParDePreguntas repositorioParDePreguntas = new RepositorioParDePreguntas();
  RepositorioAsociaciones repositorioAsociaciones = new RepositorioAsociaciones();
  RepositorioSuscripcionesParaAdopciones repositorioSuscripcionesParaAdopciones = new RepositorioSuscripcionesParaAdopciones();
  RepositorioDarEnAdopcion repositorioDarEnAdopcion = new RepositorioDarEnAdopcion();

  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void run() {

    DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad(TipoDocumento.DNI, "12345678");
    DatosDeContacto datosDeContacto = new DatosDeContacto("12345678", "dds2021g10@gmail.com");

    Persona persona = new Persona(
        "Pepito",
        "Gonzalez",
        documentoIdentidad,
        datosDeContacto,
        LocalDate.now(),
        TipoNotificadorPreferido.CORREO
    );

    Usuario pepito = new Usuario("pepito", "asd123asd123", TipoUsuario.NORMAL, persona);


    // Caracteristicas
    CaracteristicaConValoresPosibles c1 = new CaracteristicaConValoresPosibles(
        "Comportamiento", Arrays.asList("Inquieto", "Tranquilo"));

    CaracteristicaConValoresPosibles c2 = new CaracteristicaConValoresPosibles(
        "Caracter", Arrays.asList("Pacifico", "Violento"));

    CaracteristicaConValoresPosibles c3 = new CaracteristicaConValoresPosibles(
        "Apetito", Arrays.asList("Poco", "Intermedio", "Mucho"));

    CaracteristicaConValoresPosibles c4 = new CaracteristicaConValoresPosibles(
        "Animo", Arrays.asList("Feliz", "Triste", "Nose"));

    // Asociaciones
    Ubicacion ubicacion1 = new Ubicacion(2000.0, 2100.0, "Medrano 951");
    Ubicacion ubicacion2 = new Ubicacion(219.0, 22.0, "Mozart 1923");
    Ubicacion ubicacion3 = new Ubicacion(334.0, 529.0, "Pedraza 34");

    Asociacion asociacion1 = new Asociacion("Perritos - Palermo", ubicacion1);
    Asociacion asociacion2 = new Asociacion("Garritas", ubicacion2);
    Asociacion asociacion3 = new Asociacion("Una mascota feliz", ubicacion3);

    ParDePreguntas parDePreguntas1 = new ParDePreguntas(
        "¿Tenes balcon?",
        "¿Necesito Balcon?",
        false,
        null,
        null,
        null
    );
    ParDePreguntas parDePreguntas2 = new ParDePreguntas(
        "¿Tenes un veterinario de confianza?",
        "¿Necesito contar con un veterinario de confianza?",
        false,
        null,
        null,
        null
    );

    asociacion1.agregarPregunta(parDePreguntas1);
    asociacion2.agregarPregunta(parDePreguntas2);


    withTransaction(() -> {

      repositorioUsuarios.agregar(pepito);
      repositorioUsuarios.agregar(new Usuario("admin", "asd123asd123", TipoUsuario.ADMIN, persona));

      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(c1);
      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(c2);
      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(c3);
      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(c4);

      repositorioParDePreguntas.agregar(parDePreguntas1);
      repositorioParDePreguntas.agregar(parDePreguntas2);

      repositorioAsociaciones.agregar(asociacion1);
      repositorioAsociaciones.agregar(asociacion2);
      repositorioAsociaciones.agregar(asociacion3);

    });

    // Para probar las notificacion de recomendaciones

    List<Caracteristica> listaCaracteristica = new ArrayList<>();
    listaCaracteristica.add(
        repositorioCaracteristicas.getCaracteristicaSegun("Comportamiento", "Tranquilo")
    );
    listaCaracteristica.add(
        repositorioCaracteristicas.getCaracteristicaSegun("Caracter", "Pacifico")
    );


    // Mascota Registrada
    MascotaRegistrada mascotaRegistrada3 = new MascotaRegistrada(pepito, "coco", "coco",
        LocalDate.now(), "Es re bueno y gordo", Sexo.MACHO, Animal.PERRO, listaCaracteristica,
        Collections.singletonList(new Foto("/images/dog.jpg", null)),
        TamanioMascota.CHICO);

    Preferencia preferencia = new Preferencia(listaCaracteristica, Animal.PERRO);


    // Preguntas
    List<String> respuestasPosiblesDelDador1 = new ArrayList<>();
    List<String> respuestasPosiblesDelAdoptante1 = new ArrayList<>();
    List<ParDeRespuestas> paresDeRespuestas1 = new ArrayList<>();

    respuestasPosiblesDelDador1.add("Si");
    respuestasPosiblesDelDador1.add("No");

    respuestasPosiblesDelAdoptante1.add("Si");
    respuestasPosiblesDelAdoptante1.add("No");

    paresDeRespuestas1.add(new ParDeRespuestas("Si", "No"));
    paresDeRespuestas1.add(new ParDeRespuestas("No", "Si"));
    paresDeRespuestas1.add(new ParDeRespuestas("No", "No"));

    ParDePreguntas preguntas1 = new ParDePreguntas(
        "La mascota sufre si está mucho tiempo sola?",
        "Va a estar la mascota mucho tiempo sola?",
        true,
        respuestasPosiblesDelDador1,
        respuestasPosiblesDelAdoptante1,
        paresDeRespuestas1
    );


    List<String> respuestasPosiblesDelDador2 = new ArrayList<>();
    List<String> respuestasPosiblesDelAdoptante2 = new ArrayList<>();
    List<ParDeRespuestas> paresDeRespuestas2 = new ArrayList<>();

    respuestasPosiblesDelDador2.add("1");
    respuestasPosiblesDelDador2.add("2");
    respuestasPosiblesDelDador2.add("+2");

    respuestasPosiblesDelAdoptante2.add("1");
    respuestasPosiblesDelAdoptante2.add("2");
    respuestasPosiblesDelAdoptante2.add("+2");

    paresDeRespuestas2.add(new ParDeRespuestas("1", "1"));
    paresDeRespuestas2.add(new ParDeRespuestas("2", "2"));
    paresDeRespuestas2.add(new ParDeRespuestas("+2", "+2"));

    ParDePreguntas preguntas2 = new ParDePreguntas(
        "Cuantas veces necesita salir la mascota al dia?",
        "Cuantas veces sacarás a pasear a tu mascota al dia?",
        true,
        respuestasPosiblesDelDador2,
        respuestasPosiblesDelAdoptante2,
        paresDeRespuestas2
    );


    List<RespuestaDelAdoptante> respuestasDelAdoptante = Arrays.asList(
        new RespuestaDelAdoptante("Si", preguntas1),
        new RespuestaDelAdoptante("2", preguntas2)
    );

    List<RespuestaDelDador> respuestasDelDador = Arrays.asList(
        new RespuestaDelDador("Si", preguntas1),
        new RespuestaDelDador("2", preguntas2)
    );

    withTransaction(() -> {
      repositorioMascotaRegistrada.agregar(mascotaRegistrada3);

      repositorioSuscripcionesParaAdopciones.agregar(
          new SuscripcionParaAdopcion(pepito, asociacion1, preferencia, respuestasDelAdoptante)
      );
      repositorioDarEnAdopcion.agregar(
          new DarEnAdopcion(pepito, mascotaRegistrada3, respuestasDelDador, asociacion1)
      );
    });
  }

}
