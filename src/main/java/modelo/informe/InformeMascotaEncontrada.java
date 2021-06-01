package modelo.informe;

import excepciones.InformeMascotaEncontradaInvalidaException;
import modelo.mascota.Foto;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import repositorios.RepositorioInformes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class  InformeMascotaEncontrada {

  private Usuario duenioMascota;
  private Persona rescatista;
  private LocalDate fechaEncuentro;
  private String direccion;
  private List<Foto> fotosMascota = new ArrayList<>();
  private Ubicacion lugarDeEncuentro;
  private String estadoActualMascota;
  private RepositorioInformes repositorioInformes;

  public InformeMascotaEncontrada(Usuario duenioMascota, Persona rescatista, LocalDate fechaEncuentro,
                                  String direccion, List<Foto> fotosMascota, Ubicacion lugarDeEncuentro,
                                  String estadoActualMascota, RepositorioInformes repositorioInformes) {
    this.duenioMascota = duenioMascota;
    this.rescatista = rescatista;
    this.fechaEncuentro = fechaEncuentro;
    this.direccion = direccion;
    this.fotosMascota.addAll(fotosMascota);
    this.lugarDeEncuentro = lugarDeEncuentro;
    this.estadoActualMascota = estadoActualMascota;
    this.repositorioInformes = repositorioInformes;
  }

  public void procesarInforme(){
    validarListaFotos();
    repositorioInformes.agregarInformeMascotaEncontrada(this);
  }

  private void validarListaFotos() {
    if (fotosMascota == null || fotosMascota.isEmpty())
      throw new InformeMascotaEncontradaInvalidaException("Se debe ingresar al menos 1 Foto de la mascota encontrada");
  }

  private void agregarInformePendiente() {
    if (fotosMascota == null || fotosMascota.isEmpty())
      throw new InformeMascotaEncontradaInvalidaException("Se debe ingresar al menos 1 Foto de la mascota encontrada");
  }

  public Usuario getDuenioMascota() {
    return duenioMascota;
  }

  public Persona getRescatista() {
    return rescatista;
  }

  public LocalDate getFechaEncuentro() {
    return fechaEncuentro;
  }

  public String getDireccion() {
    return direccion;
  }

  public List<Foto> getFotosMascota() {
    return fotosMascota;
  }

  public Ubicacion getLugarDeEncuentro() {
    return lugarDeEncuentro;
  }

  public String getEstadoActualMascota() {
    return estadoActualMascota;
  }
}
