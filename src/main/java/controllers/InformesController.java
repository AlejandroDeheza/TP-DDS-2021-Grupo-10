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

public class InformesController extends Controller {

  RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();
  RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
  RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
  RepositorioInformes repositorioInformes = new RepositorioInformes();

  public ModelAndView mostrarMenuTipoEncuentro(Request request, Response response) {
    return new ModelAndView(getMap(request), "encontre-mascota-tipo-encuentro.html.hbs");
  }

  public ModelAndView mostrarFormularioConChapita(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    String idChapitaString = request.params(":codigoChapita");
    MascotaRegistrada mascotaRegistrada = repositorioMascotaRegistrada.buscarPorId(Long.parseLong(idChapitaString));
    if (mascotaRegistrada == null) {
      redireccionCasoError(request, response, "/informes/menu", "El codigo de chapita no es valido");
      return null;
    }
    modelo.put("codigoChapita", idChapitaString);
    modelo.put("mascotaRegistrada", mascotaRegistrada);
    return new ModelAndView(modelo, "encuentro-con-chapita.html.hbs");
  }

  public ModelAndView mostrarFormularioSinChapita(Request request, Response response) {
    Map<String, Object> modelo = getMap(request);
    modelo.put("caracteristicas", repositorioCaracteristicas.getCaracteristicasConValoresPosibles());
    modelo.put("tipoAnimales", EnumSet.allOf(Animal.class));
    modelo.put("tamanioMascota", EnumSet.allOf(TamanioMascota.class));
    return new ModelAndView(modelo, "encuentro-sin-chapita.html.hbs");
  }

  public Void generarInformeConQR(Request request, Response response) {

    Long idChapita = Long.parseLong(request.params(":codigoChapita"));
    MascotaRegistrada mascotaRegistrada = repositorioMascotaRegistrada.buscarPorId(idChapita);

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

    redireccionCasoFeliz(request, response, "El informe se genero con exito!");
    return null;
  }

  public Void generarInformeSinQR(Request request, Response response) {

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

    redireccionCasoFeliz(request, response, "El informe se genero con exito!");
    return null;
  }

  public ModelAndView mostrarInstruccionesParaEscanearQR(Request request, Response response) {
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
