package modelo.mascota;

import modelo.mascota.caracteristica.Caracteristica;

import java.time.LocalDate;
import java.util.List;

public class MascotaEncontrada extends Mascota{

  private String estadoActual;
  private Contexto contextoEncuentro;

  public MascotaEncontrada(Animal animal, String nombre, String apodo, LocalDate fechaNacimiento, Sexo sexo, String descripcionFisica, List<Caracteristica> catacteristicas, List<Foto> fotos, String estadoActualMascota, Contexto contextoEncuentro) {
    super(animal, nombre, apodo, fechaNacimiento, sexo, descripcionFisica, catacteristicas, fotos);
    this.estadoActual = estadoActualMascota;
    this.contextoEncuentro = contextoEncuentro;
  }

  public String getEstadoActual(){
    return this.estadoActual;
  }
}
