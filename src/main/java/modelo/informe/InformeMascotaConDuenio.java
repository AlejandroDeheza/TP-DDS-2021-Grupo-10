package modelo.informe;

import modelo.mascota.Foto;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import repositorios.RepositorioInformes;

import java.time.LocalDate;
import java.util.List;

public class InformeMascotaConDuenio extends InformeMascotaEncontrada {
    public InformeMascotaConDuenio(Usuario duenioMascota, Persona rescatista, LocalDate fechaEncuentro, String direccion, List<Foto> fotosMascota, Ubicacion lugarDeEncuentro, String estadoActualMascota, RepositorioInformes repositorioInformes) {
        super(duenioMascota, rescatista, fechaEncuentro, direccion, fotosMascota, lugarDeEncuentro, estadoActualMascota, repositorioInformes);
    }

    @Override
    public void procesarInforme(){
        super.procesarInforme();
        enviarNotificacion();
    }

    private void enviarNotificacion() {
    }


}
