package client.hogares;

import com.fasterxml.jackson.annotation.JsonProperty;
import modelo.mascota.Animal;

public class Admision {
    @JsonProperty("perros")
    private Boolean aceptaPerro;

    @JsonProperty("gatos")
    private Boolean aceptaGato;

    public Boolean getAceptaPerro(){
        return  this.aceptaPerro;
    }

    public Boolean getAceptaGato(){
        return  this.aceptaGato;
    }

    public void setAceptaPerro(Boolean aceptaPerro){
        this.aceptaPerro=aceptaPerro;
    }

    public void setAceptaGato(Boolean aceptaGato){
        this.aceptaGato=aceptaGato;
    }

    public Boolean aceptaAnimal (Animal animal){
        return (this.getAceptaGato() && animal == Animal.GATO) || (this.getAceptaPerro() && animal == Animal.PERRO);
    }
}
