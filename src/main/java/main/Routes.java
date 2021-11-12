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

    Spark.get("/", homeController::getHome, engine);

    Spark.get("/login", sesionController::mostrarLogin, engine);
    Spark.post("/login", sesionController::crearSesion);
    Spark.get("/logout", sesionController::cerrarSesion);

    Spark.get("/creacion-usuario", usuarioController::mostrarFormularioCreacionUsuario, engine);
    Spark.post("/creacion-usuario", usuarioController::registrarUsuario);
    Spark.get("/admin", usuarioController::mostrarAdmin, engine);

    Spark.get("/caracteristicas", caracteristicasController::mostrarCaracteristicas, engine);
    Spark.get("/nueva-caracteristica", caracteristicasController::cargarNuevaCaracteristica, engine);


    Spark.get("/preguntas-asociaciones", preguntasController::mostrarPreguntasAsociaciones, engine);
    Spark.post("/preguntas-asociaciones", preguntasController::crearParDePreguntasAsociacion);

    Spark.get("/nueva-pregunta", preguntasController::cargarNuevaPreguntaAsociacion, engine);
    Spark.get("/matchear-preguntas", preguntasController::matchearPreguntasAsociacion, engine);

    Spark.get("/mascotas-en-adopcion", publicacionesController::mostrarMascotasEnAdopcion, engine);

    Spark.get("/registracion-mascota", mascotasController::mostrarRegistracion, engine);
    Spark.post("/registracion-mascota", mascotasController::registrarMascota);

    System.out.println("Servidor iniciado!");
  }


}
