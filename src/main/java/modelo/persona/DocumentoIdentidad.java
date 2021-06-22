package modelo.persona;

public class DocumentoIdentidad {
  private TipoDocumento tipoDocumento;
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
