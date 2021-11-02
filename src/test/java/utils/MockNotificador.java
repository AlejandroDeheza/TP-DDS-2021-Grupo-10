package utils;

import modelo.notificacion.Notificador;
import modelo.notificacion.TipoNotificadorPreferido;

public class MockNotificador {

  private TipoNotificadorPreferido tipo;
  private Notificador notificador;

  public MockNotificador(TipoNotificadorPreferido tipo, Notificador notificador) {
    this.tipo = tipo;
    this.notificador = notificador;
  }

  public TipoNotificadorPreferido getTipo() {
    return tipo;
  }

  public Notificador getNotificador() {
    return notificador;
  }
}
