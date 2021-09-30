package modelo.publicacion;

import modelo.usuario.Usuario;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Publicacion {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;

  public Long getId() {
    return id;
  }

  abstract void notificarAlPublicador(Usuario usuario);

}
