package modelo;

<<<<<<< HEAD
public class Administrador extends Usuario{

  public Administrador(String usuario, String contrasenia, Persona persona){
    super(usuario, contrasenia, persona);
  }
=======
import excepciones.DuenioMascotaMascotaRegistradaException;
import servicios.repositorios.RepositorioCaracteristicas;

import java.util.List;

public class Administrador extends Usuario {
	public Administrador(String username, String password, Persona persona) {
		super(username, password, persona);
	}

	public void agregarCaracteristica(Caracteristica caracteristica){
		respositorioCaracteristicas.agregarCaracteristica(caracteristica);
	}
>>>>>>> 786d5af (Agrego herencia del Administrador)
}
