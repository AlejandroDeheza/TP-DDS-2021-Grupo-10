package modelo.mascota;

public class Foto {

    private String fotoB64Encoded;
    private String metadata; //TODO Que va a tener la metadata? Por ahora la dejo en String

    public Foto(String fotoB64Encoded, String metadata){
        this.fotoB64Encoded = fotoB64Encoded;
        this.metadata = metadata;
    }

    public String getFotoB64Encoded() {
        return fotoB64Encoded;
    }

    public String getMetadata() {
        return metadata;
    }
}
