package main;

import modelo.adopcion.RecomendadorDeAdopciones;
import repositorios.RepositorioDarEnAdopcion;
import repositorios.RepositorioSuscripcionesParaAdopciones;
import java.util.Timer;
import java.util.TimerTask;

public class PatitasRunner {

  public static void main(String[] args) {new PatitasRunner().run();}
    // creating timer task, timer
    public void run() {
    TimerTask task = new TimerTask() {
      public void run() {
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
    };
    Timer timer = new Timer();

    // scheduling the task at interval
    timer.schedule(task,60000, 60000);
  }
}
