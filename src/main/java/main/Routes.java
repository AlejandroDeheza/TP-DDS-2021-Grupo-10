package main;

import controllers.*;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;
import utils.Constantes;
import javax.servlet.MultipartConfigElement;
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

    // Mascotas
    get("/mascotas", mascotasController::mostrarMascotasDelUsuario, engine);
    get("/mascotas/registracion-mascota", mascotasController::mostrarFormularioRegistracionMascotas, engine);
    post("/mascotas", mascotasController::registrarMascota);

    get("/menu-mascotas", mascotasController::mostrarMenuDeMascotas, engine); // FIXME <-- no se usa
    get("/mascotas-en-adopcion", publicacionesController::mostrarMascotasEnAdopcion, engine); // FIXME <-- no se usa

    // Informes
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

    // Caracteristicas
    get("/caracteristicas", caracteristicasController::mostrarCaracteristicas, engine);
    get("/caracteristicas/nueva", caracteristicasController::mostrarFormularioCreacionCaracteristicas, engine);
    post("/caracteristicas", caracteristicasController::crearNuevaCaracteristicas);

    // Preguntas de Asociaciones
    path("/asociaciones", () -> {
      get("", asociacionesController::mostrarAsociaciones, engine);

      path("/:idAsociacion/preguntas", () -> {
        get("",                         preguntasController::mostrarPreguntasDeLaAsociacion, engine);
        get("/nueva-pregunta",          preguntasController::mostrarFomularioNuevaPregunta, engine);
        get("/nueva-pregunta-paso-2",   preguntasController::mostrarFormularioNuevaPreguntaContinuacion, engine);
        post("",                        preguntasController::crearParDePreguntasAsociacion);
      });
    });



    before((request, response) -> {
      if (request.requestMethod().equals("POST")) {
        //Lo separo porque request.contentType() puede ser null si no es POST
        if (request.contentType().startsWith("multipart/form-data")) {
          // Siempre que el ENC-TYPE sea 'multipart/form-data' se debe hacer esto primero
          request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        }
      }
    });

    before((request, response) -> {
      if ((request.requestMethod().equals("POST") || request.requestMethod().equals("PUT")
          || request.requestMethod().equals("DELETE")
      ) && request.session().attribute("user_id") == null
          && !request.pathInfo().equals("/login")
          && !request.pathInfo().equals("/usuarios")) {
        response.redirect("/login");
      }
    });

    before((request, response) -> {
      if ((request.pathInfo().equals("/mascotas")
          || request.pathInfo().equals("/mascotas/registracion-mascota")
          || request.pathInfo().equals("/informes/con-qr/nuevo/:codigoChapita")
          || request.pathInfo().equals("/informes/sin-qr/nuevo")
          || request.pathInfo().equals("/caracteristicas")
          || request.pathInfo().equals("/caracteristicas/nueva")
          || request.pathInfo().equals("/asociaciones")
          || request.pathInfo().equals("/asociaciones/:idAsociacion/preguntas")
          || request.pathInfo().equals("/asociaciones/:idAsociacion/preguntas/nueva-pregunta")
      )
          && request.session().attribute("user_id") == null) {
        response.redirect("/login?origin=" + request.pathInfo());
      }
    });

    before((request, response) -> {
      Boolean esAdmin = request.session().attribute("is_admin");
      if ((request.pathInfo().equals("/caracteristicas")
          || request.pathInfo().equals("/caracteristicas/nueva")
          || request.pathInfo().equals("/asociaciones")
          || request.pathInfo().equals("/asociaciones/:idAsociacion/preguntas")
          || request.pathInfo().equals("/asociaciones/:idAsociacion/preguntas/nueva-pregunta")
      )
          && !esAdmin) {
        halt(403);
      }
    });

    before((request, response) -> {
      if ((request.pathInfo().equals("/login")
          || request.pathInfo().equals("/usuarios/creacion-usuario")
          || request.pathInfo().equals("/usuarios"))
          && request.session().attribute("user_id") != null) {
        response.redirect("/");
      }
    });



    after((request, response) -> {
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
