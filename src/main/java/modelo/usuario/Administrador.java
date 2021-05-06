package modelo.usuario;

import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import repositorios.RepositorioCaracteristicas;


public class Administrador extends Usuario {

	private RepositorioCaracteristicas respositorioCaracteristicas= RepositorioCaracteristicas.getInstance();
	public Administrador(String username, String password, Persona persona) {
		super(username, password, persona);
	}

	public void agregarCaracteristica(Caracteristica caracteristica){
		respositorioCaracteristicas.agregarCaracteristica(caracteristica);
	}
}
