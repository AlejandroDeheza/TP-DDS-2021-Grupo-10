package modelo.mascota;

import excepciones.FotosMascotaException;

import java.time.LocalDate;
import java.util.List;

public class MascotaEncontrada {

  private String estadoActual;
  private Contexto contextoEncuentro;
  private List<Foto> fotos;
  private TamanioMascota tamanio;

  public MascotaEncontrada(String estadoActual, Contexto contextoEncuentro, List<Foto> fotos, TamanioMascota tamanio) {
    validarFotos(fotos);
    this.estadoActual = estadoActual;
    this.contextoEncuentro = contextoEncuentro;
    this.fotos = fotos;
    this.tamanio = tamanio;
  }

  private void validarFotos(List<Foto> fotos) {
    if (fotos == null || fotos.isEmpty())
      throw new FotosMascotaException("Se debe ingresar al menos una foto de la mascota");
  }

  public String getEstadoActual(){
    return this.estadoActual;
  }

  public LocalDate getFechaEncuentro(){
    return contextoEncuentro.getFechaEncuentro();
  }

  public List<Foto> getFotos() {
    return fotos;
  }

  public TamanioMascota getTamanio() {
    return tamanio;
  }
}