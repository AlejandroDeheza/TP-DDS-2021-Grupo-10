package controllers;

import excepciones.AutenticacionConsecutivaException;
import excepciones.AutenticacionInvalidaException;
import excepciones.UserNameException;
import modelo.usuario.Usuario;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.ValidadorAutenticacionNuevo;
import java.time.LocalTime;
import java.util.Map;
import java.util.NoSuchElementException;

public class SesionController extends Controller {

  RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();

  public ModelAndView mostrarLogin(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    modelo.put("rutaLogin", super.getRutaConOrigin(request, "/login"));
    return new ModelAndView(modelo, "login.html.hbs");
  }

  public Void crearSesion(Request request, Response response) {
    try {
      Usuario usuario = repositorioUsuarios.buscarPorUserName(request.queryParams("username"));

      if (request.session().attribute("ultimo_intento_sesion_fallido") == null ||
          request.session().attribute("contador_intentos_sesion_fallidos") == null) {
        request.session().attribute("ultimo_intento_sesion_fallido", LocalTime.now());
        request.session().attribute("contador_intentos_sesion_fallidos", 0);
      }

      new ValidadorAutenticacionNuevo(
          request.session().attribute("ultimo_intento_sesion_fallido"),
          request.session().attribute("contador_intentos_sesion_fallidos")
      ).autenticarUsuario(usuario, request.queryParams("password"));

      request.session().attribute("contador_intentos_sesion_fallidos", 0);
      super.iniciarSesion(request, usuario);
      redireccionCasoFeliz(request, response, null);
      return null;
    } catch (NoSuchElementException | AutenticacionInvalidaException e) {
      // entra aca si se ingreso mal el usuario o si se ingreso mal la contraseña respectivamente
      setearAtributosAnteError(request, response, e);
      return null;
    } catch (AutenticacionConsecutivaException | UserNameException e) {
      // entra aca si se ingreso mal la contraseña hace poco o si se ingreso mal el nombre de usuario respectivamente
      redireccionCasoError(request, response, "/login", e.getMessage());
      return null;
    }
  }

  public Void cerrarSesion(Request request, Response response) {
    request.session().removeAttribute("user_id");
    request.session().removeAttribute("is_admin");
    request.session().removeAttribute("user_name");
    request.session().removeAttribute("ultimo_intento_sesion_fallido");
    request.session().removeAttribute("contador_intentos_sesion_fallidos");
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
