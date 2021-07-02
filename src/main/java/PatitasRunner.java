import modelo.adopcion.RecomendadorDeAdopciones;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesAdopcion;

public class PatitasRunner {
    public static void main(String[] args) {
        RepositorioDarEnAdopcion repositorioDarEnAdopcion = new RepositorioDarEnAdopcion();
        RepositorioSuscripcionesAdopcion repositorioSuscripcionesAdopcion = new RepositorioSuscripcionesAdopcion();
        RecomendadorDeAdopciones recomendadorDeAdopciones = new RecomendadorDeAdopciones(3,repositorioSuscripcionesAdopcion,repositorioDarEnAdopcion);
        recomendadorDeAdopciones.recomendarAdopciones();
    }
}
