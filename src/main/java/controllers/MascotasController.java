package controllers;

import modelo.mascota.*;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.usuario.Usuario;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioMascotaRegistrada;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class MascotasController extends Controller {

  RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
  RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
  RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();

  public ModelAndView mostrarRegistracion(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/login?origin=/registracion-mascota");
      return null;
    }
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

    redireccionCasoFeliz(request, response, "/", "MASCOTA_REGISTRADA");
    return null;
  }

  public ModelAndView getRedirectMascotas(Request request, Response response) {
    return new ModelAndView(getMap(request), "menu-mascotas.html.hbs");
  }

  public ModelAndView getMascotasDeUsuario(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/login");
      return null;
    }
    // OBtener las mascotas del usuario que pidio esto
    Usuario usuario = repositorioUsuarios.buscarPorId(request.session().attribute("user_id"));

    Map<String, Object> modelo = getMap(request);
    modelo.put("mascotasUsuario", repositorioMascotaRegistrada.obtenerMascotasDeUnDuenio(usuario));
    return new ModelAndView(modelo, "mis-mascotas.html.hbs");
  }
}