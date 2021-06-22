package modelo.hogarDeTransito.respuestas;

import com.fasterxml.jackson.annotation.JsonProperty;
import modelo.mascota.Animal;

public class RespuestaDeAdmision {
    @JsonProperty("perros")
    private Boolean aceptaPerro;

    @JsonProperty("gatos")
    private Boolean aceptaGato;

    public void setAceptaPerro(Boolean aceptaPerro){
        this.aceptaPerro = aceptaPerro;
    }

    public void setAceptaGato(Boolean aceptaGato){
        this.aceptaGato = aceptaGato;
    }

    public Boolean aceptaAnimal (Animal animal){
        return (aceptaGato && animal == Animal.GATO) || (aceptaPerro && animal == Animal.PERRO);
    }
}
