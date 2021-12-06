package controllers;

import excepciones.ContraseniaInvalidaException;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.persona.DatosDeContacto;
import modelo.persona.DocumentoIdentidad;
import modelo.persona.Persona;
import modelo.persona.TipoDocumento;
import modelo.usuario.TipoUsuario;
import modelo.usuario.Usuario;
import modelo.usuario.ValidadorContrasenias;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class UsuarioController extends Controller {

  RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();

  public ModelAndView mostrarFormularioCreacionUsuario(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    modelo.put("rutaUsuarios", super.getRutaConOrigin(request, "/usuarios"));
    return new ModelAndView(modelo, "registracion.html.hbs");
  }

  public Void registrarUsuario(Request request, Response response) {

    String contrasenia = request.queryParams("contrasenia");
    String validacionContrasenia = request.queryParams("validacionContrasenia");

    try {
      new ValidadorContrasenias().correrValidaciones(request.queryParams("contrasenia"));
    } catch (ContraseniaInvalidaException e) {
      redireccionCasoError(request, response, "/error", e.getMessage());
      return null;
    }

    if(!contrasenia.equals(validacionContrasenia)) {
      redireccionCasoError(request, response, "/creacion-usuario", "Las contrasenias no matchean entre si");
      return null;
    }

    if (repositorioUsuarios.yaExiste(request.queryParams("usuario"))) {
      redireccionCasoError(request, response, "/creacion-usuario", "Ya existe una cuenta con el nombre de usuario ingresado");
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
          LocalDate.parse(request.queryParams("fechaNacimiento"), DateTimeFormatter.ofPattern("MM/dd/yyyy")),
          TipoNotificadorPreferido.values()[Integer.parseInt(request.queryParams("tipoNotificadorPreferido"))]
      );

      Usuario nuevo = new Usuario(
          request.queryParams("usuario"),
          request.queryParams("contrasenia"),
          TipoUsuario.NORMAL,
          persona
      );

      withTransaction(() -> {
        repositorioUsuarios.agregar(nuevo);
      });

      super.iniciarSesion(request, nuevo);
      redireccionCasoFeliz(request, response, "/", "La cuenta se ha registrado con exito!");
    }
    return null;
  }
}
