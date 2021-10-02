package modelo.mascota;

import javax.persistence.Embeddable;

@Embeddable
public class Foto {

  private String link;
  private String metadata; //TODO Que va a tener la metadata? Por ahora la dejo en String

  // para hibernate
  private Foto() {

  }

  public Foto(String link, String metadata) {
    this.link = link;
    this.metadata = metadata;
  }

  public String getLink() {
    return link;
  }

  public String getMetadata() {
    return metadata;
  }
}
