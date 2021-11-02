package modelo.notificacion;

import modelo.persona.DatosDeContacto;

public enum TipoNotificadorPreferido {
  CORREO {
    public Notificador getNotificador(DatosDeContacto contacto) {
      return new NotificadorCorreo(contacto.getEmail());
    }
  };

  public abstract Notificador getNotificador(DatosDeContacto contacto);
}
