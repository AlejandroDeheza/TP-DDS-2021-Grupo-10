package repositorios;

import excepciones.CaracteristicasInvalidaException;
import excepciones.ValorCaracteristicaIncompatibleException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;

public class RepositorioCaracteristicas {

	private static RepositorioCaracteristicas repositorioCaracteristicas;
	private List<CaracteristicaConValoresPosibles> caracteristicas = new ArrayList<>();

	//usamos el constructor solo para tests
	public RepositorioCaracteristicas() {}

	//usamos el getInstance en el codigo de produccion
	public static RepositorioCaracteristicas getInstance() {
		if (repositorioCaracteristicas == null) {
			repositorioCaracteristicas = new RepositorioCaracteristicas();
		}
		return repositorioCaracteristicas;
	}

	public void agregarCaracteristica(CaracteristicaConValoresPosibles caracteristica) {
		caracteristicas.add(caracteristica);
	}

	public List<CaracteristicaConValoresPosibles> getCaracteristicas() {
		return caracteristicas;
	}

	public void validarCaracteristica(String nombreCaracteristica, String valorCaracteristica) {

		List<CaracteristicaConValoresPosibles> listaCaracteristicasFiltered = caracteristicas
				.stream()
				.filter(c -> c.getNombreCaracteristica().equals(nombreCaracteristica))
				.collect(Collectors.toList());

		if (listaCaracteristicasFiltered.isEmpty())
			throw new CaracteristicasInvalidaException("El tipo de caracteristica ingresada no es valida");

		Boolean contieneCaracteristica = listaCaracteristicasFiltered
					.get(0)
					.getValoresCaracteristicas()
					.contains(valorCaracteristica); //La caractersitica esta en la lista

		if (!contieneCaracteristica)
			throw new ValorCaracteristicaIncompatibleException("El valor de la caracteristica ingresada no es valida ");
	}
}