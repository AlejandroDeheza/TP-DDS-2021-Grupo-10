package modelo.informe;

import excepciones.InformeMascotaConDuenioInvalidaException;
import modelo.usuario.Usuario;

public class InformeMascotaConDuenioBuilder extends InformeMascotaBuilder {

  protected Usuario duenioMascota;

  public InformeMascotaBuilder conDuenioMascota(Usuario duenioMascota) {
    this.duenioMascota = duenioMascota;
    return this;
  }

  public static InformeMascotaConDuenioBuilder crearBuilder() {
    return new InformeMascotaConDuenioBuilder();
  }

  public InformeMascotaConDuenio build() {
    this.validarCampos();
    return new InformeMascotaConDuenio(duenioMascota, rescatista, fechaEncuentro, direccion,
        fotosMascota, lugarDeEncuentro, estadoActualMascota);
  }

  private void validarCampos() { // TODO: Null check smells
    if (this.duenioMascota == null || this.rescatista == null || this.fechaEncuentro == null
        || this.direccion == null || this.fotosMascota == null || this.lugarDeEncuentro == null
        || this.estadoActualMascota == null) {
      throw new InformeMascotaConDuenioInvalidaException(
          "Los campos de InformeMascotaConDuenioBuilder son inválidos");
    }
  }

}
