package modelo.informe;

import modelo.mascota.Foto;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import repositorios.RepositorioInformes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class InformeMascotaBuilder {
  protected Persona rescatista;
  protected LocalDate fechaEncuentro;
  protected Ubicacion direccion;
  protected List<Foto> fotosMascota = new ArrayList<>();
  protected Ubicacion lugarDeEncuentro;
  protected Caracteristica estadoActualMascota;
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

  public InformeMascotaBuilder conDireccion(Ubicacion direccion) {
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

  public InformeMascotaBuilder conEstadoActualMascota(Caracteristica estadoActualMascota) {
    this.estadoActualMascota = estadoActualMascota;
    return this;
  }

  public InformeMascotaBuilder conRepositorioInformes(RepositorioInformes repositorioInformes) {
    this.repositorioInformes = repositorioInformes;
    return this;
  }

  public abstract InformeMascotaEncontrada build();
}
