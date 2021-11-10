package controllers;

import excepciones.AutenticacionConsecutivaException;
import excepciones.AutenticacionInvalidaException;
import excepciones.ContraseniaInvalidaException;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.persona.DatosDeContacto;
import modelo.persona.DocumentoIdentidad;
import modelo.persona.Persona;
import modelo.persona.TipoDocumento;
import modelo.usuario.TipoUsuario;
import modelo.usuario.Usuario;
import modelo.usuario.ValidadorContrasenias;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import utils.ValidadorAutenticacionNuevo;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;

public class SesionController extends Controller implements WithGlobalEntityManager, TransactionalOps {

  public ModelAndView mostrarLogin(Request request, Response response) {
    if (seInicioSesion(request)) {
      response.redirect("/");
      return null;
    }
    return new ModelAndView(getMap(request), "formulario-login.html.hbs");
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
      request.session().attribute("contador_intentos_sesion_fallidos", "0");
      redireccionCasoFeliz(request, response, "/", null);
      return null;
    } catch (NoSuchElementException e) { // entra aca si se ingreso mal el usuario
      setearAtributosAnteError(request, response, e);
      return null;
    } catch (AutenticacionInvalidaException e) { // entra aca si se ingreso mal la contraseña
      setearAtributosAnteError(request, response, e);
      return null;
    } catch (AutenticacionConsecutivaException e) { // entra aca si se ingreso mal la contraseña hace poco
      redireccionCasoError(request, response, "/login", e.getMessage());
      return null;
    }
  }

  public ModelAndView mostrarRegistracion(Request request, Response response) {
    return new ModelAndView(getMap(request), "registracion.html.hbs");
  }

  public Void registrar(Request request, Response response) {
    try {
      new ValidadorContrasenias().correrValidaciones(request.queryParams("contrasenia"));
    } catch (ContraseniaInvalidaException e) {
      redireccionCasoError(request, response, "/registracion", e.getMessage());
      return null;
    }

    if (new RepositorioUsuarios().yaExiste(request.queryParams("usuario"))) {
      redireccionCasoError(request, response,"/registracion", "Ya existe una cuenta con el nombre de usuario ingresado");
    } else {
      DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad(
          TipoDocumento.values()[Integer.parseInt(request.queryParams("tipoDocumento"))],
          request.queryParams("numeroDocumento")
      );

      DatosDeContacto datosDeContacto = new DatosDeContacto(
          request.queryParams("telefono"),
          request.queryParams("email")
      );

      Persona persona = new Persona(
          request.queryParams("nombre"),
          request.queryParams("apellido"),
          documentoIdentidad,
          datosDeContacto,
          LocalDate.parse(request.queryParams("fechaNacimiento")),
          TipoNotificadorPreferido.values()[Integer.parseInt(request.queryParams("tipoNotificadorPreferido"))]
      );

      Usuario nuevo = new Usuario(
          request.queryParams("usuario"),
          request.queryParams("contrasenia"),
          TipoUsuario.NORMAL,
          persona
      );

      withTransaction(() -> {
        new RepositorioUsuarios().agregar(nuevo);
      });

      request.session().attribute("user_id", nuevo.getId());
      redireccionCasoFeliz(request, response, "/", "La cuenta se ha registrado con exito!");
    }
    return null;
  }

  public Void cerrarSesion(Request request, Response response) {
    request.session().attribute("user_id", null);
    request.session().attribute("is_admin", null);
    request.session().attribute("ultimo_intento_sesion_fallido", null);
    request.session().attribute("contador_intentos_sesion_fallidos", null);
    response.redirect("/");
    return null;
  }

  private void setearAtributosAnteError(Request request, Response response, RuntimeException e) {
    request.session().attribute("ultimo_intento_sesion_fallido", LocalTime.now());
    int contador = request.session().attribute("contador_intentos_sesion_fallidos");
    request.session().attribute(
        "contador_intentos_sesion_fallidos",
        contador + 1
    );
    redireccionCasoError(request, response, "/login", e.getMessage());
  }

}
