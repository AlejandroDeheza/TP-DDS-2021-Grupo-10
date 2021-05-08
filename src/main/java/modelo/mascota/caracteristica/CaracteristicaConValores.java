package modelo.mascota.caracteristica;

import java.util.List;

public class CaracteristicaConValores {

  String nombreCaracteristica;
  List<String> valoresCaracteristicas;

  public CaracteristicaConValores(String nombreCaracteristica, List<String> valoresCaracteristicas) {
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
