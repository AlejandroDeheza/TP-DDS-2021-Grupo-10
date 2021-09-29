package modelo.mascota.caracteristica;

import modelo.EntidadPersistente;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;

@Entity
public class Caracteristica extends EntidadPersistente {

  private String nombreCaracteristica;
  private String valorCaracteristica;

  public Caracteristica(String nombreCaracteristica, String valorCaracteristica) {
    this.nombreCaracteristica = nombreCaracteristica;
    this.valorCaracteristica = valorCaracteristica;
  }

  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(valorCaracteristica).toHashCode();
  }

  public boolean equals(Object o) {
    boolean equals = false;
    if (o != null && Caracteristica.class.isAssignableFrom(o.getClass())) {
      Caracteristica c = (Caracteristica) o;
      equals = (new EqualsBuilder()
          .append(nombreCaracteristica, c.nombreCaracteristica)
          .append(valorCaracteristica, c.valorCaracteristica)).isEquals();
    }
    return equals;
  }

  public String getNombreCaracteristica() {
    return nombreCaracteristica;
  }

  public String getValorCaracteristica() {
    return valorCaracteristica;
  }

}
