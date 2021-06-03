package servicio.notificacion;

import modelo.persona.DatosDeContacto;
import modelo.persona.Persona;

public interface NotificacionSender {

    public void enviarNotificacion(DatosDeContacto datosDeContacto, MensajeAMandar mensajeAMandar);
}
