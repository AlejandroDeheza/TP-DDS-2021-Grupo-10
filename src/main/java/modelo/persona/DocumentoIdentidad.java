package modelo.persona;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

@Embeddable
public class DocumentoIdentidad {

  @Enumerated
  @Column(name = "tipo_documento")
  private TipoDocumento tipoDocumento;
  @Column(name = "num_documento")
  private String numeroDocumento;

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
