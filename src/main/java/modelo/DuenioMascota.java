package modelo;

import excepciones.DuenioMascotaMascotaRegistradaException;

import java.util.ArrayList;
import java.util.List;

public class DuenioMascota extends Usuario{

	private List<Mascota> listaMascotas;

	public DuenioMascota(String usuario, String contrasenia, Persona duenioMascota, List<Mascota> listaMascotas){
		super(usuario, contrasenia, duenioMascota);
		this.listaMascotas = listaMascotas;
	}

	public void agregarMascota(Mascota mascota){
		validarExistenciaMascota(mascota);
		this.listaMascotas.add(mascota);
	}

	public void eliminarMascota(Mascota mascota){
		this.listaMascotas.remove(mascota);
	}

	private void validarExistenciaMascota(Mascota mascota) {
		if(this.listaMascotas.contains(mascota)){
			throw new DuenioMascotaMascotaRegistradaException("Ya tiene registrada la mascota.")
		}
	}
}
