package modelo.mascota.caracteristica;

import repositorios.RepositorioCaracteristicas;

public class Caracteristica {

	String nombreCaracteristica;
	String valorCaracteristica;

	public Caracteristica(String nombreCaracteristica, String valorCaracteristica) {
		RepositorioCaracteristicas.getInstance()
				.validarCaracteristica(nombreCaracteristica, valorCaracteristica);
		this.nombreCaracteristica = nombreCaracteristica;
		this.valorCaracteristica = valorCaracteristica;
	}

	public String getNombreCaracteristica() {
		return nombreCaracteristica;
	}

	public String getValorCaracteristica() {
		return valorCaracteristica;
	}

}
