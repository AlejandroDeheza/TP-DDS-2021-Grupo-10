package main;

import modelo.mascota.Animal;
import modelo.mascota.Foto;
import modelo.mascota.MascotaRegistrada;
import modelo.mascota.Sexo;
import modelo.mascota.TamanioMascota;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.persona.DatosDeContacto;
import modelo.persona.DocumentoIdentidad;
import modelo.persona.Persona;
import modelo.persona.TipoDocumento;
import modelo.usuario.TipoUsuario;
import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioMascotaRegistrada;
import utils.Constantes;

import static spark.Spark.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void run() {
    DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad(
        TipoDocumento.DNI,
        "12345678"
    );

    DatosDeContacto datosDeContacto = new DatosDeContacto(
        "12345678",
        "pepito@gmail.com"
    );


    Persona persona = new Persona(
        "Pepito",
        "Gonzalez",
        documentoIdentidad,
        datosDeContacto,
        LocalDate.now(),
        TipoNotificadorPreferido.CORREO
    );

    RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();
    RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();

    Usuario usuario = new Usuario("pepito", "asd123asd123", TipoUsuario.NORMAL, persona);

    // Mascota Registrada
    MascotaRegistrada mascotaRegistrada = new MascotaRegistrada(usuario, "Perrito", "coco",
        LocalDate.now(), "Es re bueno y gordo", Sexo.MACHO, Animal.PERRO, null, Arrays.asList(new Foto("coco.jpg", null)),
        TamanioMascota.CHICO);

    // Caracteristicas
    CaracteristicaConValoresPosibles c5 =
        new CaracteristicaConValoresPosibles("Comportamiento", Arrays.asList("Inquieto", "Tranquilo"));

    CaracteristicaConValoresPosibles c4 =
        new CaracteristicaConValoresPosibles("Animo", Arrays.asList("Feliz", "Triste", "Nose"));
    CaracteristicaConValoresPosibles c1 = new CaracteristicaConValoresPosibles("Comportamiento", Arrays.asList("Inquieto", "Tranquilo"));
    CaracteristicaConValoresPosibles c2 = new CaracteristicaConValoresPosibles("Caracter", Arrays.asList("Pacifico", "Violento"));
    CaracteristicaConValoresPosibles c3 = new CaracteristicaConValoresPosibles("Apetito", Arrays.asList("Poco", "Intermedio", "Mucho"));



    withTransaction(() -> {
      persist(usuario);
      repositorioMascotaRegistrada.agregar(mascotaRegistrada);
      System.out.println(repositorioMascotaRegistrada.getPorNombre(mascotaRegistrada.getNombre()).getId());
      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(c1);
      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(c2);
      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(c3);
      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(c4);
      repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(c5);
    });

    // Se crea el directorio para subir las fotos :)
    File uploadDir = new File(Constantes.UPLOAD_DIRECTORY);
    uploadDir.mkdir();
    staticFiles.externalLocation(Constantes.UPLOAD_DIRECTORY);


  }

}
