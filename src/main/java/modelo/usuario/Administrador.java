package modelo.usuario;

import modelo.mascota.caracteristica.CaracteristicaConValores;
import modelo.persona.Persona;
import repositorios.RepositorioCaracteristicas;

public class Administrador extends Usuario {

	public Administrador(String username, String password, Persona persona) {
		super(username, password, persona);
	}

	public void agregarCaracteristica(CaracteristicaConValores caracteristica){
		RepositorioCaracteristicas.getInstance().agregarCaracteristica(caracteristica);
	}
}
