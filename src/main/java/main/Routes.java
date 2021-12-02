package main;

import static spark.Spark.staticFiles;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import controllers.*;
import spark.Spark;
import spark.debug.DebugScreen;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Routes {

  public static void main(String[] args) {
    System.out.println("Corriendo bootstrap...");
    new Bootstrap().run();

    System.out.println("Iniciando servidor...");
    Spark.port(8080);
    Spark.staticFileLocation("/public");
    DebugScreen.enableDebugScreen();
    staticFiles.externalLocation("/public/images");
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
    Spark.get("/mascotas/mis-mascotas", (request, response) -> mascotasController.getMascotasDeUsuario(request,response), engine);

    Spark.get("/mascotas/encontre-mascota/con-chapita",
        encontreMascotaController::getFormularioConChapita, engine);

    Spark.post("/mascotas/encontre-mascota/con-chapita",
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

}
