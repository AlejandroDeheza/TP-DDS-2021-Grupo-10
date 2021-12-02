package controllers;

import excepciones.AutenticacionConsecutivaException;
import excepciones.AutenticacionInvalidaException;
import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.ValidadorAutenticacionNuevo;
import java.time.LocalTime;
import java.util.NoSuchElementException;

public class SesionController extends Controller implements WithGlobalEntityManager, TransactionalOps {

  public ModelAndView mostrarLogin(Request request, Response response) {
    if (tieneSesionActiva(request)) {
      response.redirect("/");
      return null;
    }
    return new ModelAndView(getMap(request), "login.html.hbs");
  }

  public Void crearSesion(Request request, Response response) {
    try {
      Usuario usuario = new RepositorioUsuarios().buscarPorUsuario(request.queryParams("username"));

      if (request.session().attribute("ultimo_intento_sesion_fallido") == null ||
          request.session().attribute("contador_intentos_sesion_fallidos") == null) {
        request.session().attribute("ultimo_intento_sesion_fallido", LocalTime.now());
        request.session().attribute("contador_intentos_sesion_fallidos", 0);
      }

      new ValidadorAutenticacionNuevo(
          request.session().attribute("ultimo_intento_sesion_fallido"),
          request.session().attribute("contador_intentos_sesion_fallidos")
      ).autenticarUsuario(usuario, request.queryParams("password"));

      request.session().attribute("user_id", usuario.getId());
      request.session().attribute("is_admin", usuario.esAdmin());
      request.session().attribute("user_name", usuario.getUsuario());
      request.session().attribute("contador_intentos_sesion_fallidos", "0");
      redireccionCasoFeliz(request, response, "/", null);
      return null;
    } catch (NoSuchElementException | AutenticacionInvalidaException e) {
      // entra aca si se ingreso mal el usuario o si se ingreso mal la contraseña respectivamente
      setearAtributosAnteError(request, response, e);
      return null;
    } catch (AutenticacionConsecutivaException e) { // entra aca si se ingreso mal la contraseña hace poco
      redireccionCasoError(request, response, "/login", e.getMessage());
      return null;
    }
  }

  public Void cerrarSesion(Request request, Response response) {
    request.session().attribute("user_id", null);
    request.session().attribute("is_admin", null);
    request.session().attribute("user_name", null);
    request.session().attribute("ultimo_intento_sesion_fallido", null);
    request.session().attribute("contador_intentos_sesion_fallidos", null);
    response.redirect("/");
    return null;
  }

  private void setearAtributosAnteError(Request request, Response response, Exception e) {
    request.session().attribute("ultimo_intento_sesion_fallido", LocalTime.now());
    int contador = request.session().attribute("contador_intentos_sesion_fallidos");
    request.session().attribute(
        "contador_intentos_sesion_fallidos",
        contador + 1
    );
    redireccionCasoError(request, response, "/login", e.getMessage());
  }

}
