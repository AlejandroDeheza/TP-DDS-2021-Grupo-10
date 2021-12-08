package controllers;

import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;

public class WrapperCaracteristica {

  private final Integer id;
  private final CaracteristicaConValoresPosibles caracteristicaConValoresPosibles;

  public WrapperCaracteristica(Integer id, CaracteristicaConValoresPosibles caracteristicaConValoresPosibles) {
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
