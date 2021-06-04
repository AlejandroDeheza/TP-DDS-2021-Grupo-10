package modelo.informe;

import modelo.mascota.Animal;
import repositorios.RepositorioPublicaciones;

public class InformeMascotaSinDuenioBuilder extends InformeMascotaBuilder {

  public static InformeMascotaSinDuenioBuilder crearBuilder(){ return new InformeMascotaSinDuenioBuilder(); }
  private Animal animal;
  private RepositorioPublicaciones repositorioPublicaciones;

  public InformeMascotaBuilder conAnimal(Animal animal) {
    this.animal = animal;
    return this;
  }

  public InformeMascotaSinDuenio build(){
    return new InformeMascotaSinDuenio(rescatista, fechaEncuentro, direccion,
            fotosMascota, lugarDeEncuentro, estadoActualMascota, animal, repositorioInformes, repositorioPublicaciones);
  }

  public void conRepositorioPublicaciones(RepositorioPublicaciones repositorioPublicaciones) {
    this.repositorioPublicaciones = repositorioPublicaciones;
  }
}