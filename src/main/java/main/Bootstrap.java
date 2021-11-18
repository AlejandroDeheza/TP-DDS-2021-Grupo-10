package main;

import modelo.asociacion.Asociacion;
import modelo.informe.Ubicacion;
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
import repositorios.RepositorioCaracteristicas;

import java.time.LocalDate;
import java.util.Arrays;

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

    Persona persona2 = new Persona(
        "Pepito",
        "Fernandez",
        documentoIdentidad,
        datosDeContacto,
        LocalDate.now(),
        TipoNotificadorPreferido.CORREO
    );

    Ubicacion ubicacion1 = new Ubicacion(2000.0, 2100.0, "Medrano 951");
    Ubicacion ubicacion2 = new Ubicacion(219.0, 22.0, "Mozart 1923");
    Ubicacion ubicacion3 = new Ubicacion(334.0, 529.0, "Pedraza 34");

    Asociacion asociacion1 = new Asociacion("Asociada", ubicacion1);
    Asociacion asociacion2 = new Asociacion("Desasociada", ubicacion2);
    Asociacion asociacion3 = new Asociacion("Corte Logia", ubicacion3);

    ParDePreguntas parDePreguntas1 = new ParDePreguntas("Pregunta del Dador 1", "Pregunta del Adoptante 1", true);
    ParDePreguntas parDePreguntas2 = new ParDePreguntas("Pregunta del Dador 2", "Pregunta del Adoptante 2", true);
    ParDePreguntas parDePreguntas3 = new ParDePreguntas("Pregunta del Dador 3", "Pregunta del Adoptante 3", false);
    ParDePreguntas parDePreguntas4 = new ParDePreguntas("Pregunta del Dador 4", "Pregunta del Adoptante 4", true);

    CaracteristicaConValoresPosibles caracteristicaValores1 = new CaracteristicaConValoresPosibles("características Valores 1", Arrays.asList("Inquieto", "Tranquilo"));
    CaracteristicaConValoresPosibles caracteristicaValores2 = new CaracteristicaConValoresPosibles("características Valores 2", Arrays.asList("Chico", "Grande"));

    RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
    repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(caracteristicaValores1);
    repositorioCaracteristicas.agregarCaracteristicasConValoresPosibles(caracteristicaValores2);

    asociacion1.agregarPregunta(parDePreguntas1);
    asociacion1.agregarPregunta(parDePreguntas2);
    asociacion2.agregarPregunta(parDePreguntas3);
    asociacion3.agregarPregunta(parDePreguntas4);

    withTransaction(() -> {
      persist(new Usuario("pepito", "pepitopepito", TipoUsuario.NORMAL, persona));
      persist(new Usuario("admin", "adminadmin", TipoUsuario.ADMIN, persona2));
      persist(asociacion1);
      persist(asociacion2);
      persist(asociacion3);
    });
  }

}
