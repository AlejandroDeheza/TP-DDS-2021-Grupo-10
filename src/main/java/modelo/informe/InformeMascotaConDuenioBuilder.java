package modelo.informe;

import excepciones.InformeMascotaConDuenioInvalidaException;
import modelo.usuario.Usuario;
import servicio.notificacion.NotificacionCorreo;
import servicio.notificacion.NotificacionSender;

public class InformeMascotaConDuenioBuilder extends InformeMascotaBuilder {

  protected Usuario duenioMascota;
  protected NotificacionSender notificacionSender = new NotificacionCorreo(null);

  public InformeMascotaConDuenioBuilder conDuenioMascota(Usuario duenioMascota) {
    this.duenioMascota = duenioMascota;
    return this;
  }

  public InformeMascotaConDuenioBuilder conNotificacionSender(NotificacionSender notificacionSender) {
    this.notificacionSender = notificacionSender;
    return this;
  }

  public static InformeMascotaConDuenioBuilder crearBuilder() {
    return new InformeMascotaConDuenioBuilder();
  }

  public InformeMascotaConDuenio build() {
    this.validarCampos();
    return new InformeMascotaConDuenio(duenioMascota, rescatista, fechaEncuentro, direccion,
        fotosMascota, lugarDeEncuentro, estadoActualMascota, notificacionSender, repositorioInformes);
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
