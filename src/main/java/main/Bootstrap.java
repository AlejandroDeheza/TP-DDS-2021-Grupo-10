package main;

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

import java.time.LocalDate;

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

    withTransaction(() -> {
      persist(new Usuario("pepito", "pepitopepito", TipoUsuario.NORMAL, persona));
      // TODO: ver cual seria la carga inicial
    });
  }

}
