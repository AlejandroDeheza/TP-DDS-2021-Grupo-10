package servicios.repositorios;
import excepciones.CaracteristicasVacioException;
import excepciones.ValorCaracteristicaIncompatibleException;
import modelo.Caracteristica;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import utils.Util;


public class RepositorioCaracteristicas {
	private static RepositorioCaracteristicas repositorioCaracteristicas;
	private List<Caracteristica> listaCaracteristicas = new ArrayList<>();
	private Util utils = new Util();

	public static RepositorioCaracteristicas getInstance() {
		if (repositorioCaracteristicas == null) {
			repositorioCaracteristicas = new RepositorioCaracteristicas();
		}
		return repositorioCaracteristicas;
	}

	public void agregarCaracteristica(Caracteristica caracteristica) {
		utils.convertoToLower(caracteristica);
		listaCaracteristicas.add(caracteristica);
	}

	public List<Caracteristica> getListaCaracteristicas() {
		return listaCaracteristicas;
	}

	public void validarCaracteristicasMascotas(List<Caracteristica> catacteristicas) {
		catacteristicas.forEach(caracteristica -> {
			esValorValido(caracteristica);
		});
	}

	private void esValorValido(Caracteristica caracteristica) {
		utils.convertoToLower(caracteristica);
		List<Caracteristica> listaCaracteristicasFiltered = listaCaracteristicas
				.stream()
				.filter(c -> c.getNombreCaracteristica().equals(caracteristica.getNombreCaracteristica())).collect(Collectors.toList());

		if (listaCaracteristicasFiltered.isEmpty())
			throw new CaracteristicasVacioException("La caracteristica no coincide con la agregada");
		Boolean contieneCaracteristica = listaCaracteristicasFiltered
					.get(0)
					.getValoresCaracteristicas()
					.contains(caracteristica.getValoresCaracteristicas().get(0)); //La caractersitica esta en la lista

			if (!contieneCaracteristica)
				throw new ValorCaracteristicaIncompatibleException("Los valores de la caracteristica no es valida ");
	}
}