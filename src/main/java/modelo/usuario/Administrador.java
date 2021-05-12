package modelo.usuario;

import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.persona.Persona;
import repositorios.RepositorioCaracteristicas;

public class Administrador extends Usuario {

	private RepositorioCaracteristicas repo;

	public Administrador(String username, String password, Persona persona, RepositorioCaracteristicas repo) {
		super(username, password, persona);
		this.repo = repo;
	}

	public void agregarCaracteristica(CaracteristicaConValoresPosibles caracteristica){
		repo.agregarCaracteristica(caracteristica);
	}
}
