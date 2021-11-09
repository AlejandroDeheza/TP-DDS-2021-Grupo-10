package main;

import controllers.HomeController;
import controllers.SesionController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Routes {

  public static void main(String[] args) {
    System.out.println("Corriendo bootstrap...");
    //  new Bootstrap().run();

    System.out.println("Iniciando servidor...");
    Spark.port(8080);
    Spark.staticFileLocation("/public");


    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
    HomeController homeController = new HomeController();
    SesionController sesionController = new SesionController();

    Spark.get("/", homeController::getHome, engine);
    Spark.get("/login", sesionController::mostrarLogin, engine);
    Spark.post("/login", sesionController::crearSesion);
    Spark.get("/admin", sesionController::mostrarAdmin, engine);
    Spark.after((request, response) -> {
      // TODO franco se los pasa
      //PerThreadEntityManagers.closeEntityManager();;
    });

    System.out.println("Servidor iniciado!");
  }


}
