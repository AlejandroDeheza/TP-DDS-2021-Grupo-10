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
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioInformes;
import repositorios.RepositorioMascotaRegistrada;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class EncontreMascotaController extends Controller implements WithGlobalEntityManager,
    TransactionalOps {


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
    RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();
    String idChapitaString = request.params(":codigoChapita");
    Long idChapita = Long.parseLong(idChapitaString);
    MascotaRegistrada mascotaRegistrada = repositorioMascotaRegistrada.getPorId(idChapita);
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

    EnumSet<Animal> animal = EnumSet.allOf(Animal.class);
    EnumSet<TamanioMascota> tamanioMascotas = EnumSet.allOf(TamanioMascota.class);

    RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();

    List<CaracteristicaConValoresPosibles> listaCaracteristicas = repositorioCaracteristicas.getCaracteristicasConValoresPosibles();
    modelo.put("caracteristicas", listaCaracteristicas);

    modelo.put("tipoAnimales", animal);
    modelo.put("tamanioMascota", tamanioMascotas);

    return new ModelAndView(modelo, "encuentro-sin-chapita.html.hbs");
  }


  public Void enviarMascotaEncontradaConChapita(Request request, Response response) throws IOException {
    if (!tieneSesionActiva(request)) {
      response.redirect("/login");
      return null;
    }
    try {
      List<Foto> fotos = super.obtenerFotosMascota(request, response);
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
      Usuario usuario = new RepositorioUsuarios().getPorId(Id);
      Persona persona = usuario.getPersona();


      RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();
      String idChapitaString = request.params(":codigoChapita");
      Long idChapita = Long.parseLong(idChapitaString);
      MascotaRegistrada mascotaRegistrada = repositorioMascotaRegistrada.getPorId(idChapita);
      if (mascotaRegistrada == null) {
        redireccionCasoError(request, response, "/mascotas/encontre-mascota/con-chapita", "El codigo de chapita no es valido");
        return null;
      }
      TamanioMascota tamanioMascota = mascotaRegistrada.getTamanio();
      RepositorioInformes repositorioInformes = new RepositorioInformes();
      MascotaEncontrada mascotaEncontrada = new MascotaEncontrada(fotos, ubicacionRescate
          , estadoMascota, fechaRescate,
          tamanioMascota);
      ReceptorHogares receptorHogares = new ReceptorHogares();
      InformeConQR informeConQR = new InformeConQR(persona, ubicacionRescatista,
          mascotaEncontrada, receptorHogares, mascotaRegistrada);

      withTransaction(() -> {
        repositorioInformes.agregarInforme(informeConQR);
      });
    } catch (Exception e) {
      System.out.println(e);
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

      Usuario usuario = new RepositorioUsuarios().getPorId(Id);
      Persona persona = usuario.getPersona();

      TamanioMascota tamanioMascota =
          TamanioMascota.values()[Integer.parseInt(request.queryParams("tamanioMascota"))];
      Animal animal = Animal.values()[Integer.parseInt(request.queryParams("tipoAnimal"))];
      RepositorioInformes repositorioInformes = new RepositorioInformes();
      MascotaEncontrada mascotaEncontrada = new MascotaEncontrada(fotos, ubicacionRescate
          , estadoMascota, fechaRescate,
          tamanioMascota);
      ReceptorHogares receptorHogares = new ReceptorHogares();
      InformeSinQR informeSinQR = new InformeSinQR(persona,
          ubicacionRescatista, mascotaEncontrada, receptorHogares, animal, caracteristicas);
      withTransaction(() -> {
        repositorioInformes.agregarInforme(informeSinQR);

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
