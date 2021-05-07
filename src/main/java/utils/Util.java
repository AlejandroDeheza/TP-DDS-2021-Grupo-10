package utils;

import modelo.mascota.caracteristica.Caracteristica;

import java.util.List;
import java.util.stream.Collectors;

public class Util {

	public void convertToLower(Caracteristica caracteristica) {
		caracteristica.setNombreCaracteristica(caracteristica.getNombreCaracteristica().toLowerCase());
		List<String> valoresCaracteristicasEnMinusculas = caracteristica
				.getValoresCaracteristicas()
				.stream()
				.map(String::toLowerCase)
				.collect(Collectors.toList());
		caracteristica.setValoresCaracteristicas(valoresCaracteristicasEnMinusculas);
	}

}
