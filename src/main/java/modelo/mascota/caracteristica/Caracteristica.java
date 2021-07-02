package modelo.mascota.caracteristica;

import repositorios.RepositorioCaracteristicas;

import java.util.List;

public class Caracteristica {

  private String nombreCaracteristica;
  private String valorCaracteristica;

  public Caracteristica(String nombreCaracteristica, String valorCaracteristica,
                        RepositorioCaracteristicas repositorioCaracteristicas) {
    repositorioCaracteristicas.validarCaracteristica(nombreCaracteristica, valorCaracteristica);
    this.nombreCaracteristica = nombreCaracteristica;
    this.valorCaracteristica = valorCaracteristica;
  }

  public boolean matcheaConAlgunaDe(List<Caracteristica> caracteristicas) {
    return caracteristicas
        .stream()
        .filter(caracteristica -> caracteristica.getNombreCaracteristica().equals(nombreCaracteristica))
        .anyMatch(caracteristica -> caracteristica.getValorCaracteristica().equals(valorCaracteristica));
  }

  public String getNombreCaracteristica() {
    return nombreCaracteristica;
  }

  public String getValorCaracteristica() {
    return valorCaracteristica;
  }

}
