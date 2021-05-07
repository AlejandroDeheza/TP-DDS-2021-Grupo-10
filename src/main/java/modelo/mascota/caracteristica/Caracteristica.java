package modelo.mascota.caracteristica;

import java.util.List;

public class Caracteristica {

	String nombreCaracteristica;
	List<String> valoresCaracteristicas;

	public String getNombreCaracteristica() {
		return nombreCaracteristica;
	}

	public List<String> getValoresCaracteristicas() {
		return valoresCaracteristicas;
	}

	public Caracteristica(String nombreCaracteristica, List<String> valoresCaracteristicas) {
		this.nombreCaracteristica = nombreCaracteristica;
		this.valoresCaracteristicas = valoresCaracteristicas;
	}

	public void setNombreCaracteristica(String nombreCaracteristica) {
		this.nombreCaracteristica = nombreCaracteristica;
	}

	public void setValoresCaracteristicas(List<String> valoresCaracteristicas) {
		this.valoresCaracteristicas = valoresCaracteristicas;
	}
}
