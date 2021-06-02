package modelo.informe;

import modelo.mascota.Foto;
import modelo.mascota.Mascota;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import repositorios.RepositorioInformes;

import java.time.LocalDate;
import java.util.List;

public class InformeMascotaSinDuenio extends InformeMascotaEncontrada {


    private Mascota mascota;

    public InformeMascotaSinDuenio(Persona rescatista, LocalDate fechaEncuentro, String direccion, List<Foto> fotosMascota, Ubicacion lugarDeEncuentro, String estadoActualMascota, RepositorioInformes repo) {
        super(rescatista, fechaEncuentro, direccion, fotosMascota, lugarDeEncuentro, estadoActualMascota, repo);
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }


    @Override
    public void procesarInforme() {
        super.procesarInforme();
        generarPublicacion();
    }

    private void generarPublicacion(){

    }

}
