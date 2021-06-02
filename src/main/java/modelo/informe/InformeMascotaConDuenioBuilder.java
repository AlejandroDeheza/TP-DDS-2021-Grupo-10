package modelo.informe;

import modelo.usuario.Usuario;

public class InformeMascotaConDuenioBuilder extends InformeMascotaBuilder{

  protected Usuario duenioMascota;

  public InformeMascotaBuilder conDuenioMascota(Usuario duenioMascota) {
    this.duenioMascota = duenioMascota;
    return this;
  }

  public static InformeMascotaConDuenioBuilder crearBuilder(){ return new InformeMascotaConDuenioBuilder(); }

  public InformeMascotaConDuenio build(){
    return new InformeMascotaConDuenio(duenioMascota, rescatista, fechaEncuentro, direccion, fotosMascota, lugarDeEncuentro, estadoActualMascota, repositorioInformes);
  }

}
