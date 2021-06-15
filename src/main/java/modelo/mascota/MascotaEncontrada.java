package modelo.mascota;

import modelo.mascota.caracteristica.Caracteristica;

import java.time.LocalDate;
import java.util.List;

public class MascotaEncontrada extends Mascota{

  private String estadoActual;
  private Contexto contextoEncuentro;
  private TamanioMascota tamanio;

  public MascotaEncontrada(Animal animal, Sexo sexo, String descripcionFisica, List<Caracteristica> caracteristicas, List<Foto> fotos, String estadoActual, Contexto contextoEncuentro, TamanioMascota tamanio) {
    super(animal, sexo, descripcionFisica, caracteristicas, fotos);
    this.estadoActual = estadoActual;
    this.contextoEncuentro = contextoEncuentro;
    this.tamanio = tamanio;
  }

  public String getEstadoActual(){
    return this.estadoActual;
  }

  public LocalDate getFechaEncuentro(){
    return contextoEncuentro.getFechaEncuentro();
  }
}