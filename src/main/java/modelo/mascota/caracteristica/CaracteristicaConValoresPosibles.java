package modelo.mascota.caracteristica;

import java.util.List;

public class CaracteristicaConValoresPosibles {

  String nombreCaracteristica;
  List<String> valoresCaracteristicas;

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

}
