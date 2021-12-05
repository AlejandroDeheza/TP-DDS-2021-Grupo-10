package controllers;

import modelo.hogarDeTransito.ReceptorHogares;
import modelo.informe.InformeConQR;
import modelo.informe.InformeSinQR;
import modelo.informe.Ubicacion;
import modelo.mascota.Animal;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.MascotaRegistrada;
import modelo.mascota.TamanioMascota;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioInformes;
import repositorios.RepositorioMascotaRegistrada;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.Map;

public class EncontreMascotaController extends Controller {

  RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();
  RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
  RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
  RepositorioInformes repositorioInformes = new RepositorioInformes();

  public ModelAndView getTiposEncuentros(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/login");
      return null;
    }
    return new ModelAndView(getMap(request), "encontre-mascota-tipo-encuentro.html.hbs");
  }

  public ModelAndView getFormularioConChapita(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/login");
      return null;
    }
    Map<String, Object> modelo = getMap(request);
    String idChapitaString = request.params(":codigoChapita");
    MascotaRegistrada mascotaRegistrada = repositorioMascotaRegistrada.buscarPorId(Long.parseLong(idChapitaString));
    if (mascotaRegistrada == null) {
      redireccionCasoError(request, response, "/mascotas/encontre-mascota/con-chapita", "El codigo de chapita no es valido");
      return null;
    }
    modelo.put("codigoChapita", idChapitaString);
    modelo.put("mascotaRegistrada", mascotaRegistrada);
    return new ModelAndView(modelo, "encuentro-con-chapita.html.hbs");
  }

  public ModelAndView getFormularioSinChapita(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/login");
      return null;
    }
    Map<String, Object> modelo = getMap(request);
    modelo.put("caracteristicas", repositorioCaracteristicas.getCaracteristicasConValoresPosibles());
    modelo.put("tipoAnimales", EnumSet.allOf(Animal.class));
    modelo.put("tamanioMascota", EnumSet.allOf(TamanioMascota.class));
    return new ModelAndView(modelo, "encuentro-sin-chapita.html.hbs");
  }

  public Void enviarMascotaEncontradaConChapita(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/login");
      return null;
    }
    try {

      Long idChapita = Long.parseLong(request.params(":codigoChapita"));
      MascotaRegistrada mascotaRegistrada = repositorioMascotaRegistrada.buscarPorId(idChapita);
      if (mascotaRegistrada == null) {
        redireccionCasoError(request, response, "/mascotas/encontre-mascota/con-chapita", "El codigo de chapita no es valido");
        return null;
      }

      InformeConQR informeConQR = new InformeConQR(
          repositorioUsuarios.buscarPorId(request.session().attribute("user_id")).getPersona(),
          obtenerUbicacionRescatista(request),
          obtenerMascotaEncontrada(request, response, mascotaRegistrada.getTamanio()),
          new ReceptorHogares(),
          mascotaRegistrada
      );

      withTransaction(() -> {
        repositorioInformes.agregar(informeConQR);
      });

    } catch (Exception e) {
      redireccionCasoError(request, response, "/error", "Fallo la generacion del informe");
    }
    redireccionCasoFeliz(request, response, "/", "Se genero el informe!");
    return null;
  }
  
  public Void enviarMascotaEncontradaSinChapita(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    try {

      TamanioMascota tamanioMascota = TamanioMascota.values()[Integer.parseInt(request.queryParams("tamanioMascota"))];

      InformeSinQR informeSinQR = new InformeSinQR(
          repositorioUsuarios.buscarPorId(request.session().attribute("user_id")).getPersona(),
          obtenerUbicacionRescatista(request),
          obtenerMascotaEncontrada(request, response, tamanioMascota),
          new ReceptorHogares(),
          Animal.values()[Integer.parseInt(request.queryParams("tipoAnimal"))],
          super.obtenerListaCaracteristicas(request)
      );

      withTransaction(() -> {
        repositorioInformes.agregar(informeSinQR);
      });

    } catch (Exception e) {
      redireccionCasoError(request, response, "/error", "Fallo la generacion del informe");
    }
    redireccionCasoFeliz(request, response, "/", "Se genero el informe!");
    return null;
  }

  public ModelAndView getInformacionEscaneo(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/login");
      return null;
    }
    return new ModelAndView(getMap(request), "escaneeQR.html.hbs");
  }

  private MascotaEncontrada obtenerMascotaEncontrada(Request request, Response response, TamanioMascota tamanio) {

    Ubicacion ubicacionRescate = new Ubicacion(
        Double.parseDouble(request.queryParams("latitudRescate")),
        Double.parseDouble(request.queryParams("longitudRescate")),
        request.queryParams("ubicacionRescate")
    );
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    return new MascotaEncontrada(
        super.obtenerFotosMascota(request, response),
        ubicacionRescate,
        request.queryParams("estadoMascota"),
        LocalDate.parse(request.queryParams("fechaRescate"), formatter),
        tamanio
    );
  }

  private Ubicacion obtenerUbicacionRescatista(Request request) {
    return new Ubicacion(
        Double.parseDouble(request.queryParams("latitudRescatista")),
        Double.parseDouble(request.queryParams("longitudRescatista")),
        request.queryParams("ubicacionRescatista")
    );
  }

}
