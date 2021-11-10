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
    AdminController adminController = new AdminController();
    PublicacionesController publicacionesController = new PublicacionesController();
    MascotasController mascotasController = new MascotasController();

    Spark.get("/", homeController::getHome, engine);

    Spark.get("/login", sesionController::mostrarLogin, engine);
    Spark.post("/login", sesionController::crearSesion);
    Spark.get("/logout", sesionController::cerrarSesion);
    Spark.get("/registracion", sesionController::mostrarRegistracion, engine);
    Spark.post("/registracion", sesionController::registrar);

    Spark.get("/admin", adminController::mostrarAdmin, engine);

    Spark.get("/mascotas-en-adopcion", publicacionesController::mostrarMascotasEnAdopcion, engine);
    Spark.get("/registracion-mascota", mascotasController::mostrarRegistracion, engine);

    Spark.after((request, response) -> {
      // TODO franco se los pasa
      //PerThreadEntityManagers.closeEntityManager();;
    });

    System.out.println("Servidor iniciado!");
  }


}
