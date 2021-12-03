package modelo.adopcion;

import modelo.mascota.Animal;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.pregunta.RespuestaDelAdoptante;
import modelo.publicacion.DarEnAdopcion;
import modelo.suscripcion.SuscripcionParaAdopcion;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import java.util.List;
import java.util.stream.Collectors;

public class RecomendadorDeAdopciones {
  private Integer limiteDeSugerencias;
  private RepositorioSuscripcionesParaAdopciones repositorioSuscripcionesParaAdopciones;
  private RepositorioDarEnAdopcion repositorioDarEnAdopcion;

  public RecomendadorDeAdopciones(Integer limiteDeSugerencias, RepositorioDarEnAdopcion repositorioDarEnAdopcion,
                                  RepositorioSuscripcionesParaAdopciones repositorioSuscripcionesParaAdopciones) {
    this.limiteDeSugerencias = limiteDeSugerencias;
    this.repositorioSuscripcionesParaAdopciones = repositorioSuscripcionesParaAdopciones;
    this.repositorioDarEnAdopcion = repositorioDarEnAdopcion;
  }

  public void recomendarAdopcionesASuscritos() {
    repositorioSuscripcionesParaAdopciones.getSuscripcionesActivas().forEach(this::recomendarAdopciones);
  }

  private void recomendarAdopciones(SuscripcionParaAdopcion suscripcion) {
    suscripcion.enviarRecomendaciones(
        generarRecomendaciones(suscripcion)
    );
  }

  public List<DarEnAdopcion> generarRecomendaciones(SuscripcionParaAdopcion suscripcion) {
    List<Caracteristica> caracteristicas = suscripcion.getPreferenciaDelAdoptante().getCaracteristicas();
    Animal animal = suscripcion.getPreferenciaDelAdoptante().getTipoAnimal();

    return repositorioDarEnAdopcion.getPublicacionesActivas().stream()
        .filter(publicacion -> publicacion.getMascotaEnAdopcion().getAnimal().equals(animal))
        .filter(publicacion -> publicacion.getMascotaEnAdopcion().cumpleConCaracteristicas(caracteristicas))
        .sorted((p1, p2) -> laPrimeraMatcheaConMas(p1, p2, suscripcion.getComodidadesDelAdoptante()))
        .limit(limiteDeSugerencias).collect(Collectors.toList());
  }

  private int laPrimeraMatcheaConMas(DarEnAdopcion p1, DarEnAdopcion p2, List<RespuestaDelAdoptante> comodidades) {
    return Integer.compare(
        p2.cantidadConLasQueMatchea(comodidades),
        p1.cantidadConLasQueMatchea(comodidades)
    );
  }
}
