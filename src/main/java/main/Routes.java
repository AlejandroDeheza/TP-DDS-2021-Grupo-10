package main;

import controllers.*;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;
import utils.Constantes;
import java.io.File;

import static spark.Spark.*;

public class Routes {

  public static void main(String[] args) {

    new Bootstrap().run();
    //new PatitasRunner().run(); //TODO: dejo comentado asi no queda corriendo

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
    InformesController informesController = new InformesController();
    PreguntasController preguntasController = new PreguntasController();
    CaracteristicasController caracteristicasController = new CaracteristicasController();
    AsociacionesController asociacionesController = new AsociacionesController();

    get("/", homeController::getHome, engine);
    get("/error", errorController::mostrarPantallaError, engine);

    // Sesion
    get("/login", sesionController::mostrarLogin, engine);
    post("/login", sesionController::crearSesion);
    get("/logout", sesionController::cerrarSesion);

    // Usuarios
    get("/usuarios/creacion-usuario", usuarioController::mostrarFormularioCreacionUsuario, engine);
    post("/usuarios", usuarioController::registrarUsuario);

    // Caracteristicas
    get("/caracteristicas", caracteristicasController::mostrarCaracteristicas, engine);
    get("/caracteristicas/nueva-caracteristica", caracteristicasController::mostrarFormularioCreacionCaracteristicas, engine);
    post("/caracteristicas", caracteristicasController::crearNuevaCaracteristicas);

    // Preguntas de Asociaciones
    get("/asociaciones", asociacionesController::mostrarAsociaciones, engine);
    get("/asociaciones/:idAsociacion/preguntas", preguntasController::mostrarPreguntasDeLaAsociacion, engine);
    get("/asociaciones/:idAsociacion/preguntas/nueva-pregunta",
        preguntasController::mostrarFomularioNuevaPregunta, engine);
    get("/asociaciones/:idAsociacion/preguntas/nueva-pregunta-paso-2",
        preguntasController::mostrarFormularioNuevaPreguntaContinuacion, engine);
    post("/asociaciones/:idAsociacion/preguntas", preguntasController::crearParDePreguntasAsociacion);

    // Mascotas
    get("/mascotas", mascotasController::mostrarMascotasDelUsuario, engine);
    get("/mascotas/registracion-mascota", mascotasController::mostrarFormularioRegistracionMascotas, engine);
    post("/mascotas", mascotasController::registrarMascota);

    get("/menu-mascotas", mascotasController::mostrarMenuDeMascotas, engine); // FIXME <-- no se usa
    get("/mascotas-en-adopcion", publicacionesController::mostrarMascotasEnAdopcion, engine); // FIXME <-- no se usa

    // Informes
    get("/informes/menu", informesController::mostrarMenuTipoEncuentro, engine);
    get("/informes/con-qr/instrucciones-escaneo", informesController::mostrarInstruccionesParaEscanearQR, engine);
    get("/informes/con-qr/nuevo/:codigoChapita", informesController::mostrarFormularioConChapita, engine);
    post("/informes/con-qr/:codigoChapita", informesController::generarInformeConQR);
    get("/informes/sin-qr/nuevo", informesController::mostrarFormularioSinChapita, engine);
    post("/informes/sin-qr", informesController::generarInformeSinQR);

    /*
    path("/informes", () -> {
      get("/menu", informesController::mostrarMenuTipoEncuentro, engine);

      path("/con-qr", () -> {
        get("/instrucciones-escaneo", informesController::mostrarInstruccionesParaEscanearQR, engine);
        get("/nuevo/:codigoChapita",  informesController::mostrarFormularioConChapita, engine);
        post("/:codigoChapita",       informesController::generarInformeConQR);
      });
      path("/sin-qr", () -> {
        get("/nuevo",   informesController::mostrarFormularioSinChapita, engine);
        post("",        informesController::generarInformeSinQR);
      });
    });

     */

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
    return 8080; //return default port if heroku-port isn't set (i.e. on localhost)
  }

}
