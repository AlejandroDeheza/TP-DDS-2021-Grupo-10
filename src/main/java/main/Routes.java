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
    EncontreMascotaController encontreMascotaController = new EncontreMascotaController();
    UsuarioController usuarioController = new UsuarioController();
    PublicacionesController publicacionesController = new PublicacionesController();
    MascotasController mascotasController = new MascotasController();

    Spark.get("/", homeController::getHome, engine);

    Spark.get("/login", sesionController::mostrarLogin, engine);
    Spark.post("/login", sesionController::crearSesion);
    Spark.get("/logout", sesionController::cerrarSesion);

    Spark.get("/creacion-usuario", usuarioController::mostrarFormularioCreacionUsuario, engine);
    Spark.post("/creacion-usuario", usuarioController::registrarUsuario);
    Spark.get("/admin", usuarioController::mostrarAdmin, engine);

    Spark.get("/mascotas-en-adopcion", publicacionesController::mostrarMascotasEnAdopcion, engine);

    Spark.get("/registracion-mascota", mascotasController::mostrarRegistracion, engine);
    Spark.post("/registracion-mascota", mascotasController::registrarMascota);


    Spark.get("/mascotas", mascotasController::getRedirectMascotas,
        engine);
    Spark.get("/mascotas/encontre-mascota", encontreMascotaController::getFormularioDatosNoUsuario,
        engine);
    Spark.post("/mascotas/encontre-mascota",
        (request, response) -> encontreMascotaController.enviarDatosNoUsuario(request,
            response));

    Spark.get("/mascotas/encontre-mascota/lugar-encuentro",
        encontreMascotaController::getFormularioLugarEncuentro, engine);
    Spark.post("/mascotas/encontre-mascota/lugar-encuentro",
        (request, response) -> encontreMascotaController.completarLugarEncuentro(request,
            response));


    Spark.post("/mascotas/encontre-mascota/lugar-encuentro/tipo-encuentro",
        (request, response) -> encontreMascotaController.elegirLugarEncuentro(request,
            response));

    Spark.get("/mascotas/encontre-mascota/lugar-encuentro/tipo-encuentro",
        encontreMascotaController::getTiposEncuentros, engine);

    Spark.get("/mascotas/encontre-mascota/lugar-encuentro/tipo-encuentro/con-chapita",
        encontreMascotaController::getFormularioConChapita, engine);


    Spark.post("/mascotas/encontre-mascota/lugar-encuentro/tipo-encuentro/con-chapita",
        (request, response) -> encontreMascotaController.enviarMascotaEncontradaConChapita(request,
            response));

    Spark.get("/mascotas/encontre-mascota/lugar-encuentro/tipo-encuentro/sin-chapita",
        encontreMascotaController::getFormularioSinChapita, engine);
    Spark.post("/mascotas/encontre-mascota/lugar-encuentro/tipo-encuentro/sin-chapita",
        (request, response) -> encontreMascotaController.enviarMascotaEncontradaSinChapita(request,
            response));
    System.out.println("Servidor iniciado!");
  }


}
