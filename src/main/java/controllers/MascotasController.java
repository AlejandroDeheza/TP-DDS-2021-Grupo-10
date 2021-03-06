package controllers;

import modelo.mascota.Animal;
import modelo.mascota.MascotaRegistrada;
import modelo.mascota.Sexo;
import modelo.mascota.TamanioMascota;
import modelo.usuario.Usuario;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioMascotaRegistrada;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class MascotasController extends Controller {

  RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
  RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
  RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();

  public ModelAndView mostrarFormularioRegistracionMascotas(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    modelo.put("caracteristicas", repositorioCaracteristicas.getCaracteristicasConValoresPosibles());
    return new ModelAndView(modelo, "registracion-mascota.html.hbs");
  }

  public Void registrarMascota(Request request, Response response) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    MascotaRegistrada nueva = new MascotaRegistrada(
        repositorioUsuarios.buscarPorId(request.session().attribute("user_id")),
        request.queryParams("nombre"),
        request.queryParams("apodo"),
        LocalDate.parse(request.queryParams("fechaNacimiento"), formatter),
        request.queryParams("descripcionFisica"),
        Sexo.values()[Integer.parseInt(request.queryParams("sexo"))],
        Animal.values()[Integer.parseInt(request.queryParams("tipoAnimal"))],
        super.obtenerListaCaracteristicas(request),
        super.obtenerFotosMascota(request, response),
        TamanioMascota.values()[Integer.parseInt(request.queryParams("tamanio"))]
    );

    withTransaction(() -> {
      repositorioMascotaRegistrada.agregar(nueva);
    });

    response.redirect("/mascotas");
    return null;
  }

  public ModelAndView mostrarMenuDeMascotas(Request request, Response response) {
    return new ModelAndView(getMap(request), "menu-mascotas.html.hbs");
  }

  public ModelAndView mostrarMascotasDelUsuario(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    Usuario usuario = repositorioUsuarios.buscarPorId(request.session().attribute("user_id"));
    modelo.put("mascotasUsuario", repositorioMascotaRegistrada.obtenerMascotasDeUnDuenio(usuario));
    return new ModelAndView(modelo, "mis-mascotas.html.hbs");
  }

  public Void redirigirAInformeConQR(Request request, Response response) {
    request.session().attribute("idMascota", Long.parseLong(request.params(":idMascota")));
    response.redirect("/informes/con-qr/nuevo");
    return null;
  }
}