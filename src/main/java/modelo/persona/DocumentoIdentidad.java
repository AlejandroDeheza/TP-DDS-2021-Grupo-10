package modelo.persona;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class DocumentoIdentidad {

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_documento")
  private TipoDocumento tipoDocumento;

  @Column(name = "num_documento")
  private String numeroDocumento;

  // para hibernate
  private DocumentoIdentidad() {

  }

  public DocumentoIdentidad(TipoDocumento tipoDocumento, String numeroDocumento) {
    this.tipoDocumento = tipoDocumento;
    this.numeroDocumento = numeroDocumento;
  }

  public TipoDocumento getTipoDocumento() {
    return tipoDocumento;
  }

  public String getNumeroDocumento() {
    return numeroDocumento;
  }
}
