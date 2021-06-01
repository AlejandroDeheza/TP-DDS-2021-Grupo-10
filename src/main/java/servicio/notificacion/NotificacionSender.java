package servicio.notificacion;

import modelo.persona.DatosDeContacto;

public interface NotificacionSender {

  public void enviarNotificacion(DatosDeContacto dc);
}
