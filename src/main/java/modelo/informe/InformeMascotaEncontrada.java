package modelo.informe;

import excepciones.InformeMascotaEncontradaInvalidaException;
import modelo.mascota.Foto;
import modelo.mascota.Mascota;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import repositorios.RepositorioInformes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class  InformeMascotaEncontrada {

  private Persona rescatista;
  private LocalDate fechaEncuentro;
  private Ubicacion direccion;
  private Ubicacion lugarDeEncuentro;
  private List<Foto> fotosMascota = new ArrayList<>();
  private List<Caracteristica> estadoActualMascota;


  public InformeMascotaEncontrada(Persona rescatista, LocalDate fechaEncuentro,
                                  Ubicacion direccion, List<Foto> fotosMascota, Ubicacion lugarDeEncuentro,
                                  List<Caracteristica>  estadoActualMascota) {
    this.rescatista = rescatista;
    this.fechaEncuentro = fechaEncuentro;
    this.direccion = direccion;
    this.fotosMascota.addAll(fotosMascota);
    this.lugarDeEncuentro = lugarDeEncuentro;
    this.estadoActualMascota = estadoActualMascota;
  }

  public void procesarInforme(){
    validarListaFotos();
    RepositorioInformes.getInstance().agregarInformeMascotaEncontrada(this);
  }

  private void validarListaFotos() {
    if (fotosMascota == null || fotosMascota.isEmpty())
      throw new InformeMascotaEncontradaInvalidaException("Se debe ingresar al menos 1 Foto de la mascota encontrada");
  }

  public Persona getRescatista() {
    return rescatista;
  }

  public LocalDate getFechaEncuentro() {
    return fechaEncuentro;
  }

  public Ubicacion getDireccion() {
    return direccion;
  }

  public Ubicacion getLugarDeEncuentro() {
    return lugarDeEncuentro;
  }

  public List<Caracteristica>  getEstadoActualMascota(){
    return estadoActualMascota;
  }

  public List<Foto> getFotosMascota(){
    return fotosMascota;
  }
}
