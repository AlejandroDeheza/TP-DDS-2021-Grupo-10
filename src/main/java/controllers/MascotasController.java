package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import modelo.mascota.*;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioCaracteristicas;
import repositorios.RepositorioMascotas;
import repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MascotasController extends Controller implements WithGlobalEntityManager, TransactionalOps {

  public ModelAndView mostrarRegistracion(Request request, Response response) {
    if (!tieneSesionActiva(request)) {
      response.redirect("/login?origin=/registracion-mascota");
      return null;
    }
    Map<String, Object> modelo = getMap(request);
    RepositorioCaracteristicas repositorioCaracteristicas = new RepositorioCaracteristicas();
    List<CaracteristicaConValoresPosibles> listaCaracteristicas = repositorioCaracteristicas.getCaracteristicasConValoresPosibles();
    modelo.put("caracteristicas", listaCaracteristicas);

    return new ModelAndView(modelo, "registracion-mascota.html.hbs");
  }

  public Void registrarMascota(Request request, Response response) {
    List<Caracteristica> caracteristicas;
    List<Foto> fotos;

    try {
      caracteristicas = new ArrayList<>(new ObjectMapper().readValue(
          request.queryParams("caracteristicas"),
          new TypeReference<List<Caracteristica>>() {
          }
      ));

      fotos = new ArrayList<>(new ObjectMapper().readValue(
          request.queryParams("fotos"),
          new TypeReference<List<Foto>>() {
          }
      ));
    } catch (JsonProcessingException e) {
      redireccionCasoError(request, response, "/", "Hubo un error al cargar los datos, disculpe las molestias");
      return null;
    }

    MascotaRegistrada nueva = new MascotaRegistrada(
        new RepositorioUsuarios().getPorId(request.session().attribute("user_id")),
        request.queryParams("nombre"),
        request.queryParams("apodo"),
        LocalDate.parse(request.queryParams("fechaNacimiento")),
        request.queryParams("descripcionFisica"),
        Sexo.values()[Integer.parseInt(request.queryParams("sexo"))],
        Animal.values()[Integer.parseInt(request.queryParams("animal"))],
        caracteristicas,
        fotos,
        TamanioMascota.values()[Integer.parseInt(request.queryParams("tamanio"))]
    );

    withTransaction(() -> {
      new RepositorioMascotas().agregar(nueva);
    });

    redireccionCasoFeliz(request, response, "/", "Tu mascota se ha agregado con exito!");
    return null;
  }
}
