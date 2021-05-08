package modelo.usuario;

import excepciones.DuenioMascotaMascotaRegistradaException;
import modelo.mascota.Mascota;
import modelo.persona.Persona;
import java.util.ArrayList;
import java.util.List;

public class DuenioMascota extends Usuario {

	List<Mascota> listaMascotas = new ArrayList<>();

	public DuenioMascota(String username, String password, Persona persona) {
		super(username, password, persona);
	}

	public void agregarMascota(Mascota mascota){
		validarExistenciaMascota(mascota);
		this.listaMascotas.add(mascota);
	}

	public void eliminarMascota(Mascota mascota){
		this.listaMascotas.remove(mascota);
	}

	private void validarExistenciaMascota(Mascota mascota) {
		if(this.listaMascotas.contains(mascota))
			throw new DuenioMascotaMascotaRegistradaException("Ya tiene registrada la mascota");
	}

}
