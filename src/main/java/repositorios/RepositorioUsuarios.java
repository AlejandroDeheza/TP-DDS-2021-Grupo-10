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

  public Usuario buscarPorUsuario(String usuario) {
    return listar().stream()
        .filter(u -> u.getUsuario().equals(usuario))
        .findFirst().get();
  }

  public Boolean yaExiste(String usuario) {
    return listar().stream()
        .anyMatch(u -> u.getUsuario().equals(usuario));
  }

  private List<Usuario> listar() {
    return entityManager()
        .createQuery("from Usuario", Usuario.class)
        .getResultList();
  }
}
