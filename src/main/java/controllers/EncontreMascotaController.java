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
import utils.Constantes;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;

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

    List<CaracteristicaConValoresPosibles> listaCaracteristicas = repositorioCaracteristicas.getCaracteristicasConValoresPosibles();
    modelo.put("caracteristicas", listaCaracteristicas);

    modelo.put("tipoAnimales", animal);
    modelo.put("tamanioMascota", tamanioMascotas);

    return new ModelAndView(modelo, "encontre-mascota-tipo-encuentro-sin-chapita.html.hbs");
  }


  public Void enviarMascotaEncontradaConChapita(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/login");
      return null;
    }
    try {


      List<Foto> fotos = new ArrayList<>();

      // Cargo la foto de la mascota
      File uploadDir = new File(Constantes.UPLOAD_DIRECTORY);
      Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".jpg");

      request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
      try (InputStream fotoInputStream = request.raw().getPart("fotos").getInputStream()) {
        Files.copy(fotoInputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException | ServletException exception) {
        System.out.println(exception);
        redireccionCasoError(request, response, "/", "Hubo un error al cargar la foto de tu mascota, intenta con otra foto");
      }


      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      LocalDate fechaRescate = LocalDate.parse(request.queryParams("fechaRescate"), formatter);

      String ubicacionRescatistaString = request.queryParams("ubicacionRescatista");
      String ubicacionRescateString = request.queryParams("ubicacionRescate");
      Ubicacion ubicacionRescatista = new Ubicacion(1122.1, 122.1, ubicacionRescatistaString);
      Ubicacion ubicacionRescate = new Ubicacion(1122.1, 122.1, ubicacionRescateString);


      String estadoMascota = (String) request.queryParams("estadoMascota");

      Long Id = request.session().attribute("user_id");
      Usuario usuario = new RepositorioUsuarios().getPorId(Id);
      Persona persona = usuario.getPersona();


      RepositorioMascotaRegistrada repositorioMascotaRegistrada = new RepositorioMascotaRegistrada();
      String idChapitaString = request.queryParams("codigo-chapita");
      Long idChapita = Long.parseLong(idChapitaString);
      MascotaRegistrada mascotaRegistrada = repositorioMascotaRegistrada.getPorId(idChapita);
      if (mascotaRegistrada == null) {
        redireccionCasoError(request, response, "/mascotas/encontre-mascota/con-chapita", "El " +
            "codigo de chapita no es valido");
        return null;
      }
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
      redireccionCasoFeliz(request, response, "/", "Se genero el informe correctamentes");
      return null;
    } catch (Exception e) {
      redireccionCasoError(request, response, "/", "Fallo la generacion del informe");
      return null;
    }
  }


  public Void enviarMascotaEncontradaSinChapita(Request request, Response response) throws IOException {
    if (!tieneSesionActiva(request)) {
      response.redirect("/mascotas/encontre-mascota");
      return null;
    }
    List<Caracteristica> caracteristicas;
    List<Foto> fotos = new ArrayList<>();

    // Cargo la foto de la mascota
    File uploadDir = new File(Constantes.UPLOAD_DIRECTORY);
    Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".jpg");

    request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
    try (InputStream fotoInputStream = request.raw().getPart("fotos").getInputStream()) {
      Files.copy(fotoInputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException | ServletException exception) {
      System.out.println(exception);
      redireccionCasoError(request, response, "/", "Hubo un error al cargar la foto de tu mascota, intenta con otra foto");
    }

    //Obtengo sus caracteristicas
    caracteristicas = obtenerListaCaracteristicas(request);


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate fechaRescate = LocalDate.parse(request.queryParams("fechaRescate"), formatter);

    String ubicacionRescatistaString = request.queryParams("ubicacionRescatista");
    String ubicacionRescateString = request.queryParams("ubicacionRescate");
    Ubicacion ubicacionRescatista = new Ubicacion(1122.1, 122.1, ubicacionRescatistaString);
    Ubicacion ubicacionRescate = new Ubicacion(1122.1, 122.1, ubicacionRescateString);

    String estadoMascota = (String) request.queryParams("estadoMascota");

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
    redireccionCasoFeliz(request, response, "/", "La cuenta se ha registrado con exito!");
    return null;
  }


  private List<Caracteristica> obtenerListaCaracteristicas(Request request) {
    Set<String> queryParams = request.queryParams();

    List<Caracteristica> caracteristicas = new ArrayList<>();

    RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
    // Obtengo los nombre de caracteristicas para comparar con los query params :)
    List<String> listaNombresCaracteristicas = repositorioCaracteristicas.getCaracteristicas()
        .stream()
        .map(caracteristica -> caracteristica.getNombreCaracteristica())
        .collect(Collectors.toList());

    List<String> nombreParamsQueMandaron = queryParams
        .stream()
        .filter(param -> listaNombresCaracteristicas
            .stream()
            .anyMatch(c -> c.equals(param)))
        .collect(Collectors.toList());

    nombreParamsQueMandaron.forEach(
        param -> {
          Caracteristica caracteristica = new Caracteristica(param, request.queryParams(param));
          caracteristicas.add(caracteristica);
        }
    );
    return caracteristicas;
  }
}
