package modelo.informe;

import modelo.mascota.Mascota;
import modelo.usuario.Usuario;

public class InformeMascotaSinDuenioBuilder extends InformeMascotaBuilder {

  public static InformeMascotaSinDuenioBuilder crearBuilder(){ return new InformeMascotaSinDuenioBuilder(); }

  private Mascota mascota;

  public InformeMascotaBuilder conMascota(Mascota mascota) {
    this.mascota = mascota;
    return this;
  }

  public InformeMascotaSinDuenio build(){
    return new InformeMascotaSinDuenio(rescatista, fechaEncuentro, direccion, lugarDeEncuentro, mascota);
  }

}