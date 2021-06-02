package modelo.informe;

import modelo.mascota.Foto;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import repositorios.RepositorioInformes;

import java.time.LocalDate;
import java.util.List;

public class InformeMascotaConDuenio extends InformeMascotaEncontrada {

    private Usuario duenioMascota;

    public InformeMascotaConDuenio(Usuario duenioMascota, Persona rescatista, LocalDate fechaEncuentro, String direccion, List<Foto> fotosMascota, Ubicacion lugarDeEncuentro, String estadoActualMascota, RepositorioInformes repositorioInformes) {
        super(rescatista, fechaEncuentro, direccion, fotosMascota, lugarDeEncuentro, estadoActualMascota, repositorioInformes);
        this.duenioMascota = duenioMascota;
    }

    public Usuario getDuenioMascota() {
        return duenioMascota;
    }

    @Override
    public void procesarInforme(){
        super.procesarInforme();
        enviarNotificacion();
    }

    private void enviarNotificacion() {
    }


}
