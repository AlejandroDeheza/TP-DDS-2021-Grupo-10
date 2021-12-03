package main;

import static spark.Spark.staticFiles;

import modelo.adopcion.RecomendadorDeAdopciones;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import controllers.*;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;
import utils.Constantes;

import java.io.File;

public class Routes {

  public static void main(String[] args) {
    System.out.println("Corriendo bootstrap...");
    // new Bootstrap().run();
    new PatitasRunner().run(); //TODO: dejo comentado asi no queda corriendo

    System.out.println("Iniciando servidor...");
    Spark.port(getHerokuAssignedPort());
    Spark.staticFileLocation("/public");
    DebugScreen.enableDebugScreen();

    // Se crea el directorio para subir las fotos :)
    File uploadDir = new File(new Constantes().getUploadDirectory());
    uploadDir.mkdir();
      staticFiles.externalLocation(new Constantes().getUploadDirectory());

    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    HomeController homeController = new HomeController();
    SesionController sesionController = new SesionController();
    UsuarioController usuarioController = new UsuarioController();
    PublicacionesController publicacionesController = new PublicacionesController();
    MascotasController mascotasController = new MascotasController();
    ErrorController errorController = new ErrorController();
    EncontreMascotaController encontreMascotaController = new EncontreMascotaController();
    PreguntasController preguntasController = new PreguntasController();
    CaracteristicasController caracteristicasController = new CaracteristicasController();
    AsociacionesController asociacionesController = new AsociacionesController();

    Spark.get("/", homeController::getHome, engine);

    Spark.get("/login", sesionController::mostrarLogin, engine);
    Spark.post("/login", sesionController::crearSesion);
    Spark.get("/logout", sesionController::cerrarSesion);

    Spark.get("/creacion-usuario", usuarioController::mostrarFormularioCreacionUsuario, engine);
    Spark.post("/creacion-usuario", usuarioController::registrarUsuario);

    Spark.get("/caracteristicas", caracteristicasController::mostrarCaracteristicas, engine);
    Spark.post("/caracteristicas", caracteristicasController::crearNuevaCaracteristicas);
    Spark.get("/nueva-caracteristica", caracteristicasController::cantidadCaracteristicas, engine);

    Spark.get("/asociaciones", asociacionesController::mostrarAsociaciones, engine);
    Spark.get("/asociaciones/:idAsociacion/preguntas", preguntasController::mostrarPreguntasDeLaAsociacion, engine);
    Spark.get("/asociaciones/:idAsociacion/preguntas/nueva-pregunta", preguntasController::nuevaPregunta, engine);
    Spark.get("/asociaciones/:idAsociacion/preguntas/nueva-pregunta-2", preguntasController::matchearRespuestasPosibles, engine);
    Spark.post("/asociaciones/:idAsociacion/preguntas", preguntasController::crearParDePreguntasAsociacion);

    Spark.get("/mascotas-en-adopcion", publicacionesController::mostrarMascotasEnAdopcion, engine);

    Spark.get("/mascotas/registracion-mascota", mascotasController::mostrarRegistracion, engine);
    Spark.post("/registracion-mascota", mascotasController::registrarMascota);

    Spark.get("/mascotas", mascotasController::getRedirectMascotas,
        engine);
    Spark.get("/mascotas/encontre-mascota", encontreMascotaController::getTiposEncuentros,
        engine);

    // FIXME: remove after finish Entrega 6
    Spark.get("/mascotas/mis-mascotas", mascotasController::getMascotasDeUsuario, engine);

    Spark.get("/mascotas/encontre-mascota/con-chapita",
        encontreMascotaController::getInformacionEscaneo, engine);

    Spark.get("/mascotas/encontre-mascota/con-chapita/:codigoChapita",
        encontreMascotaController::getFormularioConChapita, engine);

    Spark.post("/mascotas/encontre-mascota/con-chapita/:codigoChapita",
        encontreMascotaController::enviarMascotaEncontradaConChapita);

    Spark.get("/mascotas/encontre-mascota/sin-chapita",
        encontreMascotaController::getFormularioSinChapita, engine);
    Spark.post("/mascotas/encontre-mascota/sin-chapita",
        encontreMascotaController::enviarMascotaEncontradaSinChapita);


    Spark.get("/error", errorController::mostrarPantallaError, engine);

    Spark.after((request, response) -> {
      PerThreadEntityManagers.getEntityManager();
      PerThreadEntityManagers.closeEntityManager();
    });


    System.out.println("Servidor iniciado!");
  }

  static int getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
      return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
  }

}
