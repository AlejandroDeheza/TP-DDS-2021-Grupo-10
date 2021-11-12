package modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import modelo.mascota.caracteristica.Caracteristica;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MascotaSinChapitaRequest {

  @JsonProperty("estadoMascota")
  private String estadoMascota;

  @JsonProperty("tamanioMascota")
  private String tamanioMascota;

  @JsonProperty("tipoAnimal")
  private String tipoAnimal;

  @JsonProperty("fechaRescate")
  private String fechaRescate;

  @JsonProperty("ubicacionRescate")
  private String ubicacionRescate;

  @JsonProperty("ubicacionRescatista")
  private String ubicacionRescatista;

  @JsonProperty("caracteristicasMascota")
  private List<Caracteristica> caracteristicasMascota;

  public MascotaSinChapitaRequest(String estadoMascota, String tamanioMascota, String tipoAnimal, String fechaRescate, String ubicacionRescate, String ubicacionRescatista, List<Caracteristica> caracteristicasMascota) {
    this.estadoMascota = estadoMascota;
    this.tamanioMascota = tamanioMascota;
    this.tipoAnimal = tipoAnimal;
    this.fechaRescate = fechaRescate;
    this.ubicacionRescate = ubicacionRescate;
    this.ubicacionRescatista = ubicacionRescatista;
    this.caracteristicasMascota = caracteristicasMascota;
  }

  public String getEstadoMascota() {
    return estadoMascota;
  }

  public void setEstadoMascota(String estadoMascota) {
    this.estadoMascota = estadoMascota;
  }

  public String getTamanioMascota() {
    return tamanioMascota;
  }

  public void setTamanioMascota(String tamanioMascota) {
    this.tamanioMascota = tamanioMascota;
  }

  public String getTipoAnimal() {
    return tipoAnimal;
  }

  public void setTipoAnimal(String tipoAnimal) {
    this.tipoAnimal = tipoAnimal;
  }

  public String getFechaRescate() {
    return fechaRescate;
  }

  public void setFechaRescate(String fechaRescate) {
    this.fechaRescate = fechaRescate;
  }

  public String getUbicacionRescate() {
    return ubicacionRescate;
  }

  public void setUbicacionRescate(String ubicacionRescate) {
    this.ubicacionRescate = ubicacionRescate;
  }

  public String getUbicacionRescatista() {
    return ubicacionRescatista;
  }

  public void setUbicacionRescatista(String ubicacionRescatista) {
    this.ubicacionRescatista = ubicacionRescatista;
  }

  public List<Caracteristica> getCaracteristicasMascota() {
    return caracteristicasMascota;
  }

  public void setCaracteristicasMascota(List<Caracteristica> caracteristicasMascota) {
    this.caracteristicasMascota = caracteristicasMascota;
  }
}