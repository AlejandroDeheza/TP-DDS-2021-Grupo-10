package repositorios;

import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;

public class RepositorioUsuarios implements WithGlobalEntityManager {

  public void agregar(Usuario usuario) {
    entityManager().persist(usuario);
  }

  public Usuario getPorId(Long id){
    return entityManager().find(Usuario.class, id);
  }

  public Usuario buscarPorUsuario(String nombreUsuario) {
    return listar().stream()
        .filter(u -> u.getUsuario().equals(nombreUsuario))
        .findFirst().get();
  }

  public Boolean yaExiste(String nombreUsuario) {
    return listar().stream()
        .anyMatch(u -> u.getUsuario().equals(nombreUsuario));
  }

  private List<Usuario> listar() {
    return entityManager()
        .createQuery("from Usuario", Usuario.class)
        .getResultList();
  }
}
