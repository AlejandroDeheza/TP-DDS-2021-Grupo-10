import modelo.adopcion.RecomendadorDeAdopciones;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesParaAdopciones;

public class PatitasRunner {

  public static void main(String[] args) {

    RecomendadorDeAdopciones recomendadorDeAdopciones = new RecomendadorDeAdopciones(
        3,
        RepositorioDarEnAdopcion.getInstance(),
        RepositorioSuscripcionesParaAdopciones.getInstance()
    );
    recomendadorDeAdopciones.recomendarAdopcionesASuscritos();
  }
}
