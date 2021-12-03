package main;

import modelo.asociacion.Asociacion;
import modelo.informe.Ubicacion;
import modelo.mascota.*;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.persona.DatosDeContacto;
import modelo.persona.DocumentoIdentidad;
import modelo.persona.Persona;
import modelo.persona.TipoDocumento;
import modelo.pregunta.ParDePreguntas;
import modelo.usuario.TipoUsuario;
import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

  RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();
  RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
  RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
  RepositorioParDePreguntas repositorioParDePreguntas = new RepositorioParDePreguntas();
  RepositorioAsociaciones repositorioAsociaciones = new RepositorioAsociaciones();

  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void run() {

    DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad(TipoDocumento.DNI, "12345678");
    DatosDeContacto datosDeContacto = new DatosDeContacto("12345678", "pepito@gmail.com");

    Persona persona = new Persona(
        "Pepito",
        "Gonzalez",
        documentoIdentidad,
        datosDeContacto,
        LocalDate.now(),
        TipoNotificadorPreferido.CORREO
    );

    Usuario usuario = new Usuario("pepito", "asd123asd123", TipoUsuario.NORMAL, persona);

    // Mascota Registrada
    MascotaRegistrada mascotaRegistrada = new MascotaRegistrada(usuario, "Malbec", "coco",
        LocalDate.now(), "Es re bueno y gordo", Sexo.MACHO, Animal.PERRO, null,
        Collections.singletonList(new Foto("/3261071319668366719.jpg", null)),
        TamanioMascota.CHICO);

    MascotaRegistrada mascotaRegistrada2 = new MascotaRegistrada(usuario, "Fucker", "asd",
        LocalDate.now(), "Es re bueno y gordo", Sexo.MACHO, Animal.PERRO, null,
        Collections.singletonList(new Foto("/3261071319668366719.jpg", null)),
        TamanioMascota.CHICO);

    // Caracteristicas
    CaracteristicaConValoresPosibles c1 = new CaracteristicaConValoresPosibles("Comportamiento", Arrays.asList("Inquieto", "Tranquilo"));
    CaracteristicaConValoresPosibles c2 = new CaracteristicaConValoresPosibles("Caracter", Arrays.asList("Pacifico", "Violento"));
    CaracteristicaConValoresPosibles c3 = new CaracteristicaConValoresPosibles("Apetito", Arrays.asList("Poco", "Intermedio", "Mucho"));
    CaracteristicaConValoresPosibles c4 = new CaracteristicaConValoresPosibles("Animo", Arrays.asList("Feliz", "Triste", "Nose"));

    // Asociaciones
    Ubicacion ubicacion1 = new Ubicacion(2000.0, 2100.0, "Medrano 951");
    Ubicacion ubicacion2 = new Ubicacion(219.0, 22.0, "Mozart 1923");
    Ubicacion ubicacion3 = new Ubicacion(334.0, 529.0, "Pedraza 34");

    Asociacion asociacion1 = new Asociacion("Perritos - Palermo", ubicacion1);
    Asociacion asociacion2 = new Asociacion("Garritas", ubicacion2);
    Asociacion asociacion3 = new Asociacion("Una mascota feliz", ubicacion3);

    ParDePreguntas parDePreguntas1 = new ParDePreguntas("¿Tenes balcon?", "¿Necesito Balcon?", true);
    ParDePreguntas parDePreguntas2 = new ParDePreguntas("¿Contas con redes para mascotas?", "¿Necesito redes para mascotas?", true);
    ParDePreguntas parDePreguntas3 = new ParDePreguntas("¿Tenes un veterinario de confianza?", "¿Necesito contar con un veterinario de confianza?", false);

    asociacion1.agregarPregunta(parDePreguntas1);
    asociacion2.agregarPregunta(parDePreguntas3);


    withTransaction(() -> {
      // Usuarios
      repositorioUsuarios.agregar(usuario);

      repositorioMascotaRegistrada.agregar(mascotaRegistrada);
      repositorioMascotaRegistrada.agregar(mascotaRegistrada2);
      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(c1);
      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(c2);
      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(c3);
      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(c4);
      
      // Asociaciones
      repositorioParDePreguntas.agregar(parDePreguntas1);
      repositorioParDePreguntas.agregar(parDePreguntas2);
      repositorioParDePreguntas.agregar(parDePreguntas3);
      repositorioAsociaciones.agregar(asociacion1);
      repositorioAsociaciones.agregar(asociacion2);
      repositorioAsociaciones.agregar(asociacion3);

      repositorioUsuarios.agregar(new Usuario("admin", "asd123asd123", TipoUsuario.ADMIN, persona));
    });

  }

}
