package main;

import modelo.adopcion.RecomendadorDeAdopciones;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import java.util.Timer;
import java.util.TimerTask;

public class PatitasRunner {

  RepositorioDarEnAdopcion repositorioDarEnAdopcion = new RepositorioDarEnAdopcion();
  RepositorioSuscripcionesParaAdopciones repositorioSuscripcionesParaAdopciones =
      new RepositorioSuscripcionesParaAdopciones();

  RecomendadorDeAdopciones recomendadorDeAdopciones = new RecomendadorDeAdopciones(
      3,
      repositorioDarEnAdopcion,
      repositorioSuscripcionesParaAdopciones
  );


  public static void main(String[] args) {

    new PatitasRunner().run();
  }

  // creating timer task, timer
  public void run() {
    TimerTask task = new TimerTask() {
      public void run() {
        recomendadorDeAdopciones.recomendarAdopcionesASuscritos();
        System.out.println("Recomendando a los Suscriptores");
      }
    };
    Timer timer = new Timer();

    // scheduling the task at interval
    timer.schedule(task,30000, 60000 * 10);
  }
}
