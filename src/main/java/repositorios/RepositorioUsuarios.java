package repositorios;

import modelo.usuario.Usuario;
import java.util.stream.Collectors;

public class RepositorioUsuarios extends Repositorio<Usuario> {

  public RepositorioUsuarios() {
    super(Usuario.class);
  }

  public Usuario buscarPorUserName(String nombreUsuario) {
    return listarTodos().stream()
        .filter(u -> u.getUsuario().equals(nombreUsuario))
        .collect(Collectors.toList())
        .get(0);
  }

  public Boolean yaExiste(String nombreUsuario) {
    return listarTodos().stream()
        .anyMatch(u -> u.getUsuario().equals(nombreUsuario));
  }

}
