package controllers;

import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;

public class BorradorCaracteristicas {

  private Integer id;
  private CaracteristicaConValoresPosibles caracteristicaConValoresPosibles;

  public BorradorCaracteristicas (Integer id, CaracteristicaConValoresPosibles caracteristicaConValoresPosibles) {
    this.id = id;
    this.caracteristicaConValoresPosibles = caracteristicaConValoresPosibles;
  }

  public Integer getId() {
    return this.id;
  }

  public CaracteristicaConValoresPosibles getCaracteristicaConValoresPosibles() {
    return this.caracteristicaConValoresPosibles;
  }

}
