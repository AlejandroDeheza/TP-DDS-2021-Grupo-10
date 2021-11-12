package controllers;

import modelo.hogarDeTransito.ReceptorHogares;
import modelo.informe.InformeConQR;
import modelo.informe.InformeSinQR;
import modelo.informe.Ubicacion;
import modelo.mascota.Animal;
import modelo.mascota.Foto;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.MascotaRegistrada;
import modelo.mascota.TamanioMascota;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.persona.DatosDeContacto;
import modelo.persona.DocumentoIdentidad;
import modelo.persona.Persona;
import modelo.persona.TipoDocumento;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
    String id = request.queryParams("codigo-chapita");
    MascotaRegistrada mascotaRegistrada =
        id != null ?
            repositorioMascotaRegistrada.getPorId(Long.parseLong(id)) :
            null;
    modelo.put("mascotaRegistrada", mascotaRegistrada);
    modelo.put("codigo-chapita", id);
    return new ModelAndView(modelo, "encontre-mascota-tipo-encuentro-con-chapita.html.hbs");
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
    List<CaracteristicaConValoresPosibles> listaCaracteristicasConValoresPosibles =
        repositorioCaracteristicas.getCaracteristicasConValoresPosibles();

    modelo.put("tipoAnimales", animal);
    modelo.put("tamanioMascota", tamanioMascotas);
    modelo.put("listaCaracteristicasValoresPosibles", listaCaracteristicasConValoresPosibles);

    return new ModelAndView(modelo, "encontre-mascota-tipo-encuentro-sin-chapita.html.hbs");
  }

  Map<String, Object> getValoresUbicacion(Request request, Response response) {
    String ubicacionRescatistaString = request.queryParams("ubicacion-rescatista");
    String ubicacionRescateString = request.queryParams("ubicacion-rescate");
    LocalDate fechaRescate = LocalDate.parse(request.queryParams("fecha-rescate"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String estadoMascota = request.queryParams("estado-mascota");

    String[] fotosString = request.queryParamsValues("img");
    List<Foto> fotos = new ArrayList<>();
    Arrays.stream(fotosString).forEach((foto) -> fotos.add(new Foto(foto,
        null)));
    Ubicacion ubicacionRescatista = new Ubicacion(1122.1, 122.1, ubicacionRescatistaString);
    //TODO ver la latitud y longitud
    //TODO: ver como guardar las imagenes
    Ubicacion ubicacionRescate = new Ubicacion(1122.1, 122.1, ubicacionRescateString);
    Map<String, Object> modelo = getMap(request);

    modelo.put("ubicacionRescatista", ubicacionRescatista);
    modelo.put("ubicacionRescate", ubicacionRescate);
    modelo.put("fechaRescate", fechaRescate);
    modelo.put("estadoMascota", estadoMascota);
    modelo.put("fotos", fotos);
    return modelo;
  }

  public Void enviarMascotaEncontradaConChapita(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/login");
      return null;
    }
    try {
      Map<String, Object> modelo = getValoresUbicacion(request, response);

      Ubicacion ubicacionRescatista = (Ubicacion) modelo.get("ubicacionRescatista");
      Ubicacion ubicacionRescate = (Ubicacion) modelo.get("ubicacionRescate");
      LocalDate fechaRescate = (LocalDate) modelo.get("fechaRescate");
      String estadoMascota = (String) modelo.get("estadoMascota");
      List<Foto> fotos = (List<Foto>) modelo.get("fotos");

      Long Id = request.session().attribute("user_id");
      Usuario usuario = new RepositorioUsuarios().getPorId(Id);
      Persona persona = usuario.getPersona();

      RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();
      String idChapitaString = request.queryParams("codigo-chapita");
      Long idChapita = Long.parseLong(idChapitaString);
      MascotaRegistrada mascotaRegistrada = repositorioMascotaRegistrada.getPorId(idChapita);

      RepositorioInformes repositorioInformes = new RepositorioInformes();
      MascotaEncontrada mascotaEncontrada = new MascotaEncontrada(fotos, ubicacionRescate
          , estadoMascota, fechaRescate,
          mascotaRegistrada.getTamanio());
      ReceptorHogares receptorHogares = new ReceptorHogares();
      InformeConQR informeConQR = new InformeConQR(persona, ubicacionRescatista,
          mascotaEncontrada, receptorHogares, mascotaRegistrada);

      withTransaction(() -> {
        repositorioInformes.agregarInforme(informeConQR);
      });
      redireccionCasoFeliz(request, response, "/", "La cuenta se ha registrado con exito!");
      return null;
    } catch (Exception e) {
      redireccionCasoError(request, response, "/", "Fallo la registracion");
      return null;
    }
  }


  public Void enviarMascotaEncontradaSinChapita(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    try {
      Map<String, Object> modelo = getMap(request);

      String colorString = request.queryParams("color");


      Ubicacion ubicacionRescatista = (Ubicacion) modelo.get("ubicacionRescatista");
      Ubicacion ubicacionRescate = (Ubicacion) modelo.get("ubicacionRescate");
      LocalDate fechaRescate = (LocalDate) modelo.get("fechaRescate");
      String estadoMascota = (String) modelo.get("estadoMascota");
      List<Foto> fotos = (List<Foto>) modelo.get("fotos");
      Long Id = request.session().attribute("user_id");
      Usuario usuario = new RepositorioUsuarios().getPorId(Id);
      Persona persona = usuario.getPersona();

      TamanioMascota tamanioMascota =
          TamanioMascota.values()[Integer.parseInt(request.queryParams("tamanioMascota"))];
      Animal animal = Animal.values()[Integer.parseInt(request.queryParams("tipoAnimal"))];
      List<Caracteristica> caracteristicas = new ArrayList<>();
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
      redireccionCasoFeliz(request, response, "/", "La cuenta se ha registrado con exito!");
      return null;
    } catch (
        Exception e) {
      redireccionCasoError(request, response, "/", "Fallo la registracion");
      return null;
    }
  }
}
