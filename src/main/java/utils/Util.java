package utils;

import modelo.Caracteristica;

public class Util {

	public void convertoToLower(Caracteristica caracteristica) {
		caracteristica.setNombreCaracteristica(caracteristica.getNombreCaracteristica().toLowerCase());
		caracteristica.getValoresCaracteristicas().forEach(v -> {v.toLowerCase();});
	}

}
