package modelo.publicacion;

import modelo.EntidadPersistente;
import modelo.usuario.Usuario;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Publicacion extends EntidadPersistente {

  abstract void notificarAlPublicador(Usuario usuario);

}
