package modelo.informe;

import modelo.mascota.Foto;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import repositorios.RepositorioInformes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class InformeMascotaBuilder {
  protected Persona rescatista;
  protected LocalDate fechaEncuentro;
  protected String direccion;
  protected List<Foto> fotosMascota = new ArrayList<>();
  protected Ubicacion lugarDeEncuentro;
  protected String estadoActualMascota;
  protected RepositorioInformes repositorioInformes;

  protected InformeMascotaBuilder() {
  }

  public InformeMascotaBuilder conRescatista(Persona rescatista) {
    this.rescatista = rescatista;
    return this;
  }

  public InformeMascotaBuilder conFechaEncuentro(LocalDate fechaEncuentro) {
    this.fechaEncuentro = fechaEncuentro;
    return this;
  }

  public InformeMascotaBuilder conDireccion(String direccion) {
    this.direccion = direccion;
    return this;
  }

  public InformeMascotaBuilder conFotosMascota(List<Foto> fotosMascota) {
    this.fotosMascota = fotosMascota;
    return this;
  }

  public InformeMascotaBuilder conLugarDeEncuentro(Ubicacion lugarDeEncuentro) {
    this.lugarDeEncuentro = lugarDeEncuentro;
    return this;
  }

  public InformeMascotaBuilder conEstadoActualMascota(String estadoActualMascota) {
    this.estadoActualMascota = estadoActualMascota;
    return this;
  }

  public InformeMascotaBuilder conRepositorioInformes(RepositorioInformes repositorioInformes) {
    this.repositorioInformes = repositorioInformes;
    return this;
  }

  public abstract InformeMascotaEncontrada build();
}
