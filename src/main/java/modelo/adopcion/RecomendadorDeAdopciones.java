package modelo.adopcion;

import modelo.mascota.Animal;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.pregunta.Respuesta;
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
    repositorioSuscripcionesParaAdopciones.getSuscripciones().forEach(this::recomendarAdopciones);
  }

  private void recomendarAdopciones(SuscripcionParaAdopcion suscripcion) {
    List<Caracteristica> caracteristicas = suscripcion.getPreferenciaDelAdoptante().getCaracteristicas();
    Animal animal = suscripcion.getPreferenciaDelAdoptante().getTipoAnimal();
    suscripcion.enviarRecomendaciones(
        repositorioDarEnAdopcion.getDarEnAdopcion()
            .stream()
            .filter(adopcion -> adopcion.getMascotaEnAdopcion().getAnimal().equals(animal))
            .filter(adopcion -> adopcion.getMascotaEnAdopcion().cumpleConCaracteristicas(caracteristicas))
            .sorted((a1, a2) -> elPrimeroMatcheaConMas(a1, a2, suscripcion.getComodidadesDelAdoptante()))
            .limit(limiteDeSugerencias).collect(Collectors.toList())
    );
  }

  private int elPrimeroMatcheaConMas(DarEnAdopcion a1, DarEnAdopcion a2, List<Respuesta> comodidades) {
    return Integer.compare(
        a1.cantidadConLasQueMatchea(comodidades),
        a2.cantidadConLasQueMatchea(comodidades)
    );
  }
}
