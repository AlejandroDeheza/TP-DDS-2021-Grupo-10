package main;

import controllers.*;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Request;
import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;
import utils.Constantes;
import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    File uploadDir = new File( Constantes.getUploadDirectoryPath() );
    uploadDir.mkdir();
    staticFiles.externalLocation( Constantes.getUploadDirectoryPath() );

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
    get("/mascotas/:idMascota/encontrada", mascotasController::redirigirAInformeConQR);

    path("/informes", () -> {
      get("/menu", informesController::mostrarMenuTipoEncuentro, engine);

      path("/con-qr", () -> {
        get("/instrucciones-escaneo", informesController::mostrarInstruccionesParaEscanearQR, engine);
        get("/nuevo",                 informesController::mostrarFormularioConChapita, engine);
        post("",                      informesController::generarInformeConQR);
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

      if (tieneSesionActiva(request)) {

        if (intentaLoguearseORegistrarse(request)) {
          response.redirect("/");
        }
        if (noTieneLosPermisos(request)) {
          halt(403);
        }

      } else {

        if (intentaAlgoConEfectoQueNoSeaLoguearseORegistrarse(request)) {
          response.redirect("/login");
        }
        if (tieneQueEstarLogueado(request)) {
          response.redirect("/login?origin=" + request.pathInfo());
        }

      }
    });

    // Hay que hacer esto de abajo porque lo de arriba no funciona para rutas con PATH PARAMS
    before("/asociaciones/*/preguntas/*", (request, response) -> {
      if (tieneSesionActiva(request)) {
        if (!esAdmin(request)) {
          halt(403);
        }
      } else {
        response.redirect("/login?origin=" + request.pathInfo());
      }
    });

    before("/asociaciones/*/preguntas", (request, response) -> {
      if (tieneSesionActiva(request)) {
        if (!esAdmin(request)) {
          halt(403);
        }
      } else {
        response.redirect("/login?origin=" + request.pathInfo());
      }
    });

    before("/mascotas/*/encontrada", (request, response) -> {
      if (!tieneSesionActiva(request)) {
        response.redirect("/login?origin=" + request.pathInfo());
      }
    });

    after((request, response) -> {
      PerThreadEntityManagers.getEntityManager();
      PerThreadEntityManagers.closeEntityManager();
    });

    System.out.println("Servidor iniciado!");
  }


  private static Boolean tieneSesionActiva(Request request) {
    return request.session().attribute("user_id") != null;
  }

  private static Boolean esAdmin(Request request) {
    return request.session().attribute("is_admin");
  }

  private static boolean intentaLoguearseORegistrarse(Request request) {
    return rutasFormsLoginYRegistracion().contains(request.pathInfo());
  }

  private static boolean noTieneLosPermisos(Request request) {
    return rutasDondeNecesitaSerAdmin().contains(request.pathInfo()) && !esAdmin(request);
  }

  private static boolean intentaAlgoConEfectoQueNoSeaLoguearseORegistrarse(Request request) {
    return metodosConEfecto().contains(request.requestMethod())
        && !rutasLoginYRegistracion().contains(request.pathInfo());
  }

  private static boolean tieneQueEstarLogueado(Request request) {
    return rutasDondeNecesitaEstarLogueado().contains(request.pathInfo());
  }

  private static List<String> metodosConEfecto() {
    return Arrays.asList("POST", "PUT", "DELETE");
  }

  private static List<String> rutasFormsLoginYRegistracion() {
    return Arrays.asList("/login", "/usuarios/creacion-usuario", "/usuarios");
  }

  private static List<String> rutasDondeNecesitaSerAdmin() {
    return Arrays.asList("/caracteristicas", "/caracteristicas/nueva", "/asociaciones",
        "/asociaciones/:idAsociacion/preguntas", "/asociaciones/:idAsociacion/preguntas/nueva-pregunta");
  }

  private static List<String> rutasLoginYRegistracion() {
    return Arrays.asList("/login", "/usuarios");
  }

  private static List<String> rutasDondeNecesitaEstarLogueado() {
    List<String> lista = new ArrayList<>();
    lista.addAll(Arrays.asList("/mascotas", "/mascotas/registracion-mascota", "/mascotas/:idMascota/encontrada",
        "/informes/sin-qr/nuevo"));
    lista.addAll(rutasDondeNecesitaSerAdmin());
    return lista;
  }

  static int getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
      return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 8080; //return default port if heroku-port isn't set (i.e. on localhost)
  }

}
