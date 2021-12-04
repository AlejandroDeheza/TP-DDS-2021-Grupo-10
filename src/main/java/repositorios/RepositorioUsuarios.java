package repositorios;

import excepciones.UserNameException;
import modelo.usuario.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class RepositorioUsuarios extends Repositorio<Usuario> {

  public RepositorioUsuarios() {
    super(Usuario.class);
  }

  public Usuario buscarPorUserName(String nombreUsuario) {
    List<Usuario> usuarios = listarTodos().stream()
        .filter(u -> u.getUsuario().equals(nombreUsuario))
        .collect(Collectors.toList());

    if (usuarios.size() == 0) {
      throw new UserNameException("No se encontro el nombre del Usuario");
    }

    return usuarios.get(0);
  }

  public Boolean yaExiste(String nombreUsuario) {
    return listarTodos().stream()
        .anyMatch(u -> u.getUsuario().equals(nombreUsuario));
  }

}
