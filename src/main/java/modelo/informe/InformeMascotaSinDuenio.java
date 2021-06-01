package modelo.informe;

import modelo.mascota.Foto;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import repositorios.RepositorioInformes;

import java.time.LocalDate;
import java.util.List;

public class InformeMascotaSinDuenio extends InformeMascotaEncontrada {

    public InformeMascotaSinDuenio(Usuario duenioMascota, Persona rescatista, LocalDate fechaEncuentro, String direccion, List<Foto> fotosMascota, Ubicacion lugarDeEncuentro, String estadoActualMascota, RepositorioInformes repo) {
        super(duenioMascota, rescatista, fechaEncuentro, direccion, fotosMascota, lugarDeEncuentro, estadoActualMascota, repo);
    }

    @Override
    public void procesarInforme() {
        super.procesarInforme();
        generarPublicacion();
    }

    private void generarPublicacion(){

    }

}
