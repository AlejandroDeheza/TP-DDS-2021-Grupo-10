package modelo.informe;

import modelo.mascota.Foto;
import modelo.mascota.Mascota;
import modelo.persona.Persona;
import modelo.publicacion.Publicacion;
import modelo.usuario.Usuario;
import repositorios.RepositorioInformes;
import repositorios.RepositorioPublicaciones;

import java.time.LocalDate;
import java.util.List;

public class InformeMascotaSinDuenio extends InformeMascotaEncontrada {

    private Mascota mascota;

    public InformeMascotaSinDuenio(Persona rescatista, LocalDate fechaEncuentro, String direccion, List<Foto> fotosMascota, Ubicacion lugarDeEncuentro, String estadoActualMascota, RepositorioInformes repo, Mascota mascota) {
        super(rescatista, fechaEncuentro, direccion, fotosMascota, lugarDeEncuentro, estadoActualMascota, repo);
        this.mascota = mascota;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    @Override
    public void procesarInforme(Usuario usuario) {
        super.procesarInforme(usuario);
        RepositorioPublicaciones.getInstance().agregarPublicacion(new Publicacion(getRescatista().getDatosDeContacto(),this.getFotosMascota()));
    }

    private Hogar getHogaresTransitorios(){
        Hogar hogar = null;
        return  hogar;
    }

}
