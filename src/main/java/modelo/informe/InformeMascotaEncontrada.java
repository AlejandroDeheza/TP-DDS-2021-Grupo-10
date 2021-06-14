package modelo.informe;

import excepciones.InformeMascotaEncontradaInvalidaException;
import modelo.mascota.Foto;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import repositorios.RepositorioInformes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class  InformeMascotaEncontrada {

  private Persona rescatista;
  private Ubicacion direccion;
  protected MascotaEncontrada mascota;
  private RepositorioInformes repositorioInformes;


  public InformeMascotaEncontrada(Persona rescatista, Ubicacion direccion, MascotaEncontrada mascota) {
    this.rescatista = rescatista;
    this.direccion = direccion;
    this.mascota = mascota;
  }

  public void procesarInforme(){
    validarListaFotos();
    repositorioInformes.agregarInformeMascotaEncontrada(this);
  }

  private void validarListaFotos() {
    if (mascota.getFotos() == null || mascota.getFotos().isEmpty())
      throw new InformeMascotaEncontradaInvalidaException("Se debe ingresar al menos 1 Foto de la mascota encontrada");
  }

  public Persona getRescatista() {
    return rescatista;
  }

  public Ubicacion getDireccion() {
    return direccion;
  }


}
