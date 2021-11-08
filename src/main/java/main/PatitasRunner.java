package main;

import modelo.adopcion.RecomendadorDeAdopciones;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesParaAdopciones;

public class PatitasRunner {

  public static void main(String[] args) {
    RepositorioDarEnAdopcion repositorioDarEnAdopcion = new RepositorioDarEnAdopcion();
    RepositorioSuscripcionesParaAdopciones repositorioSuscripcionesParaAdopciones =
        new RepositorioSuscripcionesParaAdopciones();

    RecomendadorDeAdopciones recomendadorDeAdopciones = new RecomendadorDeAdopciones(
        3,
        repositorioDarEnAdopcion,
        repositorioSuscripcionesParaAdopciones
    );
    recomendadorDeAdopciones.recomendarAdopcionesASuscritos();
    System.out.println("Recomendando a los Suscriptores");
  }
}
