package main;

import controllers.HomeController;
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

    Spark.get("/", homeController::getHome, engine);

    Spark.after((request, response) -> {
      // TODO franco se los pasa
      //PerThreadEntityManagers.closeEntityManager();;
    });

    System.out.println("Servidor iniciado!");
  }


}
