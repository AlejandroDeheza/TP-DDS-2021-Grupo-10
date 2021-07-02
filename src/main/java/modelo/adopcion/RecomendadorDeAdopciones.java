package modelo.adopcion;

import modelo.mascota.MascotaRegistrada;
import modelo.pregunta.ParDeRespuestas;
import modelo.pregunta.Respuesta;
import modelo.publicacion.DarEnAdopcion;
import modelo.publicacion.Preferencia;
import modelo.publicacion.SuscripcionAdopciones;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesAdopcion;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

public class RecomendadorDeAdopciones {
    private Integer limiteDeSugerencias;
    private RepositorioSuscripcionesAdopcion repositorioSuscripcionesAdopcion;
    private RepositorioDarEnAdopcion repositorioDarEnAdopcion;

    public RecomendadorDeAdopciones(Integer limiteDeSugerencias, RepositorioSuscripcionesAdopcion repositorioSuscripcionesAdopcion, RepositorioDarEnAdopcion repositorioDarEnAdopcion) {
        this.limiteDeSugerencias = limiteDeSugerencias;
        this.repositorioSuscripcionesAdopcion = repositorioSuscripcionesAdopcion;
        this.repositorioDarEnAdopcion = repositorioDarEnAdopcion;
    }

    public void recomendarAdopciones() {
        List<SuscripcionAdopciones> suscripcionAdopcionesList = repositorioSuscripcionesAdopcion.getIntencionesDeAdopcion();
        suscripcionAdopcionesList.forEach(
                suscripcion -> suscripcion.enviarRecomendaciones(obtenerMatchDeMascotas(suscripcion)));
    }

    public List<MascotaRegistrada> obtenerMatchDeMascotas(SuscripcionAdopciones suscripcionAdopciones){
        List<Respuesta> comodidades = suscripcionAdopciones.getComodidadesDelAdoptante();
        Preferencia preferencia = suscripcionAdopciones.getPreferenciaDelAdoptante();
        List<DarEnAdopcion> adopcionesActivas = repositorioDarEnAdopcion.getDarEnAdopcion();
        List<DarEnAdopcion> adopcionesMatch = adopcionesActivas.stream().filter(adopcion -> esMatch(adopcion,comodidades,preferencia)).collect(Collectors.toList());
        List<MascotaRegistrada> mascotasQueMatchean = adopcionesMatch.stream().map(adopcion -> adopcion.getMascotaEnAdopcion()).collect(Collectors.toList());
        if(mascotasQueMatchean.size() > limiteDeSugerencias) {
            return mascotasQueMatchean.stream().limit(limiteDeSugerencias).collect(Collectors.toList());
        }
        return mascotasQueMatchean;
    }

    private boolean esMatch(DarEnAdopcion adopcion, List<Respuesta> comodidades, Preferencia preferencia) {
        return adopcion.getMascotaEnAdopcion().getCaracteristicas().contains(preferencia.getCaracteristicas());
    //TODO MATCH DE PREGUNTAS Y RESPUESTAS
    //comodidades.forEach(respuesta ->
    //buscarRecomendaciones(respuesta));
    }

    private void buscarRecomendaciones(Respuesta respuesta) {
        List<ParDeRespuestas> respuestasPosibles = new ArrayList<>();
        respuestasPosibles = respuesta.getParDePreguntas()
                .getParesDeRespuestas()
                .stream()
                .filter(parDeRespuestas -> parDeRespuestas.getDelAdoptante().equals(respuesta.getRespuesta()))
                .collect(Collectors.toList());
    }
}
