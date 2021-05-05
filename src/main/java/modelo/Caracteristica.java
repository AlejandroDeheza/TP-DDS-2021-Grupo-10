package modelo;

import excepciones.CaracteristicaInvalida;
import servicios.repositorios.RepositorioCaracteristicas;

public class Caracteristica {
	NombreCaracteristica nombreCaracteristica;
	private String descripcionCaracteristica;

	public Caracteristica(NombreCaracteristica nombreCaracteristica, String descripcionCaracteristica) {
		validarExistenciaCaracteristica(nombreCaracteristica);
		this.nombreCaracteristica = nombreCaracteristica;
		this.descripcionCaracteristica = descripcionCaracteristica;
	}

	private void validarExistenciaCaracteristica(NombreCaracteristica nombreCaracteristica) {
			if (RepositorioCaracteristicas.getInstance().existeEnListaCaracteristica(nombreCaracteristica))
				throw new CaracteristicaInvalida("La caracteristica "+nombreCaracteristica.getNombreCaracterista()+" es invalida");
	}

	public NombreCaracteristica getNombreCaracteristica() {
		return nombreCaracteristica;
	}

	public String getDescripcionCaracteristica() {
		return descripcionCaracteristica;
	}
}
