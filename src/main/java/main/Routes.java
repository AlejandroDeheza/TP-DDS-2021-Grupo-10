package main;

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

    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    HomeController homeController = new HomeController();
    SesionController sesionController = new SesionController();
    UsuarioController usuarioController = new UsuarioController();
    PublicacionesController publicacionesController = new PublicacionesController();
    MascotasController mascotasController = new MascotasController();
    PreguntasController preguntasController = new PreguntasController();
    CaracteristicasController caracteristicasController = new CaracteristicasController();
    AsociacionesController asociacionesController = new AsociacionesController();
    ErrorController errorController = new ErrorController();

    Spark.get("/", homeController::getHome, engine);

    Spark.get("/login", sesionController::mostrarLogin, engine);
    Spark.post("/login", sesionController::crearSesion);
    Spark.get("/logout", sesionController::cerrarSesion);

    Spark.get("/creacion-usuario", usuarioController::mostrarFormularioCreacionUsuario, engine);
    Spark.post("/creacion-usuario", usuarioController::registrarUsuario);
    Spark.get("/admin", usuarioController::mostrarAdmin, engine);

    Spark.get("/caracteristicas", caracteristicasController::mostrarCaracteristicas, engine);
    Spark.get("/nueva-caracteristica", caracteristicasController::cargarNuevaCaracteristica, engine);

    Spark.get("/asociaciones", asociacionesController::mostrarAsociaciones, engine);
    Spark.get("/asociaciones/0/preguntas", preguntasController::mostrarPreguntasObligatoriasDeLasAsociaciones, engine);
    Spark.get("/asociaciones/:idAsociacion/preguntas", preguntasController::mostrarPreguntasDeLaAsociacion, engine);
    Spark.post("/asociaciones/:idAsociacion/preguntas", preguntasController::crearParDePreguntasAsociacion);
    Spark.get("/asociaciones/:idAsociacion/preguntas/nueva-pregunta", preguntasController::agregarNuevaPreguntaALaAsociacion, engine);
    Spark.get("/asociaciones/:idAsociacion/preguntas/nueva-pregunta-2", preguntasController::matchearRespuestasPosibles, engine);

    Spark.get("/mascotas-en-adopcion", publicacionesController::mostrarMascotasEnAdopcion, engine);

    Spark.get("/registracion-mascota", mascotasController::mostrarRegistracion, engine);
    Spark.post("/registracion-mascota", mascotasController::registrarMascota);

    Spark.get("/error", errorController::mostrarPantallaError, engine);

    System.out.println("Servidor iniciado!");
  }


}
