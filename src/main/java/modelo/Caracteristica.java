package modelo;

public class Caracteristica {
	private String nombreCaracteristica;
	private String descripcionCaracteristica;

	public Caracteristica(String nombreCaracteristica) {
		this.nombreCaracteristica = nombreCaracteristica;
	}

	public void setDescripcionCaracteristica(String descripcionCaracteristica) {
		this.descripcionCaracteristica = descripcionCaracteristica;
	}

	public String getNombreCaracteristica() {
		return nombreCaracteristica;
	}

	public String getDescripcionCaracteristica() {
		return descripcionCaracteristica;
	}
}
