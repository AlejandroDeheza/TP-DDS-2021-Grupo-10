package modelo.informe;

import modelo.mascota.Mascota;
import modelo.usuario.DuenioMascota;

public class InformeQR {

    private DuenioMascota duenioMascota;
    private Mascota mascota;

    public InformeQR(DuenioMascota duenioMascota, Mascota mascota) {
        this.duenioMascota = duenioMascota;
        this.mascota = mascota;
    }

    public DuenioMascota getDuenioMascota() {
        return duenioMascota;
    }

    public Mascota getMascota() {
        return mascota;
    }
}
