package modelo;

import excepciones.DuenioMascotaMascotaRegistradaException;
import servicios.repositorios.RepositorioCaracteristicas;

import java.util.ArrayList;
import java.util.List;

public class DuenioMascota extends Usuario {
	List<Mascota> listaMascotas=new ArrayList<>();
	private RepositorioCaracteristicas repositorioCaracteristicas= RepositorioCaracteristicas.getInstance();
	public DuenioMascota(String username, String password, Persona persona) {
		super(username, password, persona);
	}

	public void agregarMascota(Mascota mascota){
		repositorioCaracteristicas.validarCaracteristicasMascotas(mascota.getCatacteristicas());
		validarExistenciaMascota(mascota);
		this.listaMascotas.add(mascota);
	}

	public void eliminarMascota(Mascota mascota){
		this.listaMascotas.remove(mascota);
	}

	private void validarExistenciaMascota(Mascota mascota) {
		if(this.listaMascotas.contains(mascota)){
			throw new DuenioMascotaMascotaRegistradaException("Ya tiene registrada la mascota");
		}
	}

	public List<Mascota> getListaMascotas() {
		return listaMascotas;
	}

}
