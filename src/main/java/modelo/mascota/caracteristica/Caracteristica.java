package modelo.mascota.caracteristica;

import repositorios.RepositorioCaracteristicas;

public class Caracteristica {

  private String nombreCaracteristica;
  private String valorCaracteristica;

  public Caracteristica(String nombreCaracteristica, String valorCaracteristica,
                        RepositorioCaracteristicas repositorioCaracteristicas) {
    repositorioCaracteristicas.validarCaracteristica(nombreCaracteristica, valorCaracteristica);
    this.nombreCaracteristica = nombreCaracteristica;
    this.valorCaracteristica = valorCaracteristica;
  }

  public String getNombreCaracteristica() {
    return nombreCaracteristica;
  }

  public String getValorCaracteristica() {
    return valorCaracteristica;
  }

}
