package servicios.repositorios;


import excepciones.CaracteristicaExistente;
import modelo.NombreCaracteristica;
import modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioCaracteristicas {
	private static RepositorioCaracteristicas repositorioCaracteristicas;
	private List<NombreCaracteristica> listaCaracteristicas = new ArrayList<>();

	public static RepositorioCaracteristicas getInstance() {
		if(repositorioCaracteristicas == null) {
			repositorioCaracteristicas = new RepositorioCaracteristicas();
		}
		return repositorioCaracteristicas;
	}

	public void agregarCaracteristica(NombreCaracteristica caracteristica) {
		if(existeEnListaCaracteristica(caracteristica))
			throw new CaracteristicaExistente("Ya existe la caracteristica: "+caracteristica.getNombreCaracterista());
		listaCaracteristicas.add(caracteristica);
	}

	public List<NombreCaracteristica> getListaCaracteristicas(){
		return listaCaracteristicas;
	}

	public Boolean existeEnListaCaracteristica(NombreCaracteristica caracteristica){
		return (listaCaracteristicas.contains(caracteristica));
	}
}