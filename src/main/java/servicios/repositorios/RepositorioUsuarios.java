package servicios.repositorios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import modelo.Usuario;

public class RepositorioUsuarios {
	private static RepositorioUsuarios repositorioUsuarios;
	private List<Usuario> listaUsuarios = new ArrayList<>();

	public static RepositorioUsuarios getInstance() {
		if(repositorioUsuarios == null) {
			repositorioUsuarios = new RepositorioUsuarios();
		}
		return repositorioUsuarios;
	}

	public void agregarUsuario(Usuario usuario) {
		listaUsuarios.add(usuario);
	}

}