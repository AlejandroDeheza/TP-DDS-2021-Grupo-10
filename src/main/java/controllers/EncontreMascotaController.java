package controllers;

import modelo.hogarDeTransito.ReceptorHogares;
import modelo.informe.InformeConQR;
import modelo.informe.InformeSinQR;
import modelo.informe.Ubicacion;
import modelo.mascota.*;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
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
import java.util.List;
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

      Ubicacion ubicacionRescatista = new Ubicacion(
          Double.parseDouble(request.queryParams("latitudRescatista")),
          Double.parseDouble(request.queryParams("longitudRescatista")),
          request.queryParams("ubicacionRescatista")
      );
      Ubicacion ubicacionRescate = new Ubicacion(
          Double.parseDouble(request.queryParams("latitudRescate")),
          Double.parseDouble(request.queryParams("longitudRescate")),
          request.queryParams("ubicacionRescate")
      );
      String estadoMascota = request.queryParams("estadoMascota");

      Long Id = request.session().attribute("user_id");
      Usuario usuario = repositorioUsuarios.buscarPorId(Id);
      Persona rescatista = usuario.getPersona();


      String idChapitaString = request.params(":codigoChapita");
      Long idChapita = Long.parseLong(idChapitaString);
      MascotaRegistrada mascotaRegistrada = repositorioMascotaRegistrada.buscarPorId(idChapita);
      if (mascotaRegistrada == null) {
        redireccionCasoError(request, response, "/mascotas/encontre-mascota/con-chapita", "El codigo de chapita no es valido");
        return null;
      }
      TamanioMascota tamanioMascota = mascotaRegistrada.getTamanio();

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

      MascotaEncontrada mascotaEncontrada = new MascotaEncontrada(
          super.obtenerFotosMascota(request, response),
          ubicacionRescate,
          estadoMascota,
          LocalDate.parse(request.queryParams("fechaRescate"), formatter),
          tamanioMascota);
      ReceptorHogares receptorHogares = new ReceptorHogares();
      InformeConQR informeConQR = new InformeConQR(rescatista, ubicacionRescatista,
          mascotaEncontrada, receptorHogares, mascotaRegistrada);

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
      List<Foto> fotos = super.obtenerFotosMascota(request, response);


      List<Caracteristica> caracteristicas;
      //Obtengo sus caracteristicas
      caracteristicas = super.obtenerListaCaracteristicas(request);


      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      LocalDate fechaRescate = LocalDate.parse(request.queryParams("fechaRescate"), formatter);

      String ubicacionRescatistaString = request.queryParams("ubicacionRescatista");
      String latitudRescatistaString = request.queryParams("latitudRescatista");
      String longitudRescatistaString = request.queryParams("longitudRescatista");
      String latitudRescateString = request.queryParams("latitudRescate");
      String longitudRescateString = request.queryParams("longitudRescate");
      String ubicacionRescateString = request.queryParams("ubicacionRescate");

      Ubicacion ubicacionRescatista = new Ubicacion(Double.parseDouble(latitudRescatistaString),
          Double.parseDouble(longitudRescatistaString),
          ubicacionRescatistaString);
      Ubicacion ubicacionRescate = new Ubicacion(Double.parseDouble(latitudRescateString),
          Double.parseDouble(longitudRescateString), ubicacionRescateString);

      String estadoMascota = request.queryParams("estadoMascota");

      Long Id = request.session().attribute("user_id");

      Usuario usuario = repositorioUsuarios.buscarPorId(Id);
      Persona rescatista = usuario.getPersona();

      TamanioMascota tamanioMascota =
          TamanioMascota.values()[Integer.parseInt(request.queryParams("tamanioMascota"))];
      Animal animal = Animal.values()[Integer.parseInt(request.queryParams("tipoAnimal"))];
      MascotaEncontrada mascotaEncontrada = new MascotaEncontrada(fotos, ubicacionRescate
          , estadoMascota, fechaRescate,
          tamanioMascota);
      ReceptorHogares receptorHogares = new ReceptorHogares();
      InformeSinQR informeSinQR = new InformeSinQR(rescatista,
          ubicacionRescatista, mascotaEncontrada, receptorHogares, animal, caracteristicas);
      withTransaction(() -> {
        repositorioInformes.agregar(informeSinQR);

      });
    } catch (
        Exception e) {
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

}
