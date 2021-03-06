package modelo.mascota;

import excepciones.FotosMascotaException;
import modelo.EntidadPersistente;
import modelo.informe.Ubicacion;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "mascota_encontrada")
public class MascotaEncontrada extends EntidadPersistente {

  @ElementCollection
  private List<Foto> fotos;

  @Embedded
  private Ubicacion ubicacion;

  private String estadoActual;

  private LocalDate fechaEncuentro;

  @Enumerated(EnumType.STRING)
  private TamanioMascota tamanio;

  // para hibernate
  private MascotaEncontrada() {

  }

  public MascotaEncontrada(List<Foto> fotos, Ubicacion ubicacion, String estadoActual, LocalDate fechaEncuentro,
                           TamanioMascota tamanio) {
    validarFotos(fotos);
    this.fotos = fotos;
    this.ubicacion = ubicacion;
    this.estadoActual = estadoActual;
    this.fechaEncuentro = fechaEncuentro;
    this.tamanio = tamanio;
  }

  private void validarFotos(List<Foto> fotos) {
    if (fotos == null || fotos.isEmpty())
      throw new FotosMascotaException("Se debe ingresar al menos una foto de la mascota");
  }

  public List<Foto> getFotos() {
    return fotos;
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }

  public String getEstadoActual() {
    return this.estadoActual;
  }

  public LocalDate getFechaEncuentro() {
    return fechaEncuentro;
  }

  public TamanioMascota getTamanio() {
    return tamanio;
  }
}