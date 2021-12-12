package modelo.mascota.caracteristica;

import java.util.List;
import java.util.stream.Collectors;

public class CaracteristicaConValoresPosibles {

  private final String nombreCaracteristica;
  private final List<String> valoresCaracteristicas;

  public CaracteristicaConValoresPosibles(String nombreCaracteristica, List<String> valoresCaracteristicas) {
    this.nombreCaracteristica = nombreCaracteristica;
    this.valoresCaracteristicas = valoresCaracteristicas;
  }

  public String getNombreCaracteristica() {
    return nombreCaracteristica;
  }

  public List<String> getValoresCaracteristicas() {
    return valoresCaracteristicas;
  }

  public List<Caracteristica> listarCaracteristicas() {
    return valoresCaracteristicas.stream()
        .map(v -> new Caracteristica(nombreCaracteristica, v))
        .collect(Collectors.toList());
  }
}
