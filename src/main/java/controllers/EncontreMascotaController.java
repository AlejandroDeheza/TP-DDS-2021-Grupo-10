package controllers;

import modelo.hogarDeTransito.ReceptorHogares;
import modelo.informe.InformeConQR;
import modelo.informe.Ubicacion;
import modelo.mascota.Animal;
import modelo.mascota.Foto;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.MascotaRegistrada;
import modelo.mascota.TamanioMascota;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.notificacion.TipoNotificadorPreferido;
import modelo.persona.DatosDeContacto;
import modelo.persona.DocumentoIdentidad;
import modelo.persona.Persona;
import modelo.persona.TipoDocumento;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioInformes;
import repositorios.RepositorioMascotaRegistrada;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class EncontreMascotaController extends Controller implements WithGlobalEntityManager,
    TransactionalOps {

  public ModelAndView getFormularioDatosNoUsuario(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    if (tieneSesionActiva(request)) {
      response.redirect("/mascotas/encontre-mascota/lugar-encuentro");
      return null;
    }

    EnumSet<TipoNotificadorPreferido> tipoNotificadorPreferidos = EnumSet.allOf(TipoNotificadorPreferido.class);

    EnumSet<TipoDocumento> tiposDocumentos = EnumSet.allOf(TipoDocumento.class);

    modelo.put("medioContactos", tipoNotificadorPreferidos);
    modelo.put("tiposDocumentos", tiposDocumentos);
    return new ModelAndView(modelo, "formulario-encontre-mascota-datos-personales.html.hbs");
  }


  public Void completarLugarEncuentro(Request request, Response response) {
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    response.redirect("/mascotas/encontre-mascota/lugar-encuentro");
    return null;
  }

  public ModelAndView getFormularioLugarEncuentro(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    return new ModelAndView(modelo, "formulario-encontre-mascota-datos-ubicacion.html.hbs");
  }

  public Void elegirLugarEncuentro(Request request, Response response) {
//    if (!tieneSesionActiva(request) || getMap(request).get("persona") == null) {
//      response.redirect("/mascotas/encontre-mascota");
//      return null;
//    }
    String ubicacionRescatistaString = request.queryParams("ubicacion-rescatista");
    String ubicacionRescateString = request.queryParams("ubicacion-rescate");
    LocalDate fechaRescate = LocalDate.parse(request.queryParams("fecha-rescate"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String estadoMascota = request.queryParams("estado-mascota");
    String fotosString = request.queryParams("img");

    List<Foto> fotos = new ArrayList<>();
    Ubicacion ubicacionRescatista = new Ubicacion(1122.1, 122.1, ubicacionRescatistaString);
    Ubicacion ubicacionRescate = new Ubicacion(1122.1, 122.1, ubicacionRescateString);
    Map<String, Object> modelo = getMap(request);

    modelo.put("ubicacionRescatista", ubicacionRescatista);
    modelo.put("ubicacionRescate", ubicacionRescate);
    modelo.put("fechaRescate", fechaRescate);
    modelo.put("estadoMascota", estadoMascota);
    modelo.put("fotos", fotos);


    response.redirect("/mascotas/encontre-mascota/lugar-encuentro/tipo-encuentro");
    return null;
  }

  public ModelAndView getTiposEncuentros(Request request, Response response) {
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    return new ModelAndView(getMap(request), "encontre-mascota-tipo-encuentro.html.hbs");
  }


  public ModelAndView getFormularioConChapita(Request request, Response response) {
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
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
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
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


  public Void enviarMascotaEncontradaConChapita(Request request, Response response) {
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    Map<String, Object> modelo = getMap(request);

    withTransaction(() -> {
      RepositorioInformes repositorioInformes = new RepositorioInformes();
      MascotaRegistrada mascotaRegistrada = (MascotaRegistrada) modelo.get("mascotaRegistrada");
      LocalDate fechaEncuentro = (LocalDate) modelo.get("fechaEncuentro");
      Ubicacion ubicacionRescate = (Ubicacion) modelo.get("ubicacionRescate");
      MascotaEncontrada mascotaEncontrada = new MascotaEncontrada(mascotaRegistrada.getFotos(), ubicacionRescate
          , (String) modelo.get("estadoMascota"), fechaEncuentro,
          mascotaRegistrada.getTamanio());
      ReceptorHogares receptorHogares = new ReceptorHogares();
      InformeConQR informeConQR = new InformeConQR((Persona) modelo.get("persona"), (Ubicacion) modelo.get(
          "ubicacionRescatista"), mascotaEncontrada, receptorHogares, mascotaRegistrada);

      repositorioInformes.agregarInforme(informeConQR);

    });
    redireccionCasoFeliz(request, response, "/", "La cuenta se ha registrado con exito!");
    return null;
  }


  public Void enviarMascotaEncontradaSinChapita(Request request, Response response) {
    if (request.session().attribute("user_id") != null) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    Map<String, Object> modelo = getMap(request);

    withTransaction(() -> {
      RepositorioInformes repositorioInformes = new RepositorioInformes();
      MascotaRegistrada mascotaRegistrada = (MascotaRegistrada) modelo.get("mascotaRegistrada");
      LocalDate fechaEncuentro = (LocalDate) modelo.get("fechaEncuentro");
      Ubicacion ubicacionRescate = (Ubicacion) modelo.get("ubicacionRescate");
      MascotaEncontrada mascotaEncontrada = new MascotaEncontrada(mascotaRegistrada.getFotos(), ubicacionRescate
          , (String) modelo.get("estadoMascota"), fechaEncuentro,
          mascotaRegistrada.getTamanio());
      ReceptorHogares receptorHogares = new ReceptorHogares();
      InformeConQR informeConQR = new InformeConQR((Persona) modelo.get("persona"), (Ubicacion) modelo.get(
          "ubicacionRescatista"), mascotaEncontrada, receptorHogares, mascotaRegistrada);

      repositorioInformes.agregarInforme(informeConQR);

    });
    redireccionCasoFeliz(request, response, "/", "La cuenta se ha registrado con exito!");
    return null;
  }

  public Void enviarDatosNoUsuario(Request request, Response response) {
    String nombre = request.queryParams("nombre");
    String apellido = request.queryParams("apellido");
    String email = request.queryParams("email");
    String telefono = request.queryParams("telefono");
    TipoDocumento tipoDocumento = TipoDocumento.values()[Integer.parseInt(request.queryParams("tipoDocumento"))];
    String numeroDocumento = request.queryParams("numero-documento");
    LocalDate fechaNacimiento = LocalDate.parse(request.queryParams("fecha-nacimiento"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    TipoNotificadorPreferido tipoNotificadorPreferido =
        TipoNotificadorPreferido.values()[Integer.parseInt(request.queryParams(
            "tipoNotificadorPreferido"))];

    DatosDeContacto datosDeContacto = new DatosDeContacto(telefono, email);
    DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad(tipoDocumento, numeroDocumento);
    Persona persona = new Persona(
        nombre,
        apellido,
        documentoIdentidad,
        datosDeContacto,
        fechaNacimiento,
        tipoNotificadorPreferido
    );
    Map<String, Object> modelo = getMap(request);
    modelo.put("persona", persona);
    response.redirect("/mascotas/encontre-mascota/lugar-encuentro");
    return null;
  }
}
