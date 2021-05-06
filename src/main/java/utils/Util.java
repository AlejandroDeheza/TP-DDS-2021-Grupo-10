package utils;

import modelo.mascota.caracteristica.Caracteristica;

public class Util {

	public void convertoToLower(Caracteristica caracteristica) {
		caracteristica.setNombreCaracteristica(caracteristica.getNombreCaracteristica().toLowerCase());
		caracteristica.getValoresCaracteristicas().forEach(v -> {v.toLowerCase();});
	}

}
