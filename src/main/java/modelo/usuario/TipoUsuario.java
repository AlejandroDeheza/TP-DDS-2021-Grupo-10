package modelo.usuario;

import javax.persistence.Embeddable;

@Embeddable
public enum TipoUsuario {
  NORMAL, ADMIN, VOLUNTARIO
}
