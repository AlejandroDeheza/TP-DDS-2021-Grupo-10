package main;

import modelo.mascota.Animal;
import modelo.mascota.Foto;
import modelo.mascota.MascotaRegistrada;
import modelo.mascota.Sexo;
import modelo.mascota.TamanioMascota;
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
import repositorios.RepositorioMascotaRegistrada;
import repositorios.RepositorioUsuarios;

import java.time.LocalDate;
import java.util.ArrayList;
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

    Usuario usuario = new Usuario("pepito", "pepitopepito", TipoUsuario.NORMAL, persona);
    Foto foto = new Foto("coco.jpg", null);
    List<Foto> fotos = new ArrayList<>();
    fotos.add(foto);
    MascotaRegistrada mascotaRegistrada = new MascotaRegistrada(usuario, "Perrito", "coco",
        LocalDate.now(), "Es re bueno y gordo", Sexo.MACHO, Animal.PERRO, null, fotos,
        TamanioMascota.CHICO);

    RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();

    withTransaction(() -> {
      persist(usuario);
      repositorioMascotaRegistrada.agregar(mascotaRegistrada);
      System.out.println(repositorioMascotaRegistrada.getPorNombre(mascotaRegistrada.getNombre()).getId());
      // TODO: ver cual seria la carga inicial
    });
  }

}
