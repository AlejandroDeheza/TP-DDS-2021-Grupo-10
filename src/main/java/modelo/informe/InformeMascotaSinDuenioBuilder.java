package modelo.informe;

public class InformeMascotaSinDuenioBuilder extends InformeMascotaBuilder{

  public static InformeMascotaSinDuenioBuilder crearBuilder(){ return new InformeMascotaSinDuenioBuilder(); }

  public InformeMascotaSinDuenio build(){
    return new InformeMascotaSinDuenio(rescatista, fechaEncuentro, direccion, fotosMascota, lugarDeEncuentro, estadoActualMascota, repositorioInformes);
  }

}