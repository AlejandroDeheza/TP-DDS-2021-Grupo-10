package modelo.mascota.caracteristica;

import repositorios.RepositorioCaracteristicas;

public class Caracteristica {

  private String nombreCaracteristica;
  private String valorCaracteristica;
  RepositorioCaracteristicas repositorioCaracteristicas = RepositorioCaracteristicas.getInstance();

  public Caracteristica(String nombreCaracteristica, String valorCaracteristica) {
    completarCaracteristica(nombreCaracteristica, valorCaracteristica);
  }

  public Caracteristica(String nombreCaracteristica, String valorCaracteristica,RepositorioCaracteristicas repositorioCaracteristicas) {
    this.repositorioCaracteristicas=repositorioCaracteristicas;
    completarCaracteristica(nombreCaracteristica, valorCaracteristica);
  }

  private void completarCaracteristica(String nombreCaracteristica, String valorCaracteristica){
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
